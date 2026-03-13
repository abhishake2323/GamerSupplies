package com.starmodestudios.gamersupplies.controller;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser // Bypasses security for this test
    public void testShowAddForm() throws Exception {
        mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @WithMockUser
    public void testSubmitValidProductOverridesRedirect() throws Exception {
        // Mock the save behavior
        when(productService.save(any(Product.class))).thenReturn(new Product());

        mockMvc.perform(post("/products/add")
                        .with(csrf()) // Required for POST when Spring Security is active
                        .param("name", "Test Console")
                        .param("description", "A test console")
                        .param("price", "299.99")
                        .param("quantity", "10")
                        .param("category", "Consoles")
                        .param("sku", "TEST-CON-01")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(productService, times(1)).save(any(Product.class));
    }

    @Test
    @WithMockUser
    public void testSubmitInvalidProductShowsFormWithErrors() throws Exception {
        mockMvc.perform(post("/products/add")
                        .with(csrf())
                        // Missing name, string for price, invalid quantity bounds
                        .param("description", "A test console")
                        .param("price", "-5.00") // Invalid: < 0.01
                        .param("quantity", "10000") // Invalid: > 9999
                        // Missing category, sku
                )
                .andExpect(status().isOk()) // Returned to same view
                .andExpect(view().name("add-product"))
                .andExpect(model().attributeHasFieldErrors("product", "name", "price", "quantity", "category", "sku"));

        // Verify save was NEVER called because validation failed
        verify(productService, never()).save(any(Product.class));
    }

    @Test
    @WithMockUser
    public void testListProducts() throws Exception {
        when(productService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"));
        
        verify(productService, times(1)).findAll();
    }
}

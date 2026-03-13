package com.starmodestudios.gamersupplies.service;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Headset");
        product.setSku("HS-01");
        product.setCategory("Accessories");
        product.setPrice(79.99);
        product.setQuantity(15);
        product.setDescription("Gaming headset");
    }

    @Test
    public void testSave() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.save(new Product());

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Headset");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testFindAll() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product, product2));

        List<Product> products = productService.findAll();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Headset");
        assertThat(products.get(1).getName()).isEqualTo("Mouse");
        verify(productRepository, times(1)).findAll();
    }
}

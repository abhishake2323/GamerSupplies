package com.starmodestudios.gamersupplies.controller;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Available product categories (matches dropdown options in the form)
    private static final List<String> CATEGORIES = List.of("Consoles", "Accessories", "Games");

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /products — product list page
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("pageTitle", "Products");
        return "products";
    }

    // GET /products/add — show empty add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("pageTitle", "Add Product");
        return "add-product";
    }

    // POST /products/add — handle form submission with validation
    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Re-populate the category list and return the form with errors
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("pageTitle", "Add Product");
            return "add-product";
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("successMessage",
                "Product \"" + product.getName() + "\" was added successfully!");
        return "redirect:/products";
    }
}

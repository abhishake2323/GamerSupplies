//Name: Fahad Arif (N01729165)
//Course: Web Application Development (CPAN-228)

package com.starmodestudios.gamersupplies.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.repository.ProductRepository;
import com.starmodestudios.gamersupplies.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    private static final List<String> CATEGORIES = List.of(
        "Consoles", "Controllers", "Headsets",
        "Keyboards", "Mice", "Monitors", "Chairs",
        "Accessories", "Games", "Merchandise"
    );

    public AdminController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("pageTitle", "Add Product");
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("pageTitle", "Add Product");
            return "add-product";
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("successMessage",
                "Product \"" + product.getName() + "\" was added successfully!");
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
            return "redirect:/admin";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("pageTitle", "Edit Product");
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
            return "redirect:/admin";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("pageTitle", "Edit Product");
            return "edit";
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setSku(product.getSku());
        existingProduct.setImageUrl(product.getImageUrl());

        productService.save(existingProduct);

        redirectAttributes.addFlashAttribute("successMessage",
                "Product \"" + existingProduct.getName() + "\" was updated successfully!");
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
            return "redirect:/admin";
        }

        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Product \"" + product.getName() + "\" was deleted successfully!");
        return "redirect:/admin";
    }
}
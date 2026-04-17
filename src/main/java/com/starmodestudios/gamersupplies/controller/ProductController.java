//Name: Fahad Arif (N01729165)
//Course: Web Application Development (CPAN-228)

package com.starmodestudios.gamersupplies.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private static final List<String> CATEGORIES = List.of(
        "Consoles", "Controllers", "Headsets",
        "Keyboards", "Mice", "Monitors", "Chairs",
        "Accessories", "Games", "Merchandise"
    );

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            Model model) {

        List<Product> products;

        if (category != null && !category.isEmpty() && maxPrice != null) {
            products = productService.findByCategoryAndMaxPrice(category, maxPrice);
        } else if (category != null && !category.isEmpty()) {
            products = productService.findByCategory(category);
        } else if (maxPrice != null) {
            products = productService.findByMaxPrice(maxPrice);
        } else {
            products = productService.findAll();
        }

        Comparator<Product> comparator = switch (sortBy) {
            case "price" -> Comparator.comparing(Product::getPrice, Comparator.nullsLast(Comparator.naturalOrder()));
            case "quantity" -> Comparator.comparing(Product::getQuantity, Comparator.nullsLast(Comparator.naturalOrder()));
            default -> Comparator.comparing(p -> p.getName() != null ? p.getName().toLowerCase() : "");
        };

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        products = products.stream().sorted(comparator).collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("productCount", products.size());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedMaxPrice", maxPrice);
        model.addAttribute("selectedSortBy", sortBy);
        model.addAttribute("selectedSortOrder", sortOrder);
        model.addAttribute("pageTitle", "Products");

        return "products";
    }
}
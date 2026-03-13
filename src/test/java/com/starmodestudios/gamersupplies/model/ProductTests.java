package com.starmodestudios.gamersupplies.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProduct() {
        Product product = new Product();
        product.setName("Gaming Keyboard");
        product.setDescription("Mechanical RGB keyboard");
        product.setPrice(99.99);
        product.setQuantity(50);
        product.setCategory("Accessories");
        product.setSku("KB-RGB-01");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testMissingRequiredFields() {
        Product product = new Product();
        // Missing name, description, price, quantity, category, and sku

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertThat(violations).hasSize(6);
    }

    @Test
    public void testPriceBoundaries() {
        Product product = createValidProduct();
        
        // Test lower bound (0.00 is invalid, 0.01 is valid)
        product.setPrice(0.00);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("at least $0.01");

        // Test upper bound (10000.00 is invalid, 9999.99 is valid)
        product.setPrice(10000.00);
        violations = validator.validate(product);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("cannot exceed $9,999.99");
    }

    @Test
    public void testQuantityBoundaries() {
        Product product = createValidProduct();
        
        // Test lower bound (negative is invalid)
        product.setQuantity(-1);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("cannot be negative");

        // Test upper bound (> 9999 is invalid)
        product.setQuantity(10000);
        violations = validator.validate(product);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("cannot exceed 9,999");
    }

    // Helper method
    private Product createValidProduct() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("Valid Description");
        product.setPrice(10.00);
        product.setQuantity(5);
        product.setCategory("Games");
        product.setSku("VALID-SKU");
        return product;
    }
}

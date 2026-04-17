//Name: Fahad Arif (N01729165)
//Course: Web Application Development (CPAN-228)

package com.starmodestudios.gamersupplies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be 100 characters or fewer")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be 500 characters or fewer")
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least $0.01")
    @DecimalMax(value = "9999.99", message = "Price cannot exceed $9,999.99")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 9999, message = "Quantity cannot exceed 9,999")
    @Column(nullable = false)
    private Integer quantity;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category must be 100 characters or fewer")
    @Column(nullable = false, length = 100)
    private String category;

    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU must be 50 characters or fewer")
    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Size(max = 500, message = "Image URL must be 500 characters or fewer")
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    public Product() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category != null ? category.trim() : null;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) {
        this.sku = sku != null ? sku.trim() : null;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl != null ? imageUrl.trim() : null;
    }
}
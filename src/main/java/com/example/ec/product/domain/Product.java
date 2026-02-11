package com.example.ec.product.domain;

import com.example.ec.category.domain.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品名（必須） */
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    /** 価格（必須） */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "validation.price,positive")
    @Digits(integer = 8, fraction = 2)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** 説明（任意） */
    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    /** 画像（任意） */
    @URL(message = "validation.url.invalid")
    @Column(length = 1024)
    private String imageUrl;

    /** 在庫数（必須） */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer stockQty = 0;

    /** カテゴリー（必須） */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getImageUrl(){ return imageUrl; } public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
    public Integer getStockQty(){return stockQty;} public void setStockQty(Integer s){this.stockQty=s;}
    public Category getCategory(){ return category;} public void setCategory(Category category){ this.category=category; }
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}

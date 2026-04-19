package com.example.ec.domain.product;

import com.example.ec.domain.category.Category;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** ビジネスルールを持つ */
public class Product {

    private Long id;
    /** 商品名 */
    private String name;
    /** 価格 */
    private BigDecimal price;
    /** 説明 */
    private String description;
    /** 画像 */
    private String imageUrl;
    /** 在庫数 */
    private Integer stockQty = 0;
    /** カテゴリー */
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product()
    {
        this.stockQty = stockQty != null ? stockQty : 0;
    }

    public Product(
            Long id,
            String name,
            BigDecimal price,
            String description,
            String imageUrl,
            Integer stockQty,
            Category category,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stockQty = stockQty;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getImageUrl(){ return imageUrl; } public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
    public Integer getStockQty(){return stockQty;} public void setStockQty(Integer s){this.stockQty = stockQty != null ? stockQty : 0;}
    public Category getCategory(){ return category;} public void setCategory(Category category){ this.category=category; }
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}

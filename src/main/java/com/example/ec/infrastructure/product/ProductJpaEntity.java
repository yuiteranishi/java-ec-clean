package com.example.ec.infrastructure.product;

import com.example.ec.domain.category.Category;
import com.example.ec.domain.product.Product;
import com.example.ec.infrastructure.category.CategoryJpaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** DBテーブルとマッピング */
@Entity
@Table(name = "products")
public class ProductJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品名（必須） */
    @Column(nullable = false)
    private String name;

    /** 価格（必須） */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** 説明（任意） */
    @Column(length = 1000)
    private String description;

    /** 画像（任意） */
    @Column(length = 1024)
    private String imageUrl;

    /** 在庫数（必須） */
    @Column(nullable = false)
    private Integer stockQty = 0;

    /** カテゴリー（必須） */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected ProductJpaEntity() {}

    private ProductJpaEntity(
            Long id,
            String name,
            BigDecimal price,
            String description,
            String imageUrl,
            Integer stockQty,
            CategoryJpaEntity categoryJpaEntity,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stockQty = stockQty;
        this.category = categoryJpaEntity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * JPA Entity → Domain Entity
     */
    public Product toDomain()
    {
        Category domainCategory = (category != null) ? category.toDomain() : null;
        return new Product(
                id,
                name,
                price,
                description,
                imageUrl,
                stockQty,
                domainCategory,
                createdAt,
                updatedAt
        );
    }

    /**
     * Domain Entity → JPA Entity
     */
    public static ProductJpaEntity fromDomain(Product p)
    {
        //  Category が存在する場合は id だけ持つ参照用インスタンスを使用する
        CategoryJpaEntity categoryRef = null;
        if (p.getCategory() != null) {
            categoryRef = CategoryJpaEntity.refById(p.getCategory().getId());
        }
        return new ProductJpaEntity(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDescription(),
                p.getImageUrl(),
                p.getStockQty(),
                categoryRef,
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}

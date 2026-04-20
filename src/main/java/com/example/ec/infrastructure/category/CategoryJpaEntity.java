package com.example.ec.infrastructure.category;

import com.example.ec.domain.category.Category;
import jakarta.persistence.*;

/** DBテーブルとマッピング */
@Entity
@Table(name = "categories")
public class CategoryJpaEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** カテゴリ名（必須） */
    @Column(nullable=false, unique=true)
    private String name;

    /** URL用（必須） */
    @Column(nullable=false, unique=true, length=128)
    private String slug;

    /** 説明（任意） */
    @Column(length=1000)
    private String description;

    /** 画像（任意） */
    @Column(length=1024)
    private String imageUrl;

    /** 並び順（必須） */
    @Column(nullable=false)
    private Integer sortOrder = 0;

    /** 公開フラグ */
    @Column(nullable=false)
    private Boolean isVisible = false;

    protected CategoryJpaEntity() {}

    private CategoryJpaEntity(
            Long id,
            String name,
            String slug,
            String description,
            String imageUrl,
            Integer sortOrder,
            Boolean isVisible
    )
    {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.isVisible = isVisible;
    }

    /** JPA Entity → Domain Entity */
    public Category toDomain()
    {
        return new Category(id, name, slug, description, imageUrl, sortOrder, isVisible);
    }

    /** Domain Entity → JPA Entity */
    public static CategoryJpaEntity fromDomain(Category c)
    {
        return new CategoryJpaEntity(
                c.getId(),
                c.getName(),
                c.getSlug(),
                c.getDescription(),
                c.getImageUrl(),
                c.getSortOrder(),
                c.getIsVisible()
        );
    }

    /**
     * id だけ持つ参照用インスタンス
     */
    public static CategoryJpaEntity refById(Long id) {
        CategoryJpaEntity ref = new CategoryJpaEntity();
        ref.id = id;
        return ref;
    }
}

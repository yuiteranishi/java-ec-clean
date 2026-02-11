package com.example.ec.category.domain;

import jakarta.persistence.*;

@Entity @Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setId(String name){this.name=name;}
    public String getSlug(){return slug;} public void setSlug(String slug){this.slug=slug;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public Integer getSortOrder(){return sortOrder;} public void setSortOrder(Integer sortOrder){this.sortOrder=sortOrder;}
    public Boolean getIsVisible(){return isVisible;} public void setSortOrder(Boolean isVisible){this.isVisible=isVisible;}
}
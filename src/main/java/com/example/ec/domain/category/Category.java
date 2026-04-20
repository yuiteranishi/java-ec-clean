package com.example.ec.domain.category;

/**
 * ビジネスルールを持つ
 * */
public class Category {

    private Long id;
    /** カテゴリ名 */
    private String name;
    /** URL用 */
    private String slug;
    /** 説明 */
    private String description;
    /** 画像 */
    private String imageUrl;
    /** 並び順 */
    private Integer sortOrder = 0;
    /** 公開フラグ */
    private Boolean isVisible = false;

    public Category()
    {
        this.sortOrder = 0;
        this.isVisible = false;
    }

    public Category (
            Long id,
            String name,
            String slug,
            String description,
            String imageUrl,
            Integer sortOrder,
            Boolean isVisible
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder != null ? sortOrder : 0;
        this.isVisible = Boolean.TRUE.equals(isVisible);
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getSlug(){return slug;} public void setSlug(String slug){this.slug=slug;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public Integer getSortOrder(){return sortOrder;} public void setSortOrder(Integer sortOrder){this.sortOrder = sortOrder != null ? sortOrder : 0;}
    public Boolean getIsVisible(){return isVisible;} public void setIsVisible(Boolean isVisible){this.isVisible = Boolean.TRUE.equals(isVisible);}
}
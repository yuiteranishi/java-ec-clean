package com.example.ec.product.dto;

public record ProductDetailDto (
        Long id,
        String name,
        String priceText,
        String description,
        String imageUrl,
        String categoryName,
        String categorySlug
) {}
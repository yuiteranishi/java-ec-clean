package com.example.ec.product.web.dto;

// Viewに必要な形だけ（フォーマット済み価格などを持てる）
public record ProductView(
        Long id,
        String name,
        String priceText,
        String description,
        String imageUrl,
        String categoryName,
        String categorySlug
) {}

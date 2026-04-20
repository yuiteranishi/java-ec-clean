package com.example.ec.application.product;

import com.example.ec.domain.category.Category;
import com.example.ec.dto.product.ProductDetailDto;

import java.util.List;

public interface ProductQueryUseCase {
    // 一覧
    default List<ProductDetailDto> list() { return list(null); }

    // 一覧（カテゴリ指定あり）
    List<ProductDetailDto> list(String categorySlug);

    // 詳細
    ProductDetailDto getOr404(Long id);

    // カテゴリ一覧
    List<Category> listVisibleCategories();
}

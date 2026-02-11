package com.example.ec.product.application;

import com.example.ec.category.domain.Category;
import com.example.ec.product.web.dto.ProductView;

import java.util.List;

public interface ProductQueryUseCase {
    // 一覧
    default List<ProductView> list() { return list(null); }

    // 一覧（カテゴリ指定あり）
    List<ProductView> list(String categorySlug);

    // 詳細
    ProductView getOr404(Long id);

    // カテゴリ一覧
    List<Category> listVisibleCategories();
}

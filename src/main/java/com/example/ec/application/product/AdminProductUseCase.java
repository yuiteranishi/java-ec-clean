package com.example.ec.application.product;

import com.example.ec.domain.category.Category;
import com.example.ec.dto.product.from.AdminProductForm;
import com.example.ec.dto.product.AdminProductSummaryDto;

import java.util.List;

public interface AdminProductUseCase {

    // 一覧
    List<AdminProductSummaryDto> listAll();

    // 新規登録（フォーム）
    AdminProductForm newForm();

    // 更新（フォーム）
    AdminProductForm loadForm(Long id);

    // 新規登録（処理）
    Long create(AdminProductForm form);

    // 更新（処理）
    void update(Long id, AdminProductForm form);

    //  削除（処理）
    void delete(Long Id);

    // カテゴリ一覧
    List<Category> listVisibleCategories();
}

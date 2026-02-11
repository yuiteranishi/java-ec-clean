package com.example.ec.product.application;

import com.example.ec.product.form.AdminProductForm;
import com.example.ec.product.web.dto.AdminProductSummaryView;

import java.util.List;

public interface AdminProductUseCase {

    // 一覧
    List<AdminProductSummaryView> listAll();

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
}

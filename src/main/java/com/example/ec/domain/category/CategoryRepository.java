package com.example.ec.domain.category;

import java.util.List;
import java.util.Optional;

/** ビジネス用語で定義 */
public interface CategoryRepository
{
    // idが一致するカテゴリを返す
    Optional<Category> findById(Long id);
    // slugが一致する公開フラグがtrueのカテゴリを返す
    Optional<Category> findVisibleBySlug(String slug);
    // 公開フラグがtrueのカテゴリを並び順と名前の昇順で返す
    List<Category> findAllVisible();
    // カテゴリの保存
    Category save(Category category);
}

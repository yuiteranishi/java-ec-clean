package com.example.ec.category.repository;

import com.example.ec.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // slugが一致する公開フラグがtrueのカテゴリを返す
    Optional<Category> findBySlugAndIsVisibleTrue(String slug);
    // 公開フラグがtrueのカテゴリを並び順と名前の昇順で返す
    List<Category> findAllByIsVisibleTrueOrderBySortOrderAscNameAsc();
}

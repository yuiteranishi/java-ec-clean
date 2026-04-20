package com.example.ec.infrastructure.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** 技術的な操作を行う */
public interface JpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Long>
{
    /** CategoryRepositoryImpl@findVisibleBySlug から呼ばれる */
    Optional<CategoryJpaEntity> findBySlugAndIsVisibleTrue(String slug);
    /** CategoryRepositoryImpl@findAllVisible から呼ばれる */
    List<CategoryJpaEntity> findAllByIsVisibleTrueOrderBySortOrderAscNameAsc();
}

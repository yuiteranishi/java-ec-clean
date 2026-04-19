package com.example.ec.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 技術的な操作を行う */
public interface JpaProductRepository extends JpaRepository<ProductJpaEntity, Long>
{
    /**
     * ProductRepositoryImpl@findAllByCategorySlug から呼ばれる
     */
    List<ProductJpaEntity> findAllByCategory_Slug(String slug);
}

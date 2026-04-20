package com.example.ec.infrastructure.category;

import com.example.ec.domain.category.Category;
import com.example.ec.domain.category.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CategoryRepository と JpaCategoryRepository の橋渡し
 * */
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpa;
    public CategoryRepositoryImpl(JpaCategoryRepository jpa)
    {
        this.jpa = jpa;
    }

    @Override
    public Optional<Category> findById(Long id)
    {
        return jpa.findById(id).map(CategoryJpaEntity::toDomain);
    }

    @Override
    public Optional<Category> findVisibleBySlug(String slug)
    {
        return jpa.findBySlugAndIsVisibleTrue(slug).map(CategoryJpaEntity::toDomain);
    }

    @Override
    public List<Category> findAllVisible()
    {
        return jpa.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc()
                .stream()
                .map(CategoryJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Category save(Category category)
    {
        CategoryJpaEntity entity = CategoryJpaEntity.fromDomain(category);
        CategoryJpaEntity saved = jpa.save(entity);
        return saved.toDomain();
    }
}

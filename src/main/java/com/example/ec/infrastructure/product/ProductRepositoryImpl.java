package com.example.ec.infrastructure.product;

import com.example.ec.domain.product.Product;
import com.example.ec.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepository と JpaProductRepository の橋渡し
 * */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpa;

    public ProductRepositoryImpl(JpaProductRepository jpa)
    {
        this.jpa = jpa;
    }

    @Override
    public Optional<Product> findById(Long id)
    {
        return jpa.findById(id).map(ProductJpaEntity::toDomain);
    }

    @Override
    public List<Product> findAll()
    {
        return jpa.findAll().stream().map(ProductJpaEntity::toDomain).toList();
    }

    @Override
    public List<Product> findAllByCategorySlug(String slug)
    {
        return jpa.findAllByCategory_Slug(slug).stream().map(ProductJpaEntity::toDomain).toList();
    }

    @Override
    public Product save(Product product)
    {
        ProductJpaEntity entity = ProductJpaEntity.fromDomain(product);
        ProductJpaEntity saved = jpa.save(entity);

        return saved.toDomain();
    }

    @Override
    public void delete(Product product)
    {
        jpa.delete(ProductJpaEntity.fromDomain(product));
    }
}

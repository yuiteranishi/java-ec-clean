package com.example.ec.domain.product;

import java.util.List;
import java.util.Optional;

/** ビジネス用語で定義 */
public interface ProductRepository
{
    // idが一致する商品を返す
    Optional<Product> findById(Long id);
    // すべての商品一覧を返す
    List<Product> findAll();
    // 指定カテゴリに一致する商品一覧を返す
    List<Product> findAllByCategorySlug(String slug);
    // 商品の保存
    Product save(Product product);
    // 商品の削除
    void delete(Product product);
}

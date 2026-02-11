package com.example.ec.product.application;

import com.example.ec.category.domain.Category;
import com.example.ec.category.repository.CategoryRepository;
import com.example.ec.product.application.AdminProductUseCase;
import com.example.ec.product.domain.Product;
import com.example.ec.product.repository.ProductRepository;
import com.example.ec.product.form.AdminProductForm;
import com.example.ec.product.web.dto.AdminProductSummaryView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class ProductAdminService implements AdminProductUseCase {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductAdminService (ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminProductSummaryView> listAll() {
        return productRepo.findAll().stream()
                // .map() はStream APIのメソッドなので単体では使えない → Stream に変換
                // Listなどのコレクションはデータの入れ物。Streamはデータを流して処理する仕組み
                // 1件ずつ商品を表示用に変換（必要な項目に絞り込み）
                .map(p -> new AdminProductSummaryView(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStockQty(),
                        Optional.ofNullable(p.getCategory()).map(Category::getName).orElse("-")
                ))
                // リストに戻す
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProductForm newForm() {
        // record は引数必須なので、初期値を明示して 1 回生成して返す
        return new AdminProductForm(null, "", null, "", 0, "", null);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProductForm loadForm(Long id) {
        var p = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Product not found: " + id));

        return new AdminProductForm(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDescription(),
                p.getStockQty(),
                p.getImageUrl(),
                Optional.ofNullable(p.getCategory()).map(Category::getId).orElse(null)
        );
    }

    @Override
    public Long create(AdminProductForm f) {
        // 空のエンティティを作成
        var p = new Product();
        // フォームの中身をエンティティへコピーする
        applyFormToEntity(f, p);
        // 登録し、IDを取得
        return productRepo.save(p).getId();
    }

    @Override
    public void update(Long id, AdminProductForm f) {
        var p = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found: " + id));
        // 既存エンティティにフォームの値を上書き
        applyFormToEntity(f, p);
        productRepo.save(p);
    }

    @Override
    public void delete(Long id) {
        var p = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found: " + id));
        productRepo.delete(p);
    }

    private void applyFormToEntity(AdminProductForm f, Product p) {
        p.setName(f.name());
        p.setPrice(f.price());
        p.setDescription(f.description());
        p.setStockQty(f.stockQty());
        p.setImageUrl(f.imageUrl());

        if (f.category() == null) {
            p.setCategory(null);
        } else {
            // 選択されたカテゴリー（ID）を取得しセット
            var cat = categoryRepo.findById(f.category())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category not found: " + f.category()));
            p.setCategory(cat);
        }
    }
}

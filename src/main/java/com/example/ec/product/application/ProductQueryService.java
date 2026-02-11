package com.example.ec.product.application;

import com.example.ec.category.repository.CategoryRepository;
import com.example.ec.product.repository.ProductRepository;
import com.example.ec.product.web.dto.ProductView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductPresenter presenter = new ProductPresenter();

    public ProductQueryService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<ProductView> list(String categorySlug) {
        var products = (categorySlug == null || categorySlug.isBlank())
                ? productRepo.findAll()
                : productRepo.findAllByCategory_Slug(categorySlug);
        // 商品を1つずつ取り出して表示用に変換しリストにして返す
        return products.stream().map(presenter::toView).toList();
    }

    @Override
    public ProductView getOr404(Long id) {
        // IDに一致する商品を探し、取得できれば表示用に変換した値・取得できなければ404を返す
        return productRepo.findById(id)
                .map(presenter::toView)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found: " + id));
    }

    @Override
    public List<com.example.ec.category.domain.Category> listVisibleCategories() {
        // 公開フラグがtrueのカテゴリを並び順と名前の昇順で返す
        return categoryRepo.findAllByIsVisibleTrueOrderBySortOrderAscNameAsc();
    }
}

package com.example.ec.application.product;

import com.example.ec.domain.category.Category;
import com.example.ec.domain.category.CategoryRepository;
import com.example.ec.domain.product.ProductRepository;
import com.example.ec.domain.product.port.ProductPresenterPort;
import com.example.ec.dto.product.ProductDetailDto;
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
    private final ProductPresenterPort presenter;

    public ProductQueryService(ProductRepository productRepo, CategoryRepository categoryRepo, ProductPresenterPort presenter) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.presenter = presenter;
    }

    @Override
    public List<ProductDetailDto> list(String categorySlug) {
        var products = (categorySlug == null || categorySlug.isBlank())
                ? productRepo.findAll()
                : productRepo.findAllByCategorySlug(categorySlug);
        // 商品を1つずつ取り出して表示用に変換しリストにして返す
        return products.stream().map(presenter::toDto).toList();
    }

    @Override
    public ProductDetailDto getOr404(Long id) {
        // IDに一致する商品を探し、取得できれば表示用に変換した値・取得できなければ404を返す
        return productRepo.findById(id)
                .map(presenter::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found: " + id));
    }

    @Override
    public List<Category> listVisibleCategories() {
        // 公開フラグがtrueのカテゴリを並び順と名前の昇順で返す
        return categoryRepo.findAllVisible();
    }
}

package com.example.ec.application.product;

import com.example.ec.domain.category.Category;
import com.example.ec.domain.category.CategoryRepository;
import com.example.ec.domain.product.Product;
import com.example.ec.domain.product.ProductRepository;
import com.example.ec.domain.product.exception.CategoryNotFoundException;
import com.example.ec.domain.product.exception.ProductNotFoundException;
import com.example.ec.dto.product.form.AdminProductForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminProductServiceTest {
    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;
    private AdminProductService service;

    @BeforeEach
    void setUp() {
        productRepo  = mock(ProductRepository.class);
        categoryRepo = mock(CategoryRepository.class);
        service      = new AdminProductService(productRepo, categoryRepo);
    }

    @Test
    @DisplayName("listAll：商品一覧をDTOに変換して返す")
    void listAll_正常()
    {
        var category = new Category(1L, "food", "food", null, null, 0, true);
        var product1 = new Product(1L, "りんご", BigDecimal.valueOf(100), null, null, 10, category, null, null);
        var product2 = new Product(2L, "みかん", BigDecimal.valueOf(80), null, null, 5, null, null, null);

        when(productRepo.findAll()).thenReturn(List.of(product1, product2));

        var result = service.listAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("りんご");
        assertThat(result.get(0).category()).isEqualTo("food");
        assertThat(result.get(1).name()).isEqualTo("みかん");
        assertThat(result.get(1).category()).isEqualTo("-");
    }

    @Test
    @DisplayName("create：商品が保存され、IDが返される")
    void create_正常()
    {
        var category = new Category(1L, "food", "food", null, null, 0, true);
        var form = new AdminProductForm(null, "りんご", BigDecimal.valueOf(100), "説明", 10, null, 1L);
        var saved = new Product(42L, "りんご", BigDecimal.valueOf(100), "説明", null, 10, category, null, null);

        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
        when(productRepo.save(any())).thenReturn(saved);

        Long id = service.create(form);
        assertThat(id).isEqualTo(42L);
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("create：存在しないカテゴリIDを指定すると404")
    void create_カテゴリ不在() {
        var form = new AdminProductForm(null, "りんご", BigDecimal.valueOf(100), null, 10, null, 999L);
        when(categoryRepo.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(form))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found");

        verify(productRepo, never()).save(any());
    }

    @Test
    @DisplayName("update：既存商品が更新される")
    void update_正常() {
        var existing = new Product(1L, "旧名前", BigDecimal.valueOf(100), null, null, 5, null, null, null);
        var form = new AdminProductForm(1L, "新名前", BigDecimal.valueOf(200), null, 10, null, null);

        when(productRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.update(1L, form);

        verify(productRepo, times(1)).save(argThat(p ->
                p.getName().equals("新名前") &&
                        p.getPrice().compareTo(BigDecimal.valueOf(200)) == 0
        ));
    }

    @Test
    @DisplayName("update：存在しない商品IDを指定すると404")
    void update_商品不在() {
        var form = new AdminProductForm(999L, "名前", BigDecimal.valueOf(100), null, 1, null, null);
        when(productRepo.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999L, form))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    @DisplayName("delete：商品が削除される")
    void delete_正常() {
        var product = new Product(1L, "りんご", BigDecimal.valueOf(100), null, null, 5, null, null, null);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        service.delete(1L);

        verify(productRepo, times(1)).delete(product);
    }

    @Test
    @DisplayName("delete：存在しない商品IDを指定すると404")
    void delete_商品不在() {
        when(productRepo.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found");
    }
}

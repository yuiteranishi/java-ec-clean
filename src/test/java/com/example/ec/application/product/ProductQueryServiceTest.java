package com.example.ec.application.product;

import com.example.ec.application.product.port.ProductPresenterPort;
import com.example.ec.domain.category.Category;
import com.example.ec.domain.category.CategoryRepository;
import com.example.ec.domain.product.Product;
import com.example.ec.domain.product.ProductRepository;
import com.example.ec.dto.product.ProductDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductQueryServiceTest {
    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;
    private ProductPresenterPort presenterPort;
    private ProductQueryService service;

    @BeforeEach
    void setUp() {
        productRepo   = mock(ProductRepository.class);
        categoryRepo  = mock(CategoryRepository.class);
        presenterPort = mock(ProductPresenterPort.class);
        service = new ProductQueryService(productRepo, categoryRepo, presenterPort);
    }

    @Test
    @DisplayName("list：全件取得してDTOに変換して返す")
    void list_全件() {
        var product1 = new Product(1L, "りんご", BigDecimal.valueOf(100), null, null, 5, null, null, null);
        var product2 = new Product(2L, "みかん", BigDecimal.valueOf(80),  null, null, 3, null, null, null);
        var dto1 = new ProductDetailDto(1L, "りんご", "¥100", null, null, null, null);
        var dto2 = new ProductDetailDto(2L, "みかん", "¥80",  null, null, null, null);

        when(productRepo.findAll()).thenReturn(List.of(product1, product2));
        when(presenterPort.toDto(product1)).thenReturn(dto1);
        when(presenterPort.toDto(product2)).thenReturn(dto2);

        var result = service.list(null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("りんご");
        assertThat(result.get(1).name()).isEqualTo("みかん");
        verify(productRepo, times(1)).findAll();
        verify(productRepo, never()).findAllByCategorySlug(any());
    }

    @Test
    @DisplayName("list(slug)：カテゴリslugで絞り込んで返す")
    void list_カテゴリ絞り込み() {
        var product = new Product(1L, "りんご", BigDecimal.valueOf(100), null, null, 5, null, null, null);
        var dto = new ProductDetailDto(1L, "りんご", "¥100", null, null, "果物", "fruits");

        when(productRepo.findAllByCategorySlug("fruits")).thenReturn(List.of(product));
        when(presenterPort.toDto(product)).thenReturn(dto);

        var result = service.list("fruits");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).categorySlug()).isEqualTo("fruits");
        verify(productRepo, never()).findAll();
        verify(productRepo, times(1)).findAllByCategorySlug("fruits");
    }

    @Test
    @DisplayName("getOr404：IDで商品が見つかればDTOを返す")
    void getOr404_正常() {
        var product = new Product(1L, "りんご", BigDecimal.valueOf(100), null, null, 5, null, null, null);
        var dto = new ProductDetailDto(1L, "りんご", "¥100", null, null, null, null);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(presenterPort.toDto(product)).thenReturn(dto);

        var result = service.getOr404(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("りんご");
    }

    @Test
    @DisplayName("getOr404：IDで商品が見つからなければ404")
    void getOr404_商品不在() {
        when(productRepo.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getOr404(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    @DisplayName("listVisibleCategories：公開カテゴリ一覧を返す")
    void listVisibleCategories_正常() {
        var category = new Category(1L, "果物", "fruits", null, null, 0, true);
        when(categoryRepo.findAllVisible()).thenReturn(List.of(category));

        var result = service.listVisibleCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("果物");
    }
}

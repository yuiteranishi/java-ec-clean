package com.example.ec.adapter.presenter;

import com.example.ec.domain.product.Product;
import com.example.ec.product.dto.ProductDetailDto;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class ProductPresenter {
    // 価格を日本の通過形式に変換
    private static final NumberFormat CURRENCY =
            NumberFormat.getCurrencyInstance(Locale.JAPAN);

    public ProductDetailDto toDto(Product p) {
        // 表示用の整形を行う
        var category = p.getCategory();
        // カテゴリがあれば名前・slugをセット、無ければ空欄
        String cName = (category != null ? category.getName() : null);
        String cSlug = (category != null ? category.getSlug() : null);
        return new ProductDetailDto(
                p.getId(),
                p.getName(),
                CURRENCY.format(p.getPrice()),
                p.getDescription(),
                p.getImageUrl(),
                cName,
                cSlug
        );
    }
}

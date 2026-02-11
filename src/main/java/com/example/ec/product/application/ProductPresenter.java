package com.example.ec.product.application;

import com.example.ec.product.domain.Product;
import com.example.ec.product.web.dto.ProductView;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductPresenter {
    // 価格を日本の通過形式に変換
    private static final NumberFormat CURRENCY =
            NumberFormat.getCurrencyInstance(Locale.JAPAN);

    public ProductView toView(Product p) {
        // 表示用の整形を行う
        var category = p.getCategory();
        // カテゴリがあれば名前・slugをセット、無ければ空欄
        String cName = (category != null ? category.getName() : null);
        String cSlug = (category != null ? category.getSlug() : null);
        return new ProductView(
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

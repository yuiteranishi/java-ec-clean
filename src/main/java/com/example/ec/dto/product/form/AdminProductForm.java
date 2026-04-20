package com.example.ec.dto.product.form;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record AdminProductForm(
    Long id, // 更新時のみ使用（新規は null）

    @NotBlank
    @Size(max = 255)
    String name,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "{validation.price.positive}")
    @Digits(integer = 8, fraction = 2)
    BigDecimal price,

    @Size(max = 1000)
    String description,

    @NotNull
    @PositiveOrZero
    Integer stockQty,

    @URL(message = "{validation.url.invalid}")
    @Size(max = 1024)
    String imageUrl,

    Long category
) {
    public AdminProductForm withId(Long newId) {
        return new AdminProductForm(newId, name, price, description, stockQty, imageUrl, category);
    }
    public AdminProductForm withCategoryId(Long newCategory) {
        return new AdminProductForm(id, name, price, description, stockQty, imageUrl, newCategory);
    }
}

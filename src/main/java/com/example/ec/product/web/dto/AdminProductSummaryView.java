package com.example.ec.product.web.dto;

import java.math.BigDecimal;

public record AdminProductSummaryView(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQty,
        String category
) { }

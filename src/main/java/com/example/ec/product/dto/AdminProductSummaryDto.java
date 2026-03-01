package com.example.ec.product.dto;

import java.math.BigDecimal;

public record AdminProductSummaryDto (
        Long id,
        String name,
        BigDecimal price,
        Integer stockQty,
        String category
) {}

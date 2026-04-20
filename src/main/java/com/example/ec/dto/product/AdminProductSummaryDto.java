package com.example.ec.dto.product;

import java.math.BigDecimal;

public record AdminProductSummaryDto (
        Long id,
        String name,
        BigDecimal price,
        Integer stockQty,
        String category
) {}

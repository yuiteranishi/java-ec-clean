package com.example.ec.domain.product.exception;

public class ProductNotFoundException extends RuntimeException {

    private final Long productId;

    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }
}

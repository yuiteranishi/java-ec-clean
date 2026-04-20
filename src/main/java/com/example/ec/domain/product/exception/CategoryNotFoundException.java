package com.example.ec.domain.product.exception;

public class CategoryNotFoundException extends RuntimeException {

    private final Long categoryId;

    public CategoryNotFoundException(Long id) {
        super("Category not found: " + id);
        this.categoryId = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}

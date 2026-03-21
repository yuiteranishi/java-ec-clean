package com.example.ec.application.product.port;

import com.example.ec.domain.product.Product;
import com.example.ec.dto.product.ProductDetailDto;

public interface ProductPresenterPort {
    ProductDetailDto toDto(Product p);
}

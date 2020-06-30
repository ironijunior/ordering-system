package com.ironijunior.orderingsystem.adapter;

import com.ironijunior.orderingsystem.controller.dto.product.ProductRequest;
import com.ironijunior.orderingsystem.controller.dto.product.ProductResponse;
import com.ironijunior.orderingsystem.domain.Product;

import java.util.Optional;

public class ProductAdapter {

    public static Product toDomain(ProductRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .quantity(request.getQuantity())
                .build();
    }

    public static ProductResponse toResponse(Product domainProduct) {
        return Optional.ofNullable(domainProduct)
                .map(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setId(product.getId());
                    productResponse.setType(product.getType());
                    productResponse.setDescription(product.getDescription());
                    productResponse.setName(product.getName());
                    productResponse.setQuantity(product.getQuantity());
                    return productResponse;
                }).orElse(null);
    }
}

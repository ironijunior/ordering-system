package com.ironijunior.orderingsystem.controller.dto.product;

import com.ironijunior.orderingsystem.domain.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProductRequest {
    @NonNull
    private ProductType type;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Long quantity;
}

package com.ironijunior.orderingsystem.controller.dto.product;

import com.ironijunior.orderingsystem.domain.ProductType;
import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private ProductType type;
    private String name;
    private String description;
    private Long quantity;
}

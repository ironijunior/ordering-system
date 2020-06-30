package com.ironijunior.orderingsystem.controller.dto.order;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long id;
    private String name;
    private Long quantity;

}

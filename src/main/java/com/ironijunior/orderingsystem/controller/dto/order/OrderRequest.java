package com.ironijunior.orderingsystem.controller.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<OrderItemDTO> items;

}

package com.ironijunior.orderingsystem.controller.dto.order;

import com.ironijunior.orderingsystem.domain.OrderStatus;
import lombok.Data;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private OrderStatus status;
    private List<OrderItemDTO> items;

}

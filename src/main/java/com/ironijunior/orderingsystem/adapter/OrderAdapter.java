package com.ironijunior.orderingsystem.adapter;

import com.ironijunior.orderingsystem.controller.dto.order.OrderItemDTO;
import com.ironijunior.orderingsystem.controller.dto.order.OrderRequest;
import com.ironijunior.orderingsystem.controller.dto.order.OrderResponse;
import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.domain.OrderItem;
import com.ironijunior.orderingsystem.domain.Product;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAdapter {

    public static Set<OrderItem> toDomain(OrderRequest request) {
        if (request == null) {
            return new HashSet<>();
        }

        return request.getItems().stream()
                .map(item -> {
                    OrderItem.EmbbededId id = new OrderItem.EmbbededId();
                    id.setProduct(Product.builder().id(item.getId()).build());
                    return OrderItem.builder()
                            .id(id)
                            .quantity(item.getQuantity()).build();
                })
                .collect(Collectors.toSet());
    }

    public static OrderResponse toResponse(Order domainOrder) {
        return Optional.ofNullable(domainOrder)
                .map(order -> {
                    OrderResponse response = new OrderResponse();
                    response.setId(order.getId());
                    response.setStatus(order.getStatus());
                    response.setItems(
                            order.getItems().stream()
                                    .map(item -> {
                                        OrderItemDTO itemDTO = new OrderItemDTO();
                                        itemDTO.setId(item.getId().getProduct().getId());
                                        itemDTO.setName(item.getId().getProduct().getName());
                                        itemDTO.setQuantity(item.getQuantity());
                                        return itemDTO;
                                    }).collect(Collectors.toList())
                    );
                    return response;
                })
                .orElse(null);
    }
}

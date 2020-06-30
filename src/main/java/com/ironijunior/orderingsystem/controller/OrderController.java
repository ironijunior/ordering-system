package com.ironijunior.orderingsystem.controller;

import com.ironijunior.orderingsystem.adapter.OrderAdapter;
import com.ironijunior.orderingsystem.controller.dto.order.OrderRequest;
import com.ironijunior.orderingsystem.controller.dto.order.OrderResponse;
import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes an API to manage order of products.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        Order order = service.create(OrderAdapter.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderAdapter.toResponse(order));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(OrderAdapter.toResponse(service.get(id)));
    }

}

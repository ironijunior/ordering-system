package com.ironijunior.orderingsystem.service.impl;

import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.domain.OrderItem;
import com.ironijunior.orderingsystem.domain.OrderStatus;
import com.ironijunior.orderingsystem.domain.Product;
import com.ironijunior.orderingsystem.exception.OrderNotFoundException;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import com.ironijunior.orderingsystem.repository.OrderRepository;
import com.ironijunior.orderingsystem.service.OrderService;
import com.ironijunior.orderingsystem.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;
    private ProductService productService;

    @Override
    public Order create(Set<OrderItem> orderItems) {
        Order order = createBaseOrder();

        Set<OrderItem> availableItems = orderItems.stream()
                .filter(this::isKnownProduct)
                .filter(this::isAvailableInStock)
                .peek(orderItem -> orderItem.getId().setOrder(order))
                .collect(Collectors.toSet());

        order.setItems(availableItems);

        if (availableItems.isEmpty()) {
            order.setStatus(OrderStatus.CANCELLED);
            return repository.saveAndFlush(order);
        }

        availableItems.forEach(orderItem -> {
            Product stockProduct = getProduct(orderItem.getId().getProduct().getId());
            updateStockProduct(stockProduct, orderItem.getQuantity());
        });

        order.setStatus(OrderStatus.DELIVERED);
        return repository.saveAndFlush(order);
    }

    @Override
    public Order get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private Order createBaseOrder() {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.RUNNING);
        return order;
    }

    private Product getProduct(Long id) {
        return productService.get(id);
    }

    private void updateStockProduct(Product product, Long quantity) {
        productService.udpateStock(
                product.getId(),product.getQuantity() - quantity);
    }

    private boolean isKnownProduct(OrderItem orderItem) {
        try {
            Product product = productService.get(orderItem.getId().getProduct().getId());
            return Objects.nonNull(product);
        } catch(ProductNotFoundException ex) {
            log.info("The order will be cancelled because of: " + ex.getMessage(), ex);
            return false;
        }
    }

    private boolean isAvailableInStock(OrderItem orderItem) {
        Product stockProduct = getProduct(orderItem.getId().getProduct().getId());
        return stockProduct.getQuantity() >= orderItem.getQuantity();
    }

}

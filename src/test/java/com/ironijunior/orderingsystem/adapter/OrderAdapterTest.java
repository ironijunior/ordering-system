package com.ironijunior.orderingsystem.adapter;

import com.ironijunior.orderingsystem.controller.dto.order.OrderItemDTO;
import com.ironijunior.orderingsystem.controller.dto.order.OrderRequest;
import com.ironijunior.orderingsystem.controller.dto.order.OrderResponse;
import com.ironijunior.orderingsystem.domain.Order;
import com.ironijunior.orderingsystem.domain.OrderItem;
import com.ironijunior.orderingsystem.domain.OrderStatus;
import com.ironijunior.orderingsystem.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAdapterTest {

    private static final Long PRODUCT_ID = 1L;
    private static final Long PRODUCT_QT = 10L;
    private static final Long ORDER_ID = 1L;
    private static final String PRODUCT_NAME = "Product name";

    @Test
    public void testToDomain() {
        OrderRequest request = createMockRequest();

        OrderItem.EmbbededId id = new OrderItem.EmbbededId();
        id.setProduct(Product.builder().id(PRODUCT_ID).build());
        OrderItem expectedItem = OrderItem.builder().id(id).quantity(PRODUCT_QT).build();

        Set<OrderItem> actual = OrderAdapter.toDomain(request);
        assertFalse(actual.isEmpty());

        actual.forEach(actualItem -> {
            assertEquals(actualItem.getQuantity(), expectedItem.getQuantity());
            assertEquals(actualItem.getId().getProduct().getId(), expectedItem.getId().getProduct().getId());
            assertNull(actualItem.getId().getOrder());
        });
    }

    @Test
    public void testToDomainNullRequest() {
        OrderRequest request = null;

        Set<OrderItem> actual = OrderAdapter.toDomain(request);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testToDomainEmptyItemList() {
        OrderRequest request = createMockRequest();
        request.setItems(new ArrayList<>());

        Set<OrderItem> actual = OrderAdapter.toDomain(request);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testToResponse() {
        Order order = createMockOrder();

        OrderResponse response = OrderAdapter.toResponse(order);
        assertNotNull(response);

        assertEquals(ORDER_ID, response.getId());
        assertEquals(order.getStatus(), response.getStatus());
        assertNotNull(response.getItems());
        assertFalse(response.getItems().isEmpty());
        response.getItems()
                .forEach(actualItem -> {
                    assertEquals(PRODUCT_ID, actualItem.getId());
                    assertEquals(PRODUCT_NAME, actualItem.getName());
                    assertEquals(PRODUCT_QT, actualItem.getQuantity());
                });
    }

    @Test
    public void testToResponseNullOrder() {
        Order order = null;

        OrderResponse response = OrderAdapter.toResponse(order);
        assertNull(response);
    }

    @Test
    public void testToResponseEmptyItemList() {
        Order order = createMockOrder();
        order.setItems(new HashSet<>());

        OrderResponse response = OrderAdapter.toResponse(order);
        assertNotNull(response);

        assertEquals(ORDER_ID, response.getId());
        assertEquals(order.getStatus(), response.getStatus());
        assertNotNull(response.getItems());
        assertTrue(response.getItems().isEmpty());
    }

    private Order createMockOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.DELIVERED);
        order.setId(ORDER_ID);

        OrderItem.EmbbededId itemId = new OrderItem.EmbbededId();
        itemId.setProduct(Product.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build());
        order.setItems(Set.of(
                OrderItem.builder().id(itemId).quantity(PRODUCT_QT).build()
        ));

        return order;
    }

    private OrderRequest createMockRequest() {
        OrderRequest request = new OrderRequest();

        OrderItemDTO item1 = new OrderItemDTO();
        item1.setQuantity(PRODUCT_QT);
        item1.setId(PRODUCT_ID);

        request.setItems(List.of(item1));
        return request;
    }

}
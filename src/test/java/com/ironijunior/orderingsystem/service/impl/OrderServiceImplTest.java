package com.ironijunior.orderingsystem.service.impl;

import com.ironijunior.orderingsystem.domain.*;
import com.ironijunior.orderingsystem.exception.OrderNotFoundException;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import com.ironijunior.orderingsystem.repository.OrderRepository;
import com.ironijunior.orderingsystem.service.OrderService;
import com.ironijunior.orderingsystem.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class OrderServiceImplTest {

    private static final Long ORDER_ID = 10L;
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Product name";
    private static final Long PRODUCT_ORDER_QT = 2L;
    private static final Long PRODUCT_STOCK_QT =20L;
    private static final Long PRODUCT_OUT_OF_STOCK =0L;

    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private ProductService productService = Mockito.mock(ProductService.class);

    private OrderService orderService;

    @BeforeEach
    public void setup() {
        orderService = new OrderServiceImpl(orderRepository, productService);
    }

    @Test
    public void testCreateOrder() {
        Mockito.when(productService.get(PRODUCT_ID))
                .thenReturn(Product.builder()
                        .id(PRODUCT_ID)
                        .type(ProductType.TYPE_ONE)
                        .quantity(PRODUCT_STOCK_QT)
                        .name(PRODUCT_NAME)
                        .build());

        Mockito.when(productService.udpateStock(PRODUCT_ID, PRODUCT_ORDER_QT))
                .thenReturn(Product.builder()
                                .id(PRODUCT_ID)
                                .type(ProductType.TYPE_ONE)
                                .quantity(PRODUCT_STOCK_QT - PRODUCT_ORDER_QT)
                                .name(PRODUCT_NAME)
                                .build());

        Mockito.when(orderRepository.saveAndFlush(any()))
                .then(answer -> {
                    Order order = answer.getArgument(0);
                    order.setId(ORDER_ID);
                    return order;
                });

        Order actualOrder = orderService.create(createOrderItems());
        assertNotNull(actualOrder);
        assertEquals(ORDER_ID, actualOrder.getId());
        assertEquals(OrderStatus.DELIVERED, actualOrder.getStatus());
        assertNotNull(actualOrder.getCreatedAt());
        assertFalse(actualOrder.getItems().isEmpty());
        actualOrder.getItems().forEach(actualItem -> {
            assertEquals(actualItem.getId().getProduct().getId(), PRODUCT_ID);
            assertEquals(actualItem.getQuantity(), PRODUCT_ORDER_QT);
            assertNotNull(actualItem.getId().getOrder());
        });
    }

    @Test
    public void testCreateOrderNonKnownProduct() {
        Mockito.when(productService.get(PRODUCT_ID))
                .thenThrow(new ProductNotFoundException(PRODUCT_ID));

        Mockito.when(orderRepository.saveAndFlush(any()))
                .then(answer -> {
                    Order order = answer.getArgument(0);
                    order.setId(ORDER_ID);
                    return order;
                });

        Order actualOrder = orderService.create(createOrderItems());
        assertNotNull(actualOrder);
        assertEquals(ORDER_ID, actualOrder.getId());
        assertEquals(OrderStatus.CANCELLED, actualOrder.getStatus());
        assertNotNull(actualOrder.getCreatedAt());
        assertTrue(actualOrder.getItems().isEmpty());
    }

    @Test
    public void testCreateOrderOutOfStock() {
        Mockito.when(productService.get(PRODUCT_ID))
                .thenReturn(Product.builder()
                        .id(PRODUCT_ID)
                        .type(ProductType.TYPE_ONE)
                        .quantity(PRODUCT_OUT_OF_STOCK)
                        .name(PRODUCT_NAME)
                        .build());

        Mockito.when(orderRepository.saveAndFlush(any()))
                .then(answer -> {
                    Order order = answer.getArgument(0);
                    order.setId(ORDER_ID);
                    return order;
                });

        Order actualOrder = orderService.create(createOrderItems());
        assertNotNull(actualOrder);
        assertEquals(ORDER_ID, actualOrder.getId());
        assertEquals(OrderStatus.CANCELLED, actualOrder.getStatus());
        assertNotNull(actualOrder.getCreatedAt());
        assertTrue(actualOrder.getItems().isEmpty());
    }

    @Test
    public void testGetNotFoundOrder() {
        Mockito.when(orderRepository.findById(any()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.get(ORDER_ID);
        });
    }

    private Set<OrderItem> createOrderItems() {
        OrderItem.EmbbededId id = new OrderItem.EmbbededId();
        id.setProduct(Product.builder().id(PRODUCT_ID).build());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(PRODUCT_ORDER_QT);

        return Set.of(orderItem);
    }

}
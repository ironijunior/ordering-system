package com.ironijunior.orderingsystem.adapter;

import com.ironijunior.orderingsystem.controller.dto.order.OrderItemDTO;
import com.ironijunior.orderingsystem.controller.dto.order.OrderRequest;
import com.ironijunior.orderingsystem.controller.dto.order.OrderResponse;
import com.ironijunior.orderingsystem.controller.dto.product.ProductRequest;
import com.ironijunior.orderingsystem.controller.dto.product.ProductResponse;
import com.ironijunior.orderingsystem.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAdapterTest {

    private static final Long PRODUCT_ID = 1L;
    private static final Long PRODUCT_QT = 10L;
    private static final Long ORDER_ID = 1L;
    private static final String PRODUCT_NAME = "Product name";

    @Test
    public void testToDomain() {
        ProductRequest request = createMockRequest();

        Product actualProduct = ProductAdapter.toDomain(request);
        assertNotNull(actualProduct);

        assertNull(actualProduct.getId());
        assertNull(actualProduct.getUpdatedAt());
        assertNull(actualProduct.getCreatedAt());
        assertEquals(PRODUCT_NAME, actualProduct.getName());
        assertEquals("", actualProduct.getDescription());
        assertEquals(ProductType.TYPE_ONE, actualProduct.getType());
        assertEquals(PRODUCT_QT, actualProduct.getQuantity());
    }

    @Test
    public void testToDomainNullRequest() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductAdapter.toDomain(null);
        });
    }

    @Test
    public void testToResponse() {
        Product product = createMockProduct();

        ProductResponse response = ProductAdapter.toResponse(product);
        assertNotNull(response);

        assertEquals(PRODUCT_ID, response.getId());
        assertEquals(PRODUCT_NAME, response.getName());
        assertEquals("", response.getDescription());
        assertEquals(PRODUCT_QT, response.getQuantity());
        assertEquals(ProductType.TYPE_ONE, response.getType());
    }

    @Test
    public void testToResponseNull() {
        Product product = null;
        ProductResponse response = ProductAdapter.toResponse(product);
        assertNull(response);
    }

    private Product createMockProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setDescription("");
        product.setName(PRODUCT_NAME);
        product.setQuantity(PRODUCT_QT);
        product.setType(ProductType.TYPE_ONE);
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedAt(LocalDateTime.now());

        return product;
    }

    private ProductRequest createMockRequest() {
        ProductRequest request = new ProductRequest();
        request.setType(ProductType.TYPE_ONE);
        request.setName(PRODUCT_NAME);
        request.setDescription("");
        request.setQuantity(PRODUCT_QT);

        return request;
    }

}
package com.ironijunior.orderingsystem.service.impl;

import com.ironijunior.orderingsystem.domain.Product;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import com.ironijunior.orderingsystem.repository.OrderRepository;
import com.ironijunior.orderingsystem.repository.ProductRepository;
import com.ironijunior.orderingsystem.service.OrderService;
import com.ironijunior.orderingsystem.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ProductServiceImplTest {

    private static final Long PRODUCT_ID = 1L;
    private ProductRepository repository = Mockito.mock(ProductRepository.class);
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductServiceImpl(repository);
    }

    @Test
    public void testGetProductNotFound() {
        Mockito.when(repository.findById(any()))
                .thenReturn(Optional.ofNullable(null));
        assertThrows(ProductNotFoundException.class, () -> {
            productService.get(PRODUCT_ID);
        });
    }

}
package com.ironijunior.orderingsystem.controller;

import com.ironijunior.orderingsystem.controller.dto.ErrorResponse;
import com.ironijunior.orderingsystem.exception.OrderNotFoundException;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerExceptionHandlerTest {

    private static final Long ORDER_ID = 1L;
    private static final Long PRODUCT_ID = 2L;

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    public void testHandleOrderNotFoundException() {
        OrderNotFoundException ex = new OrderNotFoundException(ORDER_ID);
        ErrorResponse response = handler.handleOrderNotFound(ex, Locale.getDefault());

        String expectedMsg = MessageFormat.format("No order found for the id {0}", ORDER_ID);
        assertEquals(expectedMsg, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void testHandleProductNotFoundException() {
        ProductNotFoundException ex = new ProductNotFoundException(PRODUCT_ID);
        ErrorResponse response = handler.handleProductNotFound(ex, Locale.getDefault());

        String expectedMsg = MessageFormat.format("No product found for the id {0}", PRODUCT_ID);
        assertEquals(expectedMsg, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void testHandleUnexpectedException() {
        RuntimeException ex = new RuntimeException("Any message");
        ErrorResponse response = handler.handleUnexpectedException(ex, Locale.getDefault());

        String expectedMsg = "Any message";
        assertEquals(expectedMsg, response.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
    }

}
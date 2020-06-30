package com.ironijunior.orderingsystem.controller;

import com.ironijunior.orderingsystem.controller.dto.ErrorResponse;
import com.ironijunior.orderingsystem.exception.OrderNotFoundException;
import com.ironijunior.orderingsystem.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle the {@link ProductNotFoundException} exception and transform to
     * {@link ErrorResponse}.
     *
     * @param ex {@link ProductNotFoundException} class
     * @param locale {@link Locale} object. Could be used to internationalization
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleProductNotFound(ProductNotFoundException ex, Locale locale) {
        return createAndLogErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the {@link OrderNotFoundException} exception and transform to
     * {@link ErrorResponse}.
     *
     * @param ex {@link OrderNotFoundException} class
     * @param locale {@link Locale} object. Could be used to internationalization
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleOrderNotFound(OrderNotFoundException ex, Locale locale) {
        return createAndLogErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the {@link Exception} exception and transform to
     * {@link ErrorResponse}.
     *
     * @param ex {@link Exception} class
     * @param locale {@link Locale} object. Could be used to internationalization
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleUnexpectedException(Exception ex, Locale locale) {
        return createAndLogErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createAndLogErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse error = ErrorResponse.builder()
                .statusCode(status.value())
                .message(ex.getMessage())
                .build();
        logger.error(error.getMessage(), ex);
        return error;
    }

}

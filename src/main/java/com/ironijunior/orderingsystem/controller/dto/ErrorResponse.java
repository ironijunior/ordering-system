package com.ironijunior.orderingsystem.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String message;
    private Integer statusCode;

}

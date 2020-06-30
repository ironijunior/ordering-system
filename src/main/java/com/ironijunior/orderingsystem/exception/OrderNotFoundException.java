package com.ironijunior.orderingsystem.exception;

import java.text.MessageFormat;

/**
 * No order found with that id number.
 */
public class OrderNotFoundException extends RuntimeException {

    private static final String MSG = "No order found for the id {0}";
    private final Long id;

     public OrderNotFoundException(Long id) {
         super(MessageFormat.format(MSG, id));
         this.id = id;
     }

}

package com.ironijunior.orderingsystem.exception;

import org.apache.logging.log4j.message.StringFormattedMessage;

import java.text.MessageFormat;

/**
 * No product found with that id number.
 */
public class ProductNotFoundException extends RuntimeException {

    private static final String MSG = "No product found for the id {0}";
    private final Long id;

     public ProductNotFoundException(Long id) {
         super(MessageFormat.format(MSG, id));
         this.id = id;
     }

}

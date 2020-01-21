/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.Exception;

public class CurrencyConversionException extends Exception {
    private static final long serialVersionUID = 1L;

    public CurrencyConversionException(String message) {
        super(message);
    }
}

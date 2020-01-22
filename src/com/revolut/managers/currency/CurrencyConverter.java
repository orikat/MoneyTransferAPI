/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.currency;

import java.math.BigDecimal;

import com.revolut.Exception.CurrencyConversionException;

/**
 * Interface to support currency conversion based on different implementation 
 * **/

public interface CurrencyConverter {
    /**
     * Convert amount from base currency to target currency
     * @param baseCurrency: currency to convert from
     * @param targetCurrency: currency to convert to
     * @param amount: money value to be converted
     * @exception CurrencyConversionException if any error happened while convert amount currency
     * **/
    public abstract BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount) throws CurrencyConversionException;
}
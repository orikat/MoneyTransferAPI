/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.currency;

import java.math.BigDecimal;

import com.revolut.Exception.CurrencyConversionException;

import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;

/**
 * convert currency from base to target
 * 
 * 
 * if no network a local rates will be returned ECB Currency conversion is done
 * using java money API.
 **/
public class DefaultCurrencyConverter implements CurrencyConverter {

    @Override
    public BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount)
            throws CurrencyConversionException {
        try {

            if (baseCurrency.equals(targetCurrency))
                return amount;
            BankUaCom currencyConverter = new BankUaCom(Currency.fromString(baseCurrency),
                    Currency.fromString(targetCurrency));
            Float money = currencyConverter.convertCurrency(amount.floatValue(), Currency.USD, Currency.EUR);
            System.out.println(money);
            return new BigDecimal(money);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new CurrencyConversionException("Cannot convert amount = " + amount + " " + baseCurrency + " to "
                    + targetCurrency + "cause: " + e.getMessage());
        }
    }
}

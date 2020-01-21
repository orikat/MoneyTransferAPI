/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.currency;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;

import com.revolut.Exception.CurrencyConversionException;

/**
 * Support currency conversion using Real-time European Central Bank Rate provider
 * 
 * if no network a local rates will be returned
 * ECB Currency conversion is done using java money API.
 * **/
public class ECBCurrencyConverter implements CurrencyConverter {
    private static final String EXHANGE_RATE_PROVIDER = "ECB"; 
    
    @Override
    public BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount) throws CurrencyConversionException {
        try {

            if (baseCurrency.equals(targetCurrency))
                return amount;
            MonetaryAmount amountMoney = Money.of(amount, baseCurrency);
            ExchangeRateProvider ecbExchangeRateProvider = MonetaryConversions
                    .getExchangeRateProvider(EXHANGE_RATE_PROVIDER);
            System.out.println(ecbExchangeRateProvider);
            CurrencyConversion targetConversion = ecbExchangeRateProvider.getCurrencyConversion(targetCurrency);
            System.out.println(targetConversion.getExchangeRate(amountMoney));
            BigDecimal result = amountMoney.with(targetConversion).getNumber().numberValue(BigDecimal.class);
            System.out.println(result);
        } catch (Exception e) {
            throw new CurrencyConversionException(
                    "Cannot convert amount = " + amount + " " + baseCurrency + " to " + targetCurrency + "cause: "+ e.getMessage());
        }
        return amount;
    }
}

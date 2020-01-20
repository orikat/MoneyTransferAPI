package com.revolut.managers.currency;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;

public class ECBCurrencyConverter implements CurrencyConverter {
    private static final String EXHANGE_RATE_PROVIDER = "ECB";// Rate Provider: European Central Bank
    
    @Override
    public BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount) {
        if (baseCurrency.equals(targetCurrency)) return amount;
        MonetaryAmount amountMoney = Money.of(amount, baseCurrency);
        ExchangeRateProvider ecbExchangeRateProvider = MonetaryConversions.getExchangeRateProvider(EXHANGE_RATE_PROVIDER);
        CurrencyConversion targetConversion = ecbExchangeRateProvider.getCurrencyConversion(targetCurrency);
        return amountMoney.with(targetConversion).getNumber().numberValue(BigDecimal.class);
    }
}

package com.revolut.managers.currency;

import java.math.BigDecimal;

public interface CurrencyConverter {

    public abstract BigDecimal convertCurrency(String baseCurrency, String targetCurrency, BigDecimal amount);
}
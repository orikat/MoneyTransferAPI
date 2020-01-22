/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package managers;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.Exception.CurrencyConversionException;
import com.revolut.managers.currency.CurrencyConverter;
import com.revolut.managers.currency.DefaultCurrencyConverter;

public class ECBCurrencyConverterTest {

    @Test
    public void convertCurrencyTest() throws CurrencyConversionException {
        String baseCurr = "USD";
        String targetCurr = "EUR";
        BigDecimal amount = new BigDecimal(1000);
        CurrencyConverter converter = new DefaultCurrencyConverter();
        BigDecimal convertedAmount = converter.convertCurrency(baseCurr, targetCurr, amount);
        System.out.println(convertedAmount);
        Assert.assertNotNull(convertedAmount);
    }
    
    @Test
    public void convertSameCurrencyTest() throws CurrencyConversionException {
        String baseCurr = "USD";
        String targetCurr = "USD";
        BigDecimal amount = new BigDecimal(1000);
        CurrencyConverter converter = new DefaultCurrencyConverter();
        BigDecimal convertedAmount = converter.convertCurrency(baseCurr, targetCurr, amount);
        Assert.assertNotNull(convertedAmount);
        Assert.assertEquals(amount, new BigDecimal(1000));
    }
}

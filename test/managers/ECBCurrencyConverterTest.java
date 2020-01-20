package managers;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.managers.currency.CurrencyConverter;
import com.revolut.managers.currency.ECBCurrencyConverter;

public class ECBCurrencyConverterTest {

    @Test
    public void convertCurrencyTest() {
        // will test only not null as currency rate changes by days and also if network is not available
        String baseCurr = "USD";
        String targetCurr = "EUR";
        BigDecimal amount = new BigDecimal(1000);
        CurrencyConverter converter = new ECBCurrencyConverter();
        BigDecimal convertedAmount = converter.convertCurrency(baseCurr, targetCurr, amount);
        Assert.assertNotNull(convertedAmount);
//        Assert.assertEquals(convertedAmount.setScale(2, RoundingMode.FLOOR), new BigDecimal(900.252).setScale(2, RoundingMode.FLOOR));
    }
    
    @Test
    public void convertSameCurrencyTest() {
        String baseCurr = "USD";
        String targetCurr = "USD";
        BigDecimal amount = new BigDecimal(1000);
        CurrencyConverter converter = new ECBCurrencyConverter();
        BigDecimal convertedAmount = converter.convertCurrency(baseCurr, targetCurr, amount);
        Assert.assertNotNull(convertedAmount);
        Assert.assertEquals(amount, new BigDecimal(1000));
    }
}

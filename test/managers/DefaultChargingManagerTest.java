/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package managers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

import com.revolut.managers.charging.ChargingManager;
import com.revolut.managers.charging.DefaultChargingManager;

public class DefaultChargingManagerTest {
    
    @Test
    public void calculateFeesTest() {
        BigDecimal amount = new BigDecimal(1000);
        ChargingManager defaulManager = new DefaultChargingManager();
        BigDecimal bg1 = amount.multiply(DefaultChargingManager.getComissionFeePercentage());
        Assert.assertEquals(defaulManager.calculateChargingFees(amount).setScale(4, RoundingMode.FLOOR), bg1.add(amount.multiply(DefaultChargingManager.getTaxFeePercentage())).setScale(4, RoundingMode.FLOOR));
        
    }
}

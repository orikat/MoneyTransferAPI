/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.charging;

import java.math.BigDecimal;

public class DefaultChargingManager implements ChargingManager {
    
    private static final BigDecimal COMISSION_FEE_PERCENTAGE = new BigDecimal(0);//FIXME put in properties file
    private static final BigDecimal TAX_FEE_PERCENTAGE = new BigDecimal(0);//FIXME put in properties file
    
    @Override
    public BigDecimal calculateChargingFees(BigDecimal amount) {
        return calculateFeesAmount(amount).add(calculateTaxAmount(amount));
    }
    
    private BigDecimal calculateFeesAmount(BigDecimal amount) {
        return amount.multiply(COMISSION_FEE_PERCENTAGE);
    }
    
    private BigDecimal calculateTaxAmount(BigDecimal amount) {
        return amount.multiply(TAX_FEE_PERCENTAGE);
    }

    public static BigDecimal getComissionFeePercentage() {
        return COMISSION_FEE_PERCENTAGE;
    }

    public static BigDecimal getTaxFeePercentage() {
        return TAX_FEE_PERCENTAGE;
    }
}

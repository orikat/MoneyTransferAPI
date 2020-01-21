/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.charging;

import java.math.BigDecimal;

import com.revolut.Exception.CurrencyConversionException;

/**
 * Interface to calculate charging fees on transaction
 * **/

public interface ChargingManager {

    /**
     * calculate charging fees including taxes, transfer fees and any other related fees
     * charging fees will be deducted from sender account
     * @param amount: amount to be transfered
     * @exception CurrencyConversionException if any error happened while convert amount currency
     * **/
    abstract BigDecimal calculateChargingFees(BigDecimal amount);

}
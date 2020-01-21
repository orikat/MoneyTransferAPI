/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.gateways;

import com.revolut.models.PaymentGatewayTransaction;

/**
 * Interface to process money transfer using payment gate-way implementation
 * **/

public interface PaymentGateway {
    
    /**
     * Transfer money from sender to receiver 
     * @param PaymentGatewayTransaction: holds total amount deducted from sender & added to reciever
     * @return status indicate the result of transfer
     * STARTED(0),
       SUCCESSEFUL(1),
       PROCESSING(2),
       FAILED(-1),
       NO_ENOUGH_CREDIT(-2);
     * **/
    public abstract int transfer(PaymentGatewayTransaction transaction);
}

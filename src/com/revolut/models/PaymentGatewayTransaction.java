/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.models;

import java.math.BigDecimal;

public class PaymentGatewayTransaction {
    private UserTransaction userTransaction;
    private BigDecimal senderDedcutionAmount;
    private BigDecimal receiverAddAmount;
    
    public PaymentGatewayTransaction(UserTransaction userTransaction, BigDecimal senderDedcutionAmount, BigDecimal receiverAddAmount) {
        this.userTransaction = userTransaction;
        this.senderDedcutionAmount = senderDedcutionAmount;
        this.receiverAddAmount = receiverAddAmount;
    }
    
    public UserTransaction getUserTransaction() {
        return userTransaction;
    }
    
    public void setUserTransaction(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }

    public BigDecimal getSenderDedcutionAmount() {
        return senderDedcutionAmount;
    }

    public BigDecimal getReceiverAddAmount() {
        return receiverAddAmount;
    }

    public void setSenderDedcutionAmount(BigDecimal senderDedcutionAmount) {
        this.senderDedcutionAmount = senderDedcutionAmount;
    }

    public void setReceiverAddAmount(BigDecimal receiverAddAmount) {
        this.receiverAddAmount = receiverAddAmount;
    }
    
}

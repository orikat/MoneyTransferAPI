/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.gateways;

import java.math.BigDecimal;

import com.revolut.models.PaymentGatewayTransaction;

/**
 * Default implementation of payment gate-way
 * **/
public class DefaultPaymentGateWay implements PaymentGateway {

    @Override
    public int transfer(PaymentGatewayTransaction transaction) {
        PaymentGatewayStatus status = PaymentGatewayStatus.STARTED;
        BigDecimal senderAccountCacheValue = transaction.getUserTransaction().getSender().getAccountValue();
        BigDecimal recieverAccountCacheValue = transaction.getUserTransaction().getReciever().getAccountValue();

        status = PaymentGatewayStatus.PROCESSING;
        
        if(senderAccountCacheValue.subtract(transaction.getSenderDedcutionAmount()).signum() < 0) return PaymentGatewayStatus.NO_ENOUGH_CREDIT.getValue(); 

        try {
            BigDecimal senderAccount = senderAccountCacheValue;
            BigDecimal recieverAccount = recieverAccountCacheValue;

            transaction.getUserTransaction().getSender().setAccountValue(senderAccount.subtract(transaction.getSenderDedcutionAmount()));
            transaction.getUserTransaction().getReciever().setAccountValue(recieverAccount.add(transaction.getReceiverAddAmount()));

        } catch (Throwable e) {
            rollback(transaction, senderAccountCacheValue, recieverAccountCacheValue);
            status = PaymentGatewayStatus.FAILED;
            return status.getValue();
        }

        status = PaymentGatewayStatus.SUCCESSEFUL;
        return status.getValue();
    }

    private void rollback(PaymentGatewayTransaction transaction, BigDecimal senderOldAccount,
            BigDecimal recOldAccount) {
        transaction.getUserTransaction().getSender().setAccountValue(senderOldAccount);
        transaction.getUserTransaction().getReciever().setAccountValue(recOldAccount);

    }
}

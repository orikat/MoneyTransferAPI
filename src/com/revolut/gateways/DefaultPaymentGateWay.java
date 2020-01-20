package com.revolut.gateways;

import java.math.BigDecimal;

import com.revolut.models.PaymentGatewayTransaction;

public class DefaultPaymentGateWay implements PaymentGateway {

    @Override
    public int transfer(PaymentGatewayTransaction transaction) {
        PaymentGatewayStatus status = PaymentGatewayStatus.STARTED;
        BigDecimal senderAccountCacheValue = transaction.getUserTransaction().getSender().getAccount();
        BigDecimal recieverAccountCacheValue = transaction.getUserTransaction().getReciever().getAccount();

        status = PaymentGatewayStatus.PROCESSING;
        
        if(senderAccountCacheValue.subtract(transaction.getSenderDedcutionAmount()).signum() < 0) return PaymentGatewayStatus.NO_ENOUGH_CREDIT.getValue(); 

        try {
            BigDecimal senderAccount = senderAccountCacheValue;
            BigDecimal recieverAccount = recieverAccountCacheValue;

            transaction.getUserTransaction().getSender().setAccount(senderAccount.subtract(transaction.getSenderDedcutionAmount()));
            transaction.getUserTransaction().getReciever().setAccount(recieverAccount.add(transaction.getReceiverAddAmount()));

        } catch (Throwable e) {
            rollback(transaction, senderAccountCacheValue, recieverAccountCacheValue);
            status = PaymentGatewayStatus.FAILED;
            return status.getValue();
        }

        status = PaymentGatewayStatus.SUCCESSEFUL;
        return status.getValue();
    }

    @Override
    public int rollback(PaymentGatewayTransaction transaction, BigDecimal senderOldAccount, BigDecimal recOldAccount) {
        try {
            transaction.getUserTransaction().getSender().setAccount(senderOldAccount);
            transaction.getUserTransaction().getReciever().setAccount(recOldAccount);

        } catch (Exception e) {
            return PaymentGatewayStatus.FAILED.getValue();
        }
        return PaymentGatewayStatus.SUCCESSEFUL.getValue();
    }
}

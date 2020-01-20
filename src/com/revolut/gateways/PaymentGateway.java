package com.revolut.gateways;

import java.math.BigDecimal;

import com.revolut.models.PaymentGatewayTransaction;

public interface PaymentGateway {
    public abstract int transfer(PaymentGatewayTransaction transaction);
    public abstract int rollback(PaymentGatewayTransaction transaction, BigDecimal senderOldAccount, BigDecimal recOldAccount);
}

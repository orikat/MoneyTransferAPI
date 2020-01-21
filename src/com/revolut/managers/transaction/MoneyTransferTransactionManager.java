/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.managers.transaction;

import java.math.BigDecimal;

import com.revolut.Exception.NoEnoughCreditException;
import com.revolut.gateways.DefaultPaymentGateWay;
import com.revolut.gateways.PaymentGateway;
import com.revolut.gateways.PaymentGatewayStatus;
import com.revolut.managers.charging.ChargingManager;
import com.revolut.managers.charging.DefaultChargingManager;
import com.revolut.managers.currency.CurrencyConverter;
import com.revolut.managers.currency.ECBCurrencyConverter;
import com.revolut.models.Invoice;
import com.revolut.models.PaymentGatewayTransaction;
import com.revolut.models.UserTransaction;

/**
 * Process money transfer transaction using charging and currency converter by calling payment gateway 
 * **/

public class MoneyTransferTransactionManager implements TransactionManager {
    
    private ChargingManager chargingManager;
    private PaymentGateway paymentGateWay;
    private CurrencyConverter currencyConverter;
    
    public MoneyTransferTransactionManager() {
        this.chargingManager = new DefaultChargingManager();
        this.paymentGateWay = new DefaultPaymentGateWay();
        this.currencyConverter = new ECBCurrencyConverter();
    }
    
    public MoneyTransferTransactionManager(PaymentGateway gateway, ChargingManager chargingManager, CurrencyConverter currencyConverter) {
        this.chargingManager = chargingManager;
        this.paymentGateWay = gateway;
        this.currencyConverter = currencyConverter;
    }
    
    @Override
    public Invoice processTransaction(UserTransaction userTransaction) throws NoEnoughCreditException, Exception {
        
        BigDecimal fees = chargingManager.calculateChargingFees(userTransaction.getAmount());
        BigDecimal senderTotalDeduction = userTransaction.getAmount().add(fees);
        BigDecimal RecieverAddAmount = currencyConverter.convertCurrency(userTransaction.getSender().getCurrency(), userTransaction.getReciever().getCurrency(), userTransaction.getAmount());
        
        PaymentGatewayTransaction transaction = new PaymentGatewayTransaction(userTransaction, senderTotalDeduction, RecieverAddAmount);
        int status = paymentGateWay.transfer(transaction);
        
        if (status == PaymentGatewayStatus.FAILED.getValue()) throw new Exception("Failed to process transaction");
        if (status == PaymentGatewayStatus.NO_ENOUGH_CREDIT.getValue()) throw new NoEnoughCreditException("Sender Credit not enough");
        
        Invoice invoice = new Invoice();
        invoice.setId(userTransaction.getId());
        invoice.setFeesPercentage(DefaultChargingManager.getComissionFeePercentage().floatValue());
        invoice.setTaxPercentage(DefaultChargingManager.getTaxFeePercentage().floatValue());
        invoice.setChargingFees(fees);
        invoice.setSenderTransferedAmount(userTransaction.getAmount());
        invoice.setRecieverTransferedAmount(RecieverAddAmount);
        invoice.setSenderAccount(transaction.getUserTransaction().getSender());
        invoice.setRecieverAccount(transaction.getUserTransaction().getReciever());

        return invoice;
    }
}

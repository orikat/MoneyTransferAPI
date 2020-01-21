/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.models;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Invoice {
    
    private long id;
    private BigDecimal chargingFees;
    private BigDecimal senderTransferedAmount;
    private BigDecimal recieverTransferedAmount;
    private UserAccount senderAccount;
    private UserAccount recieverAccount;
    private float taxPercentage;
    private float feesPercentage;
    
    public long getId() {
        return id;
    }
    
    public BigDecimal getChargingFees() {
        return chargingFees;
    }
    
    public Invoice setId(long id) {
        this.id = id;
        return this;
    }
    
    public Invoice setChargingFees(BigDecimal chargingFees) {
        this.chargingFees = chargingFees;
        return this;
    }
    
    public float getTaxPercentage() {
        return taxPercentage;
    }
    
    public float getFeesPercentage() {
        return feesPercentage;
    }
    
    public Invoice setTaxPercentage(float taxPercentage) {
        this.taxPercentage = taxPercentage;
        return this;
    }
    
    public Invoice setFeesPercentage(float feesPercentage) {
        this.feesPercentage = feesPercentage;
        return this;
    }
    
    public BigDecimal getSenderTransferedAmount() {
        return senderTransferedAmount;
    }
    
    public BigDecimal getRecieverTransferedAmount() {
        return recieverTransferedAmount;
    }
    
    public Invoice setSenderTransferedAmount(BigDecimal senderTransferedAmount) {
        this.senderTransferedAmount = senderTransferedAmount;
        return this;
    }
    
    public Invoice setRecieverTransferedAmount(BigDecimal recieverTransferedAmount) {
        this.recieverTransferedAmount = recieverTransferedAmount;
        return this;
    }

    public UserAccount getSenderAccount() {
        return senderAccount;
    }

    public UserAccount getRecieverAccount() {
        return recieverAccount;
    }

    public Invoice setSenderAccount(UserAccount senderAccount) {
        this.senderAccount = senderAccount;
        return this;
    }

    public Invoice setRecieverAccount(UserAccount recieverAccount) {
        this.recieverAccount = recieverAccount;
        return this;
    }
    
}

/*
 * Copyright (c) 2020-present Revolute. All Rights Reserved.
 *
 * Licensed Material - Property of Revolute.
 */

package com.revolut.models;

import java.math.BigDecimal;

public class UserTransaction {
    private long id;
    private UserAccount sender;
    private UserAccount reciever;
    private BigDecimal amount;
    
    public UserTransaction() {}
    
    public UserTransaction(long id, UserAccount sender, UserAccount reciever, BigDecimal amount) {
     this.id = id;
     this.sender = sender;
     this.reciever = reciever;
     this.amount = amount;
    }
    
    public long getId() {
        return id;
    }
    public UserAccount getSender() {
        return sender;
    }
    public UserAccount getReciever() {
        return reciever;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setSender(UserAccount sender) {
        this.sender = sender;
    }
    public void setReciever(UserAccount reciever) {
        this.reciever = reciever;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

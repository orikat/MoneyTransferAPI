package com.revolut.models;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionParam {
    private long senderID;
    private long receiverID;
    private BigDecimal amount;
    
    public TransactionParam() {}
    
    public TransactionParam(long senderID, long receiverID, BigDecimal amount) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
    }

    public long getSenderID() {
        return senderID;
    }

    public long getReceiverID() {
        return receiverID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(long receiverID) {
        this.receiverID = receiverID;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

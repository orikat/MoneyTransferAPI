package com.revolut.models;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserAccount {
   
    private long id;
    private String name;
    private String email;
    private long accountNumber;
    private BigDecimal accountValue;
    private String currency;

    public UserAccount() {}
    
    public UserAccount(long id, String name, String email, long accountNumber, BigDecimal account, String currency) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountValue = account;
        this.currency = currency;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountNumber(long bankAccountNumber) {
        this.accountNumber = bankAccountNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAccount() {
        return accountValue;
    }

    public void setAccount(BigDecimal account) {
        this.accountValue = account;
    }
}

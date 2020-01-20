package com.revolut.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
    private int status;
    private String message;
    private Invoice invoice;

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    
}

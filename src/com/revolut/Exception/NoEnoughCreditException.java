package com.revolut.Exception;

public class NoEnoughCreditException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public NoEnoughCreditException(String message) {
        super(message);
    }
    
}

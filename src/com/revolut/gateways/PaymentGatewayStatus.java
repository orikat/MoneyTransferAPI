package com.revolut.gateways;

public enum PaymentGatewayStatus {
    STARTED(0),
    SUCCESSEFUL(1),
    PROCESSING(2),
    FAILED(-1),
    NO_ENOUGH_CREDIT(-2);
    
    
    private int value;
    
    PaymentGatewayStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package com.revolut.managers.charging;

import java.math.BigDecimal;

public interface ChargingManager {

    abstract BigDecimal calculateChargingFees(BigDecimal amount);

}
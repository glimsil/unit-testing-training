package com.github.diegopacheco.xunit.testing.bank.model;

import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;

import java.math.BigDecimal;

public class SavingAccount extends Account {

    private Double minValue = 100D;
    private Double rentTaxPercent = 1.22D;

    public SavingAccount(BigDecimal initialValue) throws InitialValueException {
        if(initialValue.compareTo(BigDecimal.valueOf(minValue)) < 0 ) {
            throw new InitialValueException("Initial value shuld be at least " + 100D);
        }
        if(initialValue.compareTo(BigDecimal.valueOf(0)) < 0 ) {
            throw new InitialValueException("Initial value cant be negative");
        }
        setWithdrawTax(BigDecimal.valueOf(2));
        setTransferTax(BigDecimal.valueOf(5));
        this.setAmount(initialValue);
    }

    public void updateYield() {
        this.setAmount(this.getAmount().multiply(BigDecimal.valueOf(rentTaxPercent)));
    }
}

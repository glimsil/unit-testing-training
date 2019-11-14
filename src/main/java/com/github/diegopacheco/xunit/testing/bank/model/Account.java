package com.github.diegopacheco.xunit.testing.bank.model;

import com.github.diegopacheco.xunit.testing.bank.exception.InvalidAmountException;

import java.math.BigDecimal;

public abstract class Account{
    private Integer id;
    private Integer accountNumber;
    private BigDecimal amount;
    private BigDecimal transferTax;
    private BigDecimal withdrawTax;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal withdraw(BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Negative amount");
        }
        if(amount.compareTo(this.amount) > 0) {
            throw new InvalidAmountException("You dont have this amount to withdraw");
        }
        this.amount = this.amount.subtract(amount);
        return applyRealTax(amount, withdrawTax);
    }

    public BigDecimal transfer(Account to, BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Negative amount");
        }
        if(amount.compareTo(this.amount) > 0) {
            throw new InvalidAmountException("You dont have this amount to transfer");
        }
        BigDecimal sendAmount;

        if (this instanceof SavingAccount && to instanceof CheckingAccount) {
            sendAmount = applyRealTax(amount, transferTax.add(withdrawTax));
        } else {
            sendAmount = applyRealTax(amount, transferTax);
        }

        this.amount = this.amount.subtract(amount);
        to.setAmount(to.getAmount().add(sendAmount));
        return sendAmount;
    }

    public BigDecimal deposit(BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Negative amount");
        }
        this.amount = this.amount.add(amount);
        return this.amount;
    }

    public BigDecimal check() {
        return this.amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTransferTax() {
        return transferTax;
    }

    public void setTransferTax(BigDecimal transferTax) {
        this.transferTax = transferTax;
    }

    public BigDecimal getWithdrawTax() {
        return withdrawTax;
    }

    public void setWithdrawTax(BigDecimal withdrawTax) {
        this.withdrawTax = withdrawTax;
    }

    private BigDecimal applyRealTax(BigDecimal value, BigDecimal tax) {
        BigDecimal realTax = BigDecimal.valueOf(100).subtract(tax);
        realTax = realTax.divide(BigDecimal.valueOf(100));
        return value.multiply(realTax);
    }

    private BigDecimal getRealTax(BigDecimal tax) {
        BigDecimal realTax = BigDecimal.valueOf(100).subtract(tax);
        return realTax.divide(BigDecimal.valueOf(100));
    }
}
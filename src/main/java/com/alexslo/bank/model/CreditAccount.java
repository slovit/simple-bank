package com.alexslo.bank.model;

import java.math.BigDecimal;

public class CreditAccount extends Account {
    private BigDecimal creditLimit;
    private double interestRate;
    private BigDecimal creditInterest;

    public CreditAccount(int userId, int accountId) {
        super(userId, accountId);
        this.creditLimit = BigDecimal.ZERO;
        this.interestRate = 0.0;
    }

    public BigDecimal getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(BigDecimal creditInterest) {
        this.creditInterest = creditInterest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setCreditLimit(BigDecimal creditLimit){
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    @Override
    public String toString() {
        return String.format("Account balance: %f\nCredit limit: %f",
                super.getBalance(),
                creditLimit);
    }
}

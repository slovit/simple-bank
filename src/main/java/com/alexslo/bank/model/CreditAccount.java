package com.alexslo.bank.model;

import java.math.BigDecimal;

public class CreditAccount extends Account {

    private BigDecimal creditLimit;

    public CreditAccount(int userId, int accountId) {
        super(userId, accountId);
        this.creditLimit = BigDecimal.ZERO;
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

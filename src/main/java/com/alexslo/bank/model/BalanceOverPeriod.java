package com.alexslo.bank.model;

import java.math.BigDecimal;

public class BalanceOverPeriod {
    private BigDecimal balance;
    private int days;

    public BalanceOverPeriod(BigDecimal balance, int days) {
        this.balance = balance;
        this.days = days;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getDays() {
        return days;
    }
}

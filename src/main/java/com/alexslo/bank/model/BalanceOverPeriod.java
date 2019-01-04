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

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof BalanceOverPeriod)) return false;
        BalanceOverPeriod balanceOverPeriod = (BalanceOverPeriod) object;
        return this.getDays() == balanceOverPeriod.getDays() && this.getBalance().equals(balanceOverPeriod
                .getBalance());
    }
}

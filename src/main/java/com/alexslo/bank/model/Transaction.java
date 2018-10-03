package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int fromAccountId;
    private int toAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public Transaction(int formAccountId, int toAccountId, BigDecimal amount) {
        this.fromAccountId = formAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return String.format("Transaction date/time: %tD\nFrom account: %d\nTo account: %d\nAmount: %f\n\n",
                transactionDate,
                fromAccountId,
                toAccountId,
                amount);
    }
}

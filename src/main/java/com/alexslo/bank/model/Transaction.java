package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int fromAccount;
    private int toAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public Transaction(int formAccountId, int toAccountId, BigDecimal amount, LocalDateTime transactionDate) {
        this.fromAccount = formAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getFromAccount() {
        return fromAccount;
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
//        return String.format("From account: " + fromAccount + " to account: " + toAccountId
//                + ". Amount: " + amount + "\n");
        return String.format("Transaction date/time: %tD\nFrom account: %d\nTo account: %d\nAmoount: %f\n\n",
                transactionDate,
                fromAccount,
                toAccountId,
                amount);
    }
}

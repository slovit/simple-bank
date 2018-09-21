package com.alexslo.bank.model;

import java.time.LocalDateTime;

public class Transaction {

    private int accountId;
    private int fromAccount;
    private int toAccountId;
    private double amount;
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(int formAccountId, int toAccountId, double amount, LocalDateTime transactionDate) {
        this.fromAccount = formAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return String.format("From account: " + fromAccount + " to account: " + toAccountId
                + ". Amount: " + amount + "\n");
    }
}

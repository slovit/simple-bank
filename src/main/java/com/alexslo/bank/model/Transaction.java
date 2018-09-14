package com.alexslo.bank.model;

import java.time.LocalDateTime;

public class Transaction {
    private int account_id;
    private int fromAccount;
    private int toAccountId;
    private double amount;
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(int formAccountId, int toAccountId, double amount, LocalDateTime transactionDate) {
     //   this.account_id = account_id;
        this.fromAccount = formAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
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
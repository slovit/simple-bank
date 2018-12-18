package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int fromAccountId;
    private int toAccountId;
    private BigDecimal amount;
    private BigDecimal accountBalance;
    private LocalDateTime transactionDate;

    public Transaction(int formAccountId, int toAccountId, BigDecimal amount, BigDecimal accountBalance) {
        this.fromAccountId = formAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.accountBalance = accountBalance;
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

    public BigDecimal getAccountBalance(){
        return accountBalance;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime localDateTime){
        this.transactionDate = localDateTime;
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

package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Account {

    private int id;
    private int userId;
    private BigDecimal balance;
    private LocalDateTime creationDate;
    private BigDecimal creditInterest;


    protected Account(int userId, int accountID) {
        this.userId = userId;
        this.id = accountID;
        this.creationDate = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void subtractBalance(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }

    public void addToBalance(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public int getUserId() {
        return userId;
    }
}

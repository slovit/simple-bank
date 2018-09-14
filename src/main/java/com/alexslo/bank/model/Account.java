package com.alexslo.bank.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;

public class Account {
    private int id;
    private int userId;
    private BigDecimal balance;
    private AccountType accountType;
    private LocalDateTime creationDate;

    public Account(){

    }

    public Account(int userId, int accountID, BigDecimal balance, AccountType accountType, LocalDateTime creationDate) {
        this.userId = userId;
        this.id = accountID;
        this.balance = balance;
        this.accountType = accountType;
        this.creationDate = creationDate;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate.withNano(0);
    }

    public void getSubstractBalance(BigDecimal amount){
        this.balance = balance.subtract(amount);
    }

    public void addToBalance(BigDecimal amount){
        this.balance = balance.add(amount);
    }

    public int getUserId(){
        return userId;
    }
}

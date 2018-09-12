package com.alexslo.bank.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {
    private int id;
    private int user_id;
    private double balance;
    private AccountType accountType;
    private LocalDateTime creationDate;
    private LocalDate expirationDate;

    public Account(){

    }

    public Account(int user_id, int accountID, double balance, AccountType accountType, LocalDateTime creationDate, LocalDate expirationDate) {
        this.user_id = user_id;
        this.id = accountID;
        this.balance = balance;
        this.accountType = accountType;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate.withNano(0);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void getFromBalance(double amount){
        this.balance = balance - amount;
    }

    public void addToBalance(double amount){
        this.balance = balance + amount;
    }

    public void setUser_id(int id){
        this.user_id = id;
    }

    public int getUser_id(){
        return user_id;
    }

}

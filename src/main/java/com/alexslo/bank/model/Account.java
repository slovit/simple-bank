package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Account {

    private int id;
    private int userId;
    private BigDecimal balance;
    private LocalDateTime creationDate;
    private BigDecimal creditInterest;
    private double interestRate;

    protected Account(int userId, int accountID) {
        this.userId = userId;
        this.id = accountID;
        this.creationDate = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
        this.creditInterest = BigDecimal.ZERO;
        this.interestRate = 0.0;
    }

    public void setCreditInterest(BigDecimal creditInterest){
        this.creditInterest = creditInterest;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public BigDecimal getCreditInterest(){
        return creditInterest;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public double getMonthlyInterest(){
        return interestRate/12;
    }

    public double getDailyInterest(){
        return interestRate/365;
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

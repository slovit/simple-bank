package com.alexslo.bank.model;

import java.time.LocalDateTime;

public class SavingAccount extends Account {

    private LocalDateTime depositExpireDate;
    private double interestRate;

    public SavingAccount(int userId, int accountId, double interestRate) {
        super(userId, accountId);
        this.interestRate = interestRate;
        this.depositExpireDate = super.getCreationDate().plusYears(1);
    }

    public void setDepositExpireDate(LocalDateTime depositExpireDate) {
        this.depositExpireDate = depositExpireDate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public LocalDateTime getDepositExpireDate() {
        return this.depositExpireDate;
    }

    @Override
    public String toString() {
        return String.format("Account balance: %f\nInterest rate: %f\n" +
                        "Deposit expiration date: %tD",
                super.getBalance(),
                interestRate,
                depositExpireDate);
    }
}

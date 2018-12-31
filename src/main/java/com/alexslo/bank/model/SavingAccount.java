package com.alexslo.bank.model;

public class SavingAccount extends Account {
    private double interestRate;

    public SavingAccount(int userId, int accountId, double interestRate) {
        super(userId, accountId);
        this.interestRate = interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return String.format("Account balance: %f\n",
                super.getBalance());
    }
}

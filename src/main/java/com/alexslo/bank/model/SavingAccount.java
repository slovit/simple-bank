package com.alexslo.bank.model;

public class SavingAccount extends Account {

    public SavingAccount(int userId, int accountId, double interestRate) {
        super(userId, accountId);
        super.setInterestRate(interestRate);
    }

    @Override
    public String toString() {
        return String.format("Account balance: %f\n",
                super.getBalance());
    }
}

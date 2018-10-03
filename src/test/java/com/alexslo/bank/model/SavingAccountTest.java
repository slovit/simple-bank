package com.alexslo.bank.model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class SavingAccountTest {

    @Test
    public void setAndGetDepositExpireDateTest() {
        SavingAccount savingAccount = new SavingAccount(2, 3, 20.0);
        LocalDateTime dateTime = LocalDateTime.now();
        savingAccount.setDepositExpireDate(dateTime);
        Assert.assertEquals(dateTime, savingAccount.getDepositExpireDate());
    }

    @Test
    public void setAndGetInterestRate() {
        SavingAccount savingAccount = new SavingAccount(2, 3, 20.0);
        double interestRate = 30.0;
        savingAccount.setInterestRate(interestRate);
        Assert.assertEquals(interestRate, savingAccount.getInterestRate(), 0);
    }
}
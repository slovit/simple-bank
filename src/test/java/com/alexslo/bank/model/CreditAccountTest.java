package com.alexslo.bank.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CreditAccountTest {

    @Test
    public void getCreditLimitTest() {
        CreditAccount creditAccount = new CreditAccount(1, 1);
        BigDecimal creditlimit = BigDecimal.valueOf(1000);
        creditAccount.setCreditLimit(creditlimit);
        Assert.assertEquals(creditlimit, creditAccount.getCreditLimit());
    }

    @Test
    public void getCreditInterest() {
        CreditAccount creditAccount = new CreditAccount(1, 1);
        BigDecimal creditInterest = BigDecimal.valueOf(1000);
        creditAccount.setCreditInterest(creditInterest);
        Assert.assertEquals(creditInterest, creditAccount.getCreditInterest());
    }

    @Test
    public void getInterestRate() {
        CreditAccount creditAccount = new CreditAccount(1, 1);
        double interestRate = 12.2;
        creditAccount.setInterestRate(interestRate);
        Assert.assertEquals(interestRate, creditAccount.getInterestRate(), 0);
    }
}

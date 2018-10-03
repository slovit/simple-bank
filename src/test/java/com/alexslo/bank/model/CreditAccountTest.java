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
}

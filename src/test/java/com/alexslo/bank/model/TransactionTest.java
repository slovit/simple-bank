package com.alexslo.bank.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class TransactionTest {
    private Transaction transaction;

    @Before
    public void init() {
        transaction = new Transaction(1, 2, BigDecimal.TEN);
    }

    @Test
    public void getFromAccountIdTest() {
        Assert.assertEquals(1, transaction.getFromAccountId());
    }

    @Test
    public void getToAccountIdTest() {
        Assert.assertEquals(2, transaction.getToAccountId());
    }

    @Test
    public void getTransactionDateTest() {
        LocalDateTime currentDate = LocalDateTime.now();
        Assert.assertEquals(currentDate, transaction.getTransactionDate());
    }

    @Test
    public void getAmountTest() {
        Assert.assertEquals(BigDecimal.TEN, transaction.getAmount());
    }

    @Test
    public void compareToEqualDateTest() {
        Transaction transaction1 = new Transaction(1, 1, BigDecimal.TEN);
        Transaction transaction2 = new Transaction(1, 1, BigDecimal.TEN);
        assertEquals(0, transaction1.compareTo(transaction2));
    }

    @Test
    public void compareToDifferentDateTest() {
        Transaction transaction1 = new Transaction(1, 1, BigDecimal.TEN);
        transaction1.setTransactionDate(LocalDateTime.now().minusSeconds(10));
        Transaction transaction2 = new Transaction(1, 1, BigDecimal.TEN);
        assertEquals(-1, transaction1.compareTo(transaction2));
    }
}
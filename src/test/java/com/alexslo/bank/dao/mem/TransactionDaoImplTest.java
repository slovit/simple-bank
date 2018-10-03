package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class TransactionDaoImplTest {
    private TransactionDao transactionDao;
    private Random random;
    @Before
    public void init() {
        transactionDao = new TransactionDaoImpl();
        random = new Random();
    }

    @Test
    public void addTransactionTest() {
        int fromAcountId = Math.abs(random.nextInt());
        int toAccountId = Math.abs(random.nextInt());
        Transaction transaction = new Transaction(fromAcountId, toAccountId, BigDecimal.valueOf(100));
        transactionDao.addTransaction(fromAcountId, transaction);
        List<Transaction> transactions = transactionDao.getTransactionsByAccountId(fromAcountId);
        Assert.assertEquals(1, transactions.size());
        Assert.assertEquals(transaction, transactions.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionIncorrectAccountIdTest() {
        int fromaAcountId = Math.abs(random.nextInt());
        int toAccountId = Math.abs(random.nextInt());
        Transaction transaction = new Transaction(fromaAcountId, toAccountId, BigDecimal.valueOf(100));
        transactionDao.addTransaction(-1, transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionNullParameterTest() {
        transactionDao.addTransaction(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTransactionsByAccountIdIllegalArgumentExceptionTest() {
        transactionDao.getTransactionsByAccountId(-1);
    }

    @Test
    public void getTransactionsByAccountIdTest() {
        int fromaAcountId = Math.abs(random.nextInt());
        int toAccountId = Math.abs(random.nextInt());
        Transaction transaction1 = new Transaction(fromaAcountId, toAccountId, BigDecimal.valueOf(100));
        Transaction transaction2 = new Transaction(toAccountId, fromaAcountId, BigDecimal.valueOf(100));
        transactionDao.addTransaction(fromaAcountId, transaction1);
        transactionDao.addTransaction(fromaAcountId, transaction2);
        List<Transaction> transactions = transactionDao.getTransactionsByAccountId(fromaAcountId);
        Assert.assertEquals(transaction1, transactions.get(0));
        Assert.assertEquals(transaction2, transactions.get(1));
        Assert.assertEquals(2, transactions.size());
    }

    @Test
    public void getTransactionsByAccountIdEmptyListTest() {
        List<Transaction> transactions;
        transactions = transactionDao.getTransactionsByAccountId(1);
        Assert.assertTrue(transactions.isEmpty());
    }
}
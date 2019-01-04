package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

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
        int fromAccountId = Math.abs(random.nextInt());
        int toAccountId = Math.abs(random.nextInt());
        Transaction transaction = new Transaction(fromAccountId, toAccountId, BigDecimal.valueOf(100));
        transactionDao.addTransaction(fromAccountId, transaction);
        List<Transaction> transactions = transactionDao.getTransactionsByAccountId(fromAccountId);
        Assert.assertEquals(1, transactions.size());
        Assert.assertEquals(transaction, transactions.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTransactionIncorrectAccountIdTest() {
        int fromAccountId = Math.abs(random.nextInt());
        int toAccountId = Math.abs(random.nextInt());
        Transaction transaction = new Transaction(fromAccountId, toAccountId, BigDecimal.valueOf(100));
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
        initData();
        List<Transaction> transactionList = transactionDao.getTransactionsByAccountId(1);
        Assert.assertEquals(5, transactionList.size());
    }

    @Test
    public void getTransactionsByAccountIdEmptyListTest() {
        initData();
        List<Transaction> sortedList = transactionDao.getSortedTransactions(1);
        assertTrue(sortedList.get(0).getTransactionDate().isBefore(sortedList.get(1).getTransactionDate()));
    }

    @Test
    public void getTransactionsOlderThanTest() {
        initData();
        List<Transaction> filtered = transactionDao
                .getTransactionsOlderThan(1, LocalDateTime.now().minusMonths(2).getMonthValue());
        assertEquals(2, filtered.size());
    }

    @Test
    public void getAccountTransactionsForMonthTest(){
        initData();
        List<Transaction> transactions = transactionDao.getAccountTransactionsForMonth(1,11);
        assertEquals(2, transactions.size());
    }

    private void initData() {
        Transaction transaction1 = new Transaction(1, 2, BigDecimal.ONE);
        transaction1.setTransactionDate(LocalDateTime.now().minusMonths(2).minusSeconds(10));
        Transaction transaction2 = new Transaction(1, 2, BigDecimal.ONE);
        transaction2.setTransactionDate(LocalDateTime.now().minusMonths(3));
        Transaction transaction3 = new Transaction(1, 2, BigDecimal.ONE);
        transaction3.setTransactionDate(LocalDateTime.now().minusMonths(3).minusSeconds(10));
        Transaction transaction4 = new Transaction(1, 2, BigDecimal.ONE);
        transaction4.setTransactionDate(LocalDateTime.now().minusMonths(3).minusSeconds(9));
        Transaction transaction5 = new Transaction(1, 2, BigDecimal.ONE);
        transaction5.setTransactionDate(LocalDateTime.now().minusMonths(2));
        transactionDao.addTransaction(1, transaction5);
        transactionDao.addTransaction(1, transaction4);
        transactionDao.addTransaction(1, transaction3);
        transactionDao.addTransaction(1, transaction2);
        transactionDao.addTransaction(1, transaction1);
    }
}
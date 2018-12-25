package com.alexslo.bank.service;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.dao.mem.TransactionDaoImpl;
import com.alexslo.bank.model.SavingAccount;
import com.alexslo.bank.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InterestCalculationServiceTest {

    private TransactionDao transactionDao;
    private SavingAccount savingAccount;
    private final int MONTH = 12;

    @Before
    public void init() {
        transactionDao = new TransactionDaoImpl();
        initData();
    }

    //According to initialized data, there must be 7 transactions that was made on needed month
    @Test
    public void getAccountTransactionsForNeededMonthTest() throws Exception {
        List<Transaction> transactionList = Whitebox.invokeMethod(new InterestCalculationService(transactionDao),
                "getAccountTransactionsForNeededMonth", savingAccount, MONTH);
        Assert.assertEquals(7, transactionList.size());
    }

    @Test
    public void getAccountBalanceAtMonthStartTest() throws Exception {
        BigDecimal accountBalanceArMonthStart = BigDecimal.valueOf(100);
        BigDecimal result = Whitebox.invokeMethod(new InterestCalculationService(transactionDao),
                "getAccountBalanceAtMonthStart", savingAccount, MONTH);
        Assert.assertEquals(accountBalanceArMonthStart, result);
    }

    //According to initialized data, number of periods ( balance was unchanged) is 6
    @Test
    public void getNumberOfBalanceChangesPeriodInDaysTest() throws Exception {
        List<Transaction> transactionList = Whitebox.invokeMethod(new InterestCalculationService(transactionDao),
                "getAccountTransactionsForNeededMonth", savingAccount, MONTH);
        List<Integer> periods = Whitebox.invokeMethod(new InterestCalculationService(transactionDao),
                "getNumberOfBalanceChangesPeriodInDays", transactionList);
        Assert.assertEquals(6, periods.size());
    }

    private void initData() {
        /*
        Init data into TransactionDao and SavingAccount
        Consider that there will be 9 transactions, 7 of them on needed month
        _______________________________________________________________________________________________________
        1 Tr from previous month, 1 Tr from next month, 2 Tr at the same day,
        2 Tr with date difference 1 day, 2 Tr on different days of month and one Tr on the last day of month
        _______________________________________________________________________________________________________
        Account balance set : 800
        8 transactions add balance, 1 transaction subtract balance
        1 Tr was on previous month, so balance at the start of a month is 100
        Periods: 6
         */
        Random random = new Random();
        int accountId = Math.abs(random.nextInt());
        int anotherId = Math.abs(random.nextInt());
        int userId = Math.abs(random.nextInt());
        savingAccount = new SavingAccount(userId, accountId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int numberOfDaysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        //consider that interest rate will be 1% per day
        savingAccount.setInterestRate(numberOfDaysInYear);
        LocalDateTime time = LocalDateTime.of(2018, MONTH, 24, 13, 13);
        savingAccount.setBalance(BigDecimal.valueOf(800));
        Transaction transaction1 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        Transaction transactionM = new Transaction(accountId, anotherId, BigDecimal.valueOf(100));
        transaction1.setTransactionDate(time.minusMonths(1));
        transactionDao.addTransaction(accountId, transaction1);
        Transaction transaction2 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction2.setTransactionDate(time.minusDays(22));
        transactionDao.addTransaction(accountId, transaction2);
        transactionDao.addTransaction(accountId, transaction2);
        Transaction transaction3 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction3.setTransactionDate(time.minusDays(15));
        transactionDao.addTransaction(accountId, transaction3);
        Transaction transaction4 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction4.setTransactionDate(time.minusDays(14));
        transactionDao.addTransaction(accountId, transaction4);
        transactionM.setTransactionDate(time.minusDays(6));
        transactionDao.addTransaction(accountId, transactionM);
        Transaction transaction5 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction5.setTransactionDate(time.plusDays(5));
        transactionDao.addTransaction(accountId, transaction5);
        Transaction transaction6 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction6.setTransactionDate(time.plusDays(7));
        transactionDao.addTransaction(accountId, transaction6);
        Transaction transaction7 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        transaction7.setTransactionDate(time.plusDays(12));
        transactionDao.addTransaction(accountId, transaction7);
    }
}
package com.alexslo.bank.service;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.BalanceOverPeriod;
import com.alexslo.bank.model.SavingAccount;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

import com.alexslo.bank.model.Transaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InterestCalculationServiceTest {

    private SavingAccount savingAccount;
    private List<Transaction> transactions;
    private final int MONTH = 12;

    @Mock
    private TransactionDao transactionDaoMock;

    @InjectMocks
    private InterestCalculationService interestService = new InterestCalculationService(transactionDaoMock);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initData();
    }

    @Test
    public void getAccountTransactionsForMonthTest() {
        setGetTransactionsByAccIdBehaviour();
        List<Transaction> monthTransactions = interestService.getAccountTransactionsForMonth(savingAccount, MONTH);
        assertNotNull(monthTransactions);
        assertEquals(7, monthTransactions.size());
    }

    @Test
    public void getAccountBalanceAtMonthEndTest() {
        setGetSortedTransactionsBehaviour();
        BigDecimal accountBalance = interestService.getAccountBalanceAtMonthEnd(savingAccount, 12);
        assertNotNull(accountBalance);
        assertEquals(BigDecimal.valueOf(600), accountBalance);
    }

    @Test
    public void getAccountBalanceAtMonthEndZeroTransactionsTest() {
        BigDecimal accountBalance = interestService.getAccountBalanceAtMonthEnd(savingAccount, 12);
        assertNotNull(accountBalance);
        assertEquals(BigDecimal.ZERO, accountBalance);
    }

    @Test
    public void calcTransactionBalanceTest() {
        Transaction addBalanceTransaction = transactions.get(3);
        Transaction subtractBalanceTransaction = transactions.get(5);
        BigDecimal accountBalance = BigDecimal.valueOf(500);
        assertEquals(BigDecimal.valueOf(400),
                interestService.calcBalanceBeforeTransaction(addBalanceTransaction, 1, accountBalance));
        assertEquals(BigDecimal.valueOf(600),
                interestService.calcBalanceBeforeTransaction(subtractBalanceTransaction, 1, accountBalance));
    }

    @Test
    public void calcDailyInterestTest() {
        assertEquals(1.0, interestService.calcDailyInterest(savingAccount));
    }

    @Test
    public void getBalancesOverPeriodsTest() {
        List<Transaction> monthTransactions = transactions;
        monthTransactions.remove(0);
        monthTransactions.remove(transactions.size() - 1);
        BigDecimal accBalance = BigDecimal.valueOf(600);
        List<BalanceOverPeriod> balances = interestService.getBalancesOverPeriods(monthTransactions, 1, accBalance);
        assertNotNull(balances);
        Assert.assertThat(balances,
                containsInAnyOrder(new BalanceOverPeriod(BigDecimal.valueOf(500), 2),
                        new BalanceOverPeriod(BigDecimal.valueOf(400), 11),
                        new BalanceOverPeriod(BigDecimal.valueOf(500), 8),
                        new BalanceOverPeriod(BigDecimal.valueOf(400), 1),
                        new BalanceOverPeriod(BigDecimal.valueOf(300), 7),
                        new BalanceOverPeriod(BigDecimal.valueOf(200), 0),
                        new BalanceOverPeriod(BigDecimal.valueOf(100), 2)
                ));
    }

    @Test
    public void calcInterestTest() {
        setGetTransactionsByAccIdBehaviour();
        setGetSortedTransactionsBehaviour();
        List<Transaction> transactions = interestService.getAccountTransactionsForMonth(savingAccount, MONTH);
        BigDecimal accBalance = interestService.getAccountBalanceAtMonthEnd(savingAccount, MONTH);
        List<BalanceOverPeriod> balances = interestService.getBalancesOverPeriods(transactions, 1, accBalance);
        BigDecimal calculatedInterest = interestService.calcInterest(1.0, balances);
        assertNotNull(calculatedInterest);
        assertEquals(BigDecimal.valueOf(121.0), calculatedInterest);
    }

    @Test
    public void calcSavingAccountInterestTest() {
        setGetTransactionsByAccIdBehaviour();
        setGetSortedTransactionsBehaviour();
        BigDecimal balance = interestService.calcSavingAccountInterest(savingAccount, MONTH);
        assertEquals(BigDecimal.valueOf(121.0), balance);
    }

    @Test
    public void addInterestToBalanceTest() {
        setGetTransactionsByAccIdBehaviour();
        setGetSortedTransactionsBehaviour();
        interestService.addInterestToBalance(savingAccount, MONTH);
        assertEquals(BigDecimal.valueOf(821.0), savingAccount.getBalance());
        verify(transactionDaoMock, times(1)).addTransaction(anyInt(), any(Transaction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addInterestToBalanceNullAccountArgumentTest() {
        interestService.addInterestToBalance(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addInterestToBalanceIncorrectMonthTest() {
        interestService.addInterestToBalance(savingAccount, 13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcSavingAccountInterestNullAccountArgumentTest() {
        interestService.calcSavingAccountInterest(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcSavingAccountInterestIncorrectMonthTest() {
        interestService.calcSavingAccountInterest(savingAccount, 13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccountTransactionsForMonthNullAccountArgumentTest() {
        interestService.getAccountTransactionsForMonth(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccountTransactionsForMonthIncorrectMonthTest() {
        interestService.getAccountTransactionsForMonth(savingAccount, 13);
    }

    @Test
    public void getBalancesOverPeriodsEmptyTransactionListTest() {
        List<Transaction> transactions = new ArrayList<>();
        List<BalanceOverPeriod> list = interestService.getBalancesOverPeriods(transactions, 1, BigDecimal.TEN);
        assertEquals(list, Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBalancesOverPeriodsIncorrectAccountIdTest() {
        interestService.getBalancesOverPeriods(transactions, -1, BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcDailyInterestNullArgumentTest() {
        interestService.calcDailyInterest(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcInterestIncorrectArgumentsTest() {
        List<BalanceOverPeriod> balance = new ArrayList<>(0);
        interestService.calcInterest(0.0, balance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccountBalanceAtMonthEndIncorrectArgumentsTest() {
        interestService.getAccountBalanceAtMonthEnd(savingAccount, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcTransactionBalanceIncorrectArgumentsTest() {
        interestService.calcBalanceBeforeTransaction(null, -2, BigDecimal.ZERO);
    }

    private void setGetTransactionsByAccIdBehaviour() {
        when(transactionDaoMock.getTransactionsByAccountId(1)).thenReturn(transactions);
    }

    private void setGetSortedTransactionsBehaviour() {
        List<Transaction> sortedTr = transactions;
        sortedTr.remove(0);
        when(transactionDaoMock.getTransactionsOlderThan(anyInt(), anyInt())).thenReturn(transactions);
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
        consider that interest rate will be 1% per day
         */
        int accountId = 1;
        int userId = 2;
        int anotherId = 3;
        int numberOfDaysInYear = 365;
        transactions = new ArrayList<>();
        savingAccount = new SavingAccount(userId, accountId, numberOfDaysInYear);
        savingAccount.setBalance(BigDecimal.valueOf(700));
        LocalDateTime time24_11 = LocalDateTime.of(2018, 11, 24, 13, 13);
        LocalDateTime time02_12 = LocalDateTime.of(2018, MONTH, 2, 13, 13);
        LocalDateTime time09_12 = LocalDateTime.of(2018, MONTH, 9, 13, 13);
        LocalDateTime time10_12 = LocalDateTime.of(2018, MONTH, 10, 13, 13);
        LocalDateTime time18_12 = LocalDateTime.of(2018, MONTH, 18, 13, 13);
        LocalDateTime time29_12 = LocalDateTime.of(2018, MONTH, 29, 13, 13);
        LocalDateTime time31_12 = LocalDateTime.of(2018, MONTH, 31, 13, 13);
        LocalDateTime time04_01 = LocalDateTime.of(2019, 1, 4, 13, 13);
        Transaction tr24_11 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr24_11.setTransactionDate(time24_11);
        Transaction tr02_12 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr02_12.setTransactionDate(time02_12);
        Transaction tr02_12_2 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr02_12_2.setTransactionDate(time02_12);
        Transaction tr09_12 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr09_12.setTransactionDate(time09_12);
        Transaction tr10_12 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr10_12.setTransactionDate(time10_12);
        Transaction tr18_12 = new Transaction(accountId, anotherId, BigDecimal.valueOf(100));
        tr18_12.setTransactionDate(time18_12);
        Transaction tr29_12 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr29_12.setTransactionDate(time29_12);
        Transaction tr31_12 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr31_12.setTransactionDate(time31_12);
        Transaction tr04_01 = new Transaction(anotherId, accountId, BigDecimal.valueOf(100));
        tr04_01.setTransactionDate(time04_01);
        transactions.add(tr24_11);   // +100 balance from 0 - 100   period: 2 days
        transactions.add(tr02_12);   // +100 balance from 100 - 200 period: 0 days
        transactions.add(tr02_12_2); // +100 balance from 200 - 300 period: 7 days
        transactions.add(tr09_12);   // +100 balance from 300 - 400 period: 1 days
        transactions.add(tr10_12);   // +100 balance from 400 - 500 period: 8 days
        transactions.add(tr18_12);   // -100 balance from 500 - 400 period: 11 days
        transactions.add(tr29_12);   // +100 balance from 400 - 500 period: 2 days
        transactions.add(tr31_12);   // +100 balance from 500 - 600 period: 0 days
        transactions.add(tr04_01);   // +100 balance from 600 - 700 period: 0 days
    }
}

package com.alexslo.bank.service;

import com.alexslo.bank.dao.mem.AccountDaoImpl;
import com.alexslo.bank.dao.mem.TransactionDaoImpl;
import com.alexslo.bank.exception.AccountDoNotExistException;
import com.alexslo.bank.exception.NotEnoughMoneyException;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.CreditAccount;
import com.alexslo.bank.model.SavingAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AccountServiceTest {
    private AccountDaoImpl accountDao;
    private AccountService accountService;
    private Random random;

    @Before
    public void init() {
        accountDao = new AccountDaoImpl();
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();
        accountService = new AccountService(accountDao, transactionDao);
        random = new Random();
    }

    @Test
    public void createCreditAccountTest() {
        int accountId = Math.abs(random.nextInt());
        int userId = Math.abs(random.nextInt());
        assertNotNull(accountService.createCreditAccount(userId, accountId));
        Account account = accountDao.getAccountById(accountId);
        assertTrue(account instanceof CreditAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCreditAccountIncorrectArgumentsTest() {
        accountService.createCreditAccount(-1, -2);
    }

    @Test
    public void createSavingAccountTest() {
        int accountId = Math.abs(random.nextInt());
        int userId = Math.abs(random.nextInt());
        assertNotNull(accountService.createSavingAccount(userId, accountId, 20.0));
        Account account = accountDao.getAccountById(accountId);
        assertTrue(account instanceof SavingAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSavingAccountIncorrectArgumentsTest() {
        accountService.createSavingAccount(-1, 0, -20.0);
    }

    @Test
    public void depositTest() {
        int userId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        int creditAcId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 20);
        CreditAccount creditAccount = new CreditAccount(userId, creditAcId);
        accountDao.addAccount(userId, savingAccount);
        accountDao.addAccount(userId, creditAccount);
        accountService.deposit(0, savingAcId, BigDecimal.valueOf(500));
        accountService.deposit(0, creditAcId, BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(500), savingAccount.getBalance());
        assertEquals(BigDecimal.valueOf(1000), creditAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositIncorrectArgumentsTest() {
        accountService.deposit(0, -1, BigDecimal.ZERO);
    }

    @Test(expected = AccountDoNotExistException.class)
    public void depositIncorrectAccountTest() {
        accountService.deposit(0, 1, BigDecimal.valueOf(100));
    }

    @Test
    public void withdrawFromSavingAccountTest() {
        int userId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 20);
        savingAccount.addToBalance(BigDecimal.valueOf(500));
        accountDao.addAccount(userId, savingAccount);
        accountService.withdraw(savingAcId, 0, BigDecimal.valueOf(300));
        assertEquals(BigDecimal.valueOf(200), savingAccount.getBalance());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void withdrawFromSavingAccountWithNoMoneyTest() {
        int userId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 20);
        accountDao.addAccount(userId, savingAccount);
        accountService.withdraw(savingAcId, 0, BigDecimal.valueOf(300));
    }

    @Test
    public void withdrawFromSavingAccountDifferentAmountTest() {
        int userId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 20);
        savingAccount.setBalance(BigDecimal.TEN);
        accountDao.addAccount(userId, savingAccount);
        accountService.withdraw(savingAcId, 0, BigDecimal.valueOf(7));
        accountService.withdraw(savingAcId, 0, BigDecimal.valueOf(3));
        Assert.assertEquals(BigDecimal.ZERO, savingAccount.getBalance());
    }

    @Test
    public void withdrawFromCreditAccountToSavingAccountTest() {
        int userId = Math.abs(random.nextInt());
        int creditAcId = Math.abs(random.nextInt());
        CreditAccount creditAccount = new CreditAccount(userId, creditAcId);
        creditAccount.setCreditLimit(BigDecimal.valueOf(-2000));
        accountDao.addAccount(userId, creditAccount);
        accountService.withdraw(creditAcId, 0, BigDecimal.valueOf(2000));
        assertEquals(BigDecimal.valueOf(-2000), creditAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawIncorrectArgumentsTest() {
        accountService.withdraw(-1, 0, BigDecimal.ZERO);
    }

    @Test(expected = AccountDoNotExistException.class)
    public void withdrawIncorrectAccountTest() {
        accountService.withdraw(12, 0, BigDecimal.ONE);
    }

    @Test
    public void transferFromCreditToSavingAccountTest() {
        int userId = Math.abs(random.nextInt());
        int creditAcId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        CreditAccount creditAccount = new CreditAccount(userId, creditAcId);
        creditAccount.setCreditLimit(BigDecimal.valueOf(-2000));
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 20);
        accountDao.addAccount(userId, creditAccount);
        accountDao.addAccount(userId, savingAccount);
        accountService.transferFromToAccount(creditAcId, savingAcId, BigDecimal.valueOf(1500));
        Assert.assertEquals(BigDecimal.valueOf(1500), savingAccount.getBalance());
        Assert.assertEquals(BigDecimal.valueOf(-1500), creditAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferFromToAccountIncorrectArgumentsTest() {
        accountService.transferFromToAccount(-1, 0, BigDecimal.ZERO);
    }

    @Test(expected = AccountDoNotExistException.class)
    public void transferFromToAccountIncorrectAccountTest() {
        accountService.transferFromToAccount(12, 21, BigDecimal.ONE);
    }
}

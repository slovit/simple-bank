package com.alexslo.bank.dao.mem;

import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.CreditAccount;
import com.alexslo.bank.model.SavingAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class AccountDaoImplTest {
    private AccountDaoImpl accountDao;
    private Random random;

    @Before
    public void init() {
        accountDao = new AccountDaoImpl();
        random = new Random();
    }

    @Test
    public void addSingleAccountTest() {
        int userId = Math.abs(random.nextInt());
        int accountId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, accountId, 20.0);
        accountDao.addAccount(userId, savingAccount);
        List<Account> userAccounts = accountDao.getAccountsByUserId(userId);
        Assert.assertEquals(1, userAccounts.size());
        Assert.assertEquals(savingAccount, userAccounts.get(0));
        Account accountById = accountDao.getAccountById(accountId);
        Assert.assertTrue(accountById instanceof SavingAccount);
        Assert.assertEquals(accountById, savingAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAccountIllegalArgumentTest() {
        accountDao.addAccount(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAccountsByUserIdIllegalArgumentExceptionTest() {
        accountDao.getAccountsByUserId(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gGetAccountByIdIllegalArgumentExceptionTest() {
        accountDao.getAccountById(-121);
    }

    @Test
    public void getAccountByIdThatDoesntExistTest() {
        accountDao.addAccount(1, new SavingAccount(1, 2, 30));
        Account account = accountDao.getAccountById(3);
        assertNull(account);
    }

    @Test
    public void getAccountByIdTest() {
        int userId = Math.abs(random.nextInt());
        int accountId = Math.abs(random.nextInt());
        SavingAccount savingAccount = new SavingAccount(userId, accountId, 20.0);
        accountDao.addAccount(userId, savingAccount);
        Assert.assertEquals(savingAccount, accountDao.getAccountById(accountId));
    }

    @Test
    public void getAccountByIdEmptyListTest() {
        List<Account> accounts = accountDao.getAccountsByUserId(1);
        assertEquals(Collections.emptyList(), accounts);
    }

    @Test
    public void getAccountsByUserIdTest() {
        int userId = Math.abs(random.nextInt());
        int creditAcId = Math.abs(random.nextInt());
        int savingAcId = Math.abs(random.nextInt());
        CreditAccount creditAccount = new CreditAccount(userId, creditAcId);
        SavingAccount savingAccount = new SavingAccount(userId, savingAcId, 30.0);
        accountDao.addAccount(userId, creditAccount);
        accountDao.addAccount(userId, savingAccount);
        List<Account> accounts = accountDao.getAccountsByUserId(userId);
        Account accountOfZeroIndex = accounts.get(0);
        Account accountOfFirstIndex = accounts.get(1);
        Assert.assertEquals(creditAccount, accountOfZeroIndex);
        Assert.assertEquals(savingAccount, accountOfFirstIndex);
        Assert.assertTrue(accountOfZeroIndex instanceof CreditAccount);
        Assert.assertTrue(accountOfFirstIndex instanceof SavingAccount);
    }

    @Test
    public void isAccountExistTest() {
        assertFalse(accountDao.isAccountExist(1));
        accountDao.addAccount(2, new CreditAccount(2, 321));
        assertTrue(accountDao.isAccountExist(321));
    }

    @Test
    public void addTwoAccountsForTheSameUserTest() {
        int userId = Math.abs(random.nextInt());
        Account account1 = new CreditAccount(userId, 1);
        Account account2 = new CreditAccount(userId, 2);
        accountDao.addAccount(userId, account1);
        accountDao.addAccount(userId, account2);
        Account firstAccount = accountDao.getAccountById(1);
        Account secondAccount = accountDao.getAccountById(2);
        Assert.assertEquals(account1, firstAccount);
        Assert.assertEquals(account2, secondAccount);
    }
}

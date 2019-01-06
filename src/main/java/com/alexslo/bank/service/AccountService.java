package com.alexslo.bank.service;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.exception.AccountDoNotExistException;
import com.alexslo.bank.exception.NotEnoughMoneyException;
import com.alexslo.bank.model.*;

import java.math.BigDecimal;

public class AccountService {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public AccountService(AccountDao accDao, TransactionDao trDao) {
        accountDao = accDao;
        transactionDao = trDao;
    }

    public SavingAccount createSavingAccount(int userId, int accountId, double interestRate) {
        if (userId <= 0 || accountId <= 0 || interestRate <= 0) {
            throw new IllegalArgumentException("Incorrect userId or negative interestRate!");
        }
        SavingAccount savingAccount = new SavingAccount(userId, accountId, interestRate);
        accountDao.addAccount(userId, savingAccount);
        return savingAccount;
    }

    public CreditAccount createCreditAccount(int userId, int accountId) {
        if (userId <= 0 || accountId <= 0) {
            throw new IllegalArgumentException("Incorrect userId or negative interestRate!");
        }
        CreditAccount creditAccount = new CreditAccount(userId, accountId);
        accountDao.addAccount(userId, creditAccount);
        return creditAccount;
    }

    private void addToBalance(Account account, BigDecimal amount) {
        account.addToBalance(amount);
    }

    private void subtractCreditBalance(CreditAccount account, BigDecimal amount) {
        BigDecimal accountBalance = account.getBalance();
        BigDecimal creditLimit = account.getCreditLimit();

        if (accountBalance.compareTo(creditLimit) >= 0
                && accountBalance.subtract(amount).compareTo(creditLimit) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
        }
    }

    private void subtractSavingBalance(SavingAccount account, BigDecimal amount) {
        if (!isEnoughMoney(account, amount)) {
            throw new NotEnoughMoneyException();
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    public void deposit(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || toAccountId <= 0 || fromAccountId < 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account account = accountDao.getAccountById(toAccountId);
        if (account == null) {
            throw new AccountDoNotExistException();
        }
        addToBalance(account, amount);
        transactionDao.addTransaction(toAccountId, new Transaction(fromAccountId, toAccountId, amount));
    }

    public void withdraw(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || fromAccountId <= 0 || toAccountId < 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account account = accountDao.getAccountById(fromAccountId);
        if (account == null) {
            throw new AccountDoNotExistException();
        }
        if (account instanceof SavingAccount) {
            subtractSavingBalance((SavingAccount) account, amount);
        }
        if (account instanceof CreditAccount) {
            subtractCreditBalance((CreditAccount) account, amount);
        }
        transactionDao.addTransaction(fromAccountId, new Transaction(fromAccountId, toAccountId, amount));
    }

    public void transferFromToAccount(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId <= 0 || toAccountId <= 0 || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account fromAccount = accountDao.getAccountById(fromAccountId);
        Account toAccount = accountDao.getAccountById(toAccountId);
        if (fromAccount == null || toAccount == null) {
            throw new AccountDoNotExistException();
        }
        withdraw(fromAccountId, toAccountId, amount);
        deposit(fromAccountId, toAccountId, amount);
    }

    private boolean isEnoughMoney(Account account, BigDecimal amount) {
        int result = account.getBalance().compareTo(amount);
        switch (result) {
            case -1:
                return false;
            case 0:
                return true;
            case 1:
                return true;
            default:
                return false;
        }
    }
}

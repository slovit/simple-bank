package com.alexslo.bank.service;

import com.alexslo.bank.dao.mem.TransactionDaoImpl;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.dao.mem.AccountDaoImpl;
import com.alexslo.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountService {

    private AccountDaoImpl accDao;
    private TransactionDaoImpl trDao;

    public AccountService(AccountDaoImpl accDao, TransactionDaoImpl trDao) {
        this.accDao = accDao;
        this.trDao = trDao;
    }

    public boolean deposit(int accountId, BigDecimal amount) {
        boolean result = false;
        if (accDao.accountExist(accountId)) {
            Account account = accDao.getAccountById(accountId);
            if (isEnoughMoney(account, amount)) {
                account.setBalance(account.getBalance().add(amount));
                LocalDateTime dateTime = LocalDateTime.now();
                Transaction transaction = new Transaction(accountId, accountId, amount, dateTime);
                trDao.addTransaction(accountId, transaction);
                result = true;
            }
        }
        return result;
    }

    public boolean withdraw(int accountId, BigDecimal amount) {
        boolean result = false;
        if (accDao.accountExist(accountId)) {
            Account account = accDao.getAccountById(accountId);
            if (isEnoughMoney(account, amount)) {
                account.setBalance(account.getBalance().subtract(amount));
                LocalDateTime dateTime = LocalDateTime.now();
                Transaction transaction = new Transaction(accountId, accountId, amount, dateTime);
                trDao.addTransaction(accountId, transaction);
                result = true;
            }
        }
        return result;
    }

    public boolean depositFromToAccount(int fromAccountId, int toAccountId, BigDecimal amount) {
        boolean result = false;
        if (accDao.accountExist(fromAccountId) && accDao.accountExist(toAccountId)) {
            Account fromAccount = accDao.getAccountById(fromAccountId);
            Account toAccount = accDao.getAccountById(toAccountId);
            if (isEnoughMoney(fromAccount, amount)) {
                fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
                toAccount.setBalance(toAccount.getBalance().add(amount));
                LocalDateTime dateTime = LocalDateTime.now();
                Transaction transaction = new Transaction(fromAccountId, toAccountId, amount, dateTime);
                trDao.addTransaction(fromAccountId, transaction);
                trDao.addTransaction(toAccountId, transaction);
                result = true;
            }
        }
        return result;
    }

    public boolean isEnoughMoney(Account account, BigDecimal amount) {
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

package com.alexslo.bank.service;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.mem.AccountDaoImpl;

import java.math.BigDecimal;

public class AccountService {

    AccountDaoImpl accDao = new AccountDaoImpl();

    public void deposit(int accountId, BigDecimal amount) {
        Account account = accDao.getAccountById(accountId);
        account.setBalance(account.getBalance().add(amount));
    }

    public void withdraw(int accountId, BigDecimal amount) {
        Account account;
        account = accDao.getAccountById(accountId);
        account.setBalance(account.getBalance().subtract(amount));
    }

    public void depositFromToAccount(int fromAccountId, int toAccountId, BigDecimal amount) {
        Account fromAccount = accDao.getAccountById(fromAccountId);
        Account toAccount = accDao.getAccountById(toAccountId);
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
    }
}

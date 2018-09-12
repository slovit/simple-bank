package com.alexslo.bank.service;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.service.impl.AccountDao;

public class AccountService {

    AccountDao accDao = new AccountDao();

    public void deposit(int accountId, double amount){
        Account account = new Account();
        account = accDao.getAccountByAccountId(accountId);
        account.setBalance(account.getBalance() + amount);
    }

    public void withdraw(int accountId, double amount){
        Account account = new Account();
        account = accDao.getAccountByAccountId(accountId);
        account.setBalance(account.getBalance() - amount);
    }

    public void depositFromToAccount(int from_accountId, int to_accountId, double amount){
        Account from_account = new Account();
        Account to_account = new Account();
        from_account = accDao.getAccountByAccountId(from_accountId);
        to_account = accDao.getAccountByAccountId(to_accountId);
        from_account.setBalance(from_account.getBalance() - amount);
        to_account.setBalance(to_account.getBalance() + amount);
    }
}

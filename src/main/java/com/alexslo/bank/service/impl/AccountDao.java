package com.alexslo.bank.service.impl;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.AccountType;

public class AccountDao implements com.alexslo.bank.dao.AccountDao {

    @Override
    public void createAccount(Account account) {

    }

    @Override
    public Account getAccountByUserId(int id) {
        return null;
    }

    @Override
    public double getBalanceByAccountId(int id) {
        return 0;
    }

    @Override
    public AccountType getAccountTypeByAccountId(int id) {
        return null;
    }
}

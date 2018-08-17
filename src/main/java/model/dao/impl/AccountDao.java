package model.dao.impl;

import model.entity.Account;
import model.entity.AccountType;

public class AccountDao implements model.dao.AccountDao {

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

package com.alexslo.bank.dao;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.AccountType;

public interface AccountDao {

    void createAccount(Account account);

    Account getAccountByUserId(int id);

    double getBalanceByAccountId(int id);

    AccountType getAccountTypeByAccountId(int id);
}

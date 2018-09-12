package com.alexslo.bank.dao;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.AccountType;

public interface AccountDao {

    void addAccount(Account account);

    Account getAccountByUserId(int id);

    Account getAccountByAccountId(int id);

    double getBalanceByAccountId(int id);

    AccountType getAccountTypeByAccountId(int id);

    String getAllAccountInfo(Account account);
}

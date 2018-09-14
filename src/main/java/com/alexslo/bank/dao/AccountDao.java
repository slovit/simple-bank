package com.alexslo.bank.dao;

import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;

import java.math.BigDecimal;

public interface AccountDao {

    void addAccount(Account account);

    Account getAccountByUserId(int id);

    Account getAccountById(int id);

    BigDecimal getBalanceByAccountId(int id);

    AccountType getAccountTypeById(int id);

    String getAllAccountInfo(Account account);
}

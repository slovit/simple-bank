package com.alexslo.bank.dao;

import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    void addAccount(int userId, Account account);

    List<Account> getAccountByUserId(int id);

    Account getAccountById(int id);

    BigDecimal getBalanceFromAccount(Account account);

    AccountType getAccountType(Account account);

    String getAllAccountInfo(Account account);

    boolean accountExist(int accountId);
}

package model.dao;

import model.entity.Account;
import model.entity.AccountType;

public interface AccountDao {

    void createAccount(Account account);

    Account getAccountByUserId(int id);

    double getBalanceByAccountId(int id);

    AccountType getAccountTypeByAccountId(int id);
}

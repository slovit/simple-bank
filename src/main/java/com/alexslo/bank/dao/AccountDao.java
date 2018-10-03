package com.alexslo.bank.dao;

import com.alexslo.bank.model.Account;

import java.util.List;

public interface AccountDao {
    /**
     * Add Account by userId into database
     *
     * @param userId
     * @param account
     */
    void addAccount(int userId, Account account);

    /**
     * Return all accounts that user have as List
     *
     * @param userId
     * @return
     */
    List<Account> getAccountsByUserId(int userId);

    /**
     * Return account by its id
     *
     * @param accountId
     * @return
     */
    Account getAccountById(int accountId);

    /**
     * Checks if account exists
     *
     * @param accountId
     * @return true/false
     */
    boolean isAccountExist(int accountId);
}

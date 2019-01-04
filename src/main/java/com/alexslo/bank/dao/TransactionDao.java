package com.alexslo.bank.dao;

import com.alexslo.bank.model.Transaction;

import java.util.List;

public interface TransactionDao {

    /**
     * Add transaction to storage by account id
     *
     * @param accountId
     * @param transaction
     */
    void addTransaction(int accountId, Transaction transaction);

    /**
     * Return all transactions that are tied to account by it`s id as List
     *
     * @param id
     * @return
     */
    List<Transaction> getTransactionsByAccountId(int id);

    List<Transaction> getTransactionsOlderThan(int accountId, int month);

    List<Transaction> getSortedTransactions(int accountId);

    List<Transaction> getAccountTransactionsForMonth(int accountId, int month);
}

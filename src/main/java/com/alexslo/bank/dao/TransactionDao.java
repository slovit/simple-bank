package com.alexslo.bank.dao;

import com.alexslo.bank.model.Transaction;

import java.util.List;

public interface TransactionDao {

    void addTransaction(int accountId, Transaction transaction);

    List<Transaction> getAllTransactionsByAccountId(int id);
}

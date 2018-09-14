package com.alexslo.bank.dao;

import com.alexslo.bank.model.Transaction;

import java.util.List;

public interface TransactionDao  {

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactionsByAccountId(int id);
}

package com.alexslo.bank.dao;

import com.alexslo.bank.model.entity.Transaction;

import java.util.List;

public interface TransactionDao  {

    void addTransaction(Transaction transaction);

    List<Transaction> getAllTransactionsByAccountId(int id);
}

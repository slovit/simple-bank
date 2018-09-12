package com.alexslo.bank.service.impl;

import com.alexslo.bank.model.entity.Transaction;

import java.util.List;

public class TransactionDao implements com.alexslo.bank.dao.TransactionDao {

    @Override
    public void addTransaction(Transaction transaction) {

    }

    @Override
    public Transaction getTransactionByAccountId(int id) {
        return null;
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(int id) {
        return null;
    }
}

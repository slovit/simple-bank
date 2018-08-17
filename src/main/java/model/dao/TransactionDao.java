package model.dao;

import model.entity.Transaction;

import java.util.List;

public interface TransactionDao  {

    void addTransaction(Transaction transaction);

    Transaction getTransactionByAccountId(int id);

    List<Transaction> getAllTransactionsByAccountId(int id);
}

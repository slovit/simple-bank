package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDaoImpl implements TransactionDao {

    private Map<Integer, List<Transaction>> transactionsStorage;

    public TransactionDaoImpl() {
        transactionsStorage = new HashMap<>();
    }

    @Override
    public void addTransaction(int accountId, Transaction transaction) {
        List<Transaction> tempList = transactionsStorage.get(accountId);
        if (tempList == null) {
            tempList = new ArrayList<>();
            tempList.add(transaction);
            transactionsStorage.put(accountId, tempList);
        } else {
            tempList.add(transaction);
            transactionsStorage.put(accountId, tempList);
        }
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(int accountId) {
        return transactionsStorage.get(accountId);
    }
}

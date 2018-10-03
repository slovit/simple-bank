package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class TransactionDaoImpl implements TransactionDao {

    private Map<Integer, List<Transaction>> transactionsByAccountId;

    public TransactionDaoImpl() {
        transactionsByAccountId = new HashMap<>();
    }

    @Override
    public void addTransaction(int accountId, Transaction transaction) {
        if (accountId <= 0 || transaction == null) {
            throw new IllegalArgumentException("incorrect accountId or Transaction equals null!");
        }
        List<Transaction> transactions = transactionsByAccountId.get(accountId);
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transactionsByAccountId.put(accountId, transactions);

    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Incorrect accountId!");
        }
        List<Transaction> transactions = transactionsByAccountId.get(accountId);
        if (transactions == null) {
            return Collections.emptyList();
        }
        return transactions;
    }
}

package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;

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

    @Override
    public List<Transaction> getTransactionsOlderThan(int accountId, int month) {
        return getSortedTransactions(accountId).stream()
                .filter(transaction -> transaction.getTransactionDate().getMonthValue() >= month)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getSortedTransactions(int accountId) {
        List<Transaction> allTransactions = getTransactionsByAccountId(accountId);
        Collections.sort(allTransactions);
        return allTransactions;
    }

    @Override
    public List<Transaction> getAccountTransactionsForMonth(int accountId, int month) {
        if (accountId < 1 || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        List<Transaction> monthTransactions = getTransactionsByAccountId(accountId);
        return monthTransactions.stream()
                .filter(transaction -> transaction.getTransactionDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }
}

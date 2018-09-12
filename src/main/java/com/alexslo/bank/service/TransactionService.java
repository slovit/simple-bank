package com.alexslo.bank.service;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.Transaction;
import com.alexslo.bank.service.impl.TransactionDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private TransactionDao transactionDao = new TransactionDao();

    public List<Transaction> getTransactionsByAccountId(int accountId){
        List<Transaction> result;
        result = transactionDao.getAllTransactionsByAccountId(accountId);
        return result;
    }

    public void makeTransaction(int from_accountId, int to_accountId, double amount){
        LocalDateTime time = LocalDateTime.now();
        Transaction transaction = new Transaction(from_accountId,to_accountId,amount,time);
        transactionDao.addTransaction(transaction);
    }
}

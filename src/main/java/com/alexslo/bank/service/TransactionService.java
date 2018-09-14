package com.alexslo.bank.service;

import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.Transaction;
import com.alexslo.bank.mem.TransactionDaoImpl;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {

    private TransactionDaoImpl transactionDaoImpl = new TransactionDaoImpl();

    public List<Transaction> getTransactionsByAccountId(int accountId){
        List<Transaction> result;
        result = transactionDaoImpl.getAllTransactionsByAccountId(accountId);
        return result;
    }

    public void makeTransaction(int fromAccountId, int toAccountId, double amount){
        LocalDateTime time = LocalDateTime.now();
        Transaction transaction = new Transaction(fromAccountId,toAccountId,amount,time);
        transactionDaoImpl.addTransaction(transaction);
    }
}

package com.alexslo.bank.service;

import com.alexslo.bank.model.Transaction;
import com.alexslo.bank.mem.TransactionDaoImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class TransactionService {

    private TransactionDaoImpl transactionDaoImpl;

    public TransactionService(TransactionDaoImpl transactionDaoImpl){
        this.transactionDaoImpl = transactionDaoImpl;

    }

    public List<Transaction> getTransactionsByAccountId(int accountId){
        return transactionDaoImpl.getAllTransactionsByAccountId(accountId);
    }

    public void makeTransaction(int fromAccountId, int toAccountId, BigDecimal amount){
        LocalDateTime time = LocalDateTime.now();
        Transaction transaction = new Transaction(fromAccountId,toAccountId,amount,time);
        transactionDaoImpl.addTransaction(fromAccountId,transaction);
        transactionDaoImpl.addTransaction(toAccountId,transaction);
    }

    public void showAllAccountTransactions(int accountId){
        List<Transaction> transactions = getTransactionsByAccountId(accountId);
        for(Transaction transaction: transactions){
            System.out.println(transaction.toString());
        }
    }
}

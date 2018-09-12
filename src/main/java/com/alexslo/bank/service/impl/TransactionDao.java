package com.alexslo.bank.service.impl;

import com.alexslo.bank.model.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements com.alexslo.bank.dao.TransactionDao {

    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(int id) {
       List<Transaction> result = new ArrayList<>();
       for(Transaction transaction: transactions){
           if(transaction.getAccount_id() == id){
               result.add(transaction);
           }
       }
       return result;
    }
}

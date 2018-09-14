package com.alexslo.bank.mem;

import com.alexslo.bank.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements com.alexslo.bank.dao.TransactionDao {

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

package com.alexslo.bank.service;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private List<Transaction> allTransactions = new ArrayList<>();

    public void makeTransaction(Account fromAccount,Account toAccount, double amount){

    }

    public List<Transaction> getTransactions(){
        return allTransactions;
    }

    public void addToTransactions(Transaction transaction){
        allTransactions.add(transaction);
    }


}

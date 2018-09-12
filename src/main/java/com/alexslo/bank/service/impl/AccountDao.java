package com.alexslo.bank.service.impl;

import com.alexslo.bank.model.entity.Account;
import com.alexslo.bank.model.entity.AccountType;

import java.util.*;

public class AccountDao implements com.alexslo.bank.dao.AccountDao {

    private List<Account> accounts = new ArrayList<>();


    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Account getAccountByUserId(int id) {
        Account result = new Account();
        for(Account account: this.accounts){
            if(account.getUser_id() == id){
                result = account;
            }
        }
        return result;
    }

    @Override
    public Account getAccountByAccountId(int id) {
        Account result = new Account();
        for(Account account: this.accounts){
            if(account.getId() == id){
                result = account;
            }
        }
        return result;
    }

    @Override
    public double getBalanceByAccountId(int id) {
        double result = 0;
       for(Account account: this.accounts){
           if(account.getId() == id){
              result = account.getBalance();
           }
        }
        return result;
    }

    @Override
    public AccountType getAccountTypeByAccountId(int id) {
        Account result = new Account();
        for(Account account: this.accounts){
            if(account.getId() == id){
                result = account;
            }
        }
        return result.getAccountType();
    }

    @Override
    public String getAllAccountInfo(Account account) {
        return String.format("Account type: %s \nAccount balance: %f\nCreation date: %tD\nExpire date: %tD",
                account.getAccountType(),account.getBalance(),account.getCreationDate(),account.getExpirationDate());
    }
}

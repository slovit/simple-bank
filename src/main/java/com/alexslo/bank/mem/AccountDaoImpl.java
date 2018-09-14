package com.alexslo.bank.mem;

import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;

import java.math.BigDecimal;
import java.util.*;

public class AccountDaoImpl implements com.alexslo.bank.dao.AccountDao {

    private List<Account> accounts = new ArrayList<>();
    private Map<Integer,Account> account = new HashMap<>();


    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Account getAccountByUserId(int id) {
        Account result = new Account();
        for(Account account: this.accounts){
            if(account.getUserId() == id){
                result = account;
            }
        }
        return result;
    }

    @Override
    public Account getAccountById(int id) {
        Account result = new Account();
        for(Account account: this.accounts){
            if(account.getId() == id){
                result = account;
            }
        }
        return result;
    }

    @Override
    public BigDecimal getBalanceByAccountId(int id) {
        BigDecimal result = new BigDecimal(0);
       for(Account account: this.accounts){
           if(account.getId() == id){
              result.add(account.getBalance());
           }
        }
        return result;
    }

    @Override
    public AccountType getAccountTypeById(int id) {
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
        return String.format("Account type: %s \nAccount balance: %f\nCreation date: %tD",
                account.getAccountType(),account.getBalance(),account.getCreationDate());
    }
}

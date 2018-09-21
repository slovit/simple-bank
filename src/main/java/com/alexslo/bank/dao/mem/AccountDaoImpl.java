package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;

import java.math.BigDecimal;
import java.util.*;


public class AccountDaoImpl implements AccountDao {
    private Map<Integer,List<Account>> userAccounts;
    private Map<Integer,Account> accountWarehouse;


    public AccountDaoImpl(){
        userAccounts = new HashMap<>();
        accountWarehouse = new HashMap<>();
    }

    @Override
    public void addAccount(int userId, Account account) {
        List<Account> tempList = userAccounts.get(userId);
        if(tempList == null){
            tempList = new ArrayList<>();
            tempList.add(account);
            userAccounts.put(userId,tempList);
            accountWarehouse.put(account.getId(), account);
        } else {
            tempList.add(account);
            userAccounts.put(userId, tempList);
            accountWarehouse.put(account.getId(), account);
        }
    }

    @Override
    public List<Account> getAccountByUserId(int userId) {
        List<Account> tempList = userAccounts.get(userId);
//        Account result = new Account();
//        for(Account account: tempList){
//            if(account.getUserId() == userId){
//                result = account;
//            }
//        }
        return tempList;
    }

    @Override
    public Account getAccountById(int accountId){
        return accountWarehouse.get(accountId);
    }

    @Override
    public BigDecimal getBalanceFromAccount(Account account) {
        return account.getBalance();
    }

    @Override
    public AccountType getAccountType(Account account) {
        return account.getAccountType();
    }

    @Override
    public boolean accountExist(int accountId){
        Account account = accountWarehouse.get(accountId);
        return account != null;
    }

    @Override
    public String getAllAccountInfo(Account account) {
        return String.format("Account type: %s \nAccount balance: %f\nCreation date: %tD",
                account.getAccountType(),account.getBalance(),account.getCreationDate());
    }
}

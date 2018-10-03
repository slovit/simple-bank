package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.model.Account;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class AccountDaoImpl implements AccountDao {
    private Map<Integer, List<Account>> accountsByUserId;
    private Map<Integer, Account> accountsByAccountId;

    public AccountDaoImpl() {
        accountsByUserId = new HashMap<>();
        accountsByAccountId = new HashMap<>();
    }

    @Override
    public void addAccount(int userId, Account account) {
        if (userId <= 0 || account == null) {
            throw new IllegalArgumentException("Incorrect userId or Account equal null!");
        }
        List<Account> userAccounts = accountsByUserId.get(userId);
        if (userAccounts == null) {
            userAccounts = new ArrayList<>();
        }
        userAccounts.add(account);
        accountsByUserId.put(userId, userAccounts);
        accountsByAccountId.put(account.getId(), account);
    }

    @Override
    public List<Account> getAccountsByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Incorrect userId!");
        }
        List<Account> userAccounts = accountsByUserId.get(userId);
        if (userAccounts != null) {
            return userAccounts;
        }
        return Collections.emptyList();
    }

    @Override
    public Account getAccountById(int accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Incorrect accountId!");
        }
        return accountsByAccountId.get(accountId);
    }

    @Override
    public boolean isAccountExist(int accountId) {
        return accountsByAccountId.containsKey(accountId);
    }
}

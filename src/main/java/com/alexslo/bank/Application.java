package com.alexslo.bank;


import com.alexslo.bank.mem.DaoFactoryImpl;
import com.alexslo.bank.mem.TransactionDaoImpl;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;
import com.alexslo.bank.mem.AccountDaoImpl;
import com.alexslo.bank.model.SavingAccount;
import com.alexslo.bank.service.AccountService;
import com.alexslo.bank.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        DaoFactoryImpl factory = new DaoFactoryImpl();
        AccountDaoImpl accountDao = factory.createAccountDao();
        TransactionDaoImpl transactionDao = factory.createTransactionDao();
        AccountService accountService = new AccountService(accountDao, transactionDao);
        TransactionService transactionService = new TransactionService(transactionDao);
        LocalDateTime time = LocalDateTime.now();
        BigDecimal first = new BigDecimal(5000);
        BigDecimal second = new BigDecimal(3000);
        Account account = new SavingAccount(1, 1, time);
        account.setBalance(first);
        Account account1 = new Account(2, 3, time);
        account1.setBalance(second);

        accountDao.addAccount(1, account);
        accountDao.addAccount(1, account1);
        accountService.depositFromToAccount(1, 3, BigDecimal.valueOf(600));
        accountService.deposit(1, BigDecimal.valueOf(1200));
        transactionService.showAllAccountTransactions(1);

    }
}

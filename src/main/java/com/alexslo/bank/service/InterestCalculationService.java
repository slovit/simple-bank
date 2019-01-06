package com.alexslo.bank.service;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.alexslo.bank.model.ServiceAccounts.SAVING_INTEREST;

public class InterestCalculationService {
    private TransactionDao transactionDao;

    public InterestCalculationService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void addInterestToBalance(SavingAccount savingAccount, int month) {
        if (savingAccount == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        int accountId = savingAccount.getId();
        BigDecimal calculatedInterest = calcSavingAccountInterest(savingAccount, month);
        if (calculatedInterest.compareTo(BigDecimal.ZERO) > 0) {
            Transaction serviceTransaction = new Transaction(SAVING_INTEREST.getAccountId(), accountId, calculatedInterest);
            savingAccount.addToBalance(calculatedInterest);
            transactionDao.addTransaction(accountId, serviceTransaction);
        }
    }

    BigDecimal calcSavingAccountInterest(SavingAccount savingAccount, int month) {
        if (savingAccount == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        int accountId = savingAccount.getId();
        double dailyInterest = calcDailyInterest(savingAccount);
        BigDecimal accountBalance = getAccountBalanceAtMonthEnd(savingAccount, month);
        List<Transaction> monthTransaction = transactionDao.getAccountTransactionsForMonth(accountId, month);
        //List<Transaction> monthTransaction = getAccountTransactionsForMonth(savingAccount, month);
        List<BalanceOverPeriod> balanceOverPeriods = getBalancesOverPeriods(monthTransaction, accountId, accountBalance);
        return calcInterest(dailyInterest, balanceOverPeriods);
    }

    List<Transaction> getAccountTransactionsForMonth(SavingAccount account, int month) {
        if (account == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        List<Transaction> monthTransactions = new ArrayList<>();
        List<Transaction> allAccountTransactions = transactionDao.getTransactionsByAccountId(account.getId());
        for (Transaction transaction : allAccountTransactions) {
            int transactionMonth = transaction.getTransactionDate().getMonthValue();
            if (transactionMonth == month) {
                monthTransactions.add(transaction);
            }
        }
        return monthTransactions;
    }

    List<BalanceOverPeriod> getBalancesOverPeriods(List<Transaction> transactions, int accountId, BigDecimal accountBalance) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Incorrect accountId");
        }
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }
        BigDecimal balance = accountBalance;
        List<BalanceOverPeriod> balanceOverPeriods = new ArrayList<>();
        int maxMonthDays = transactions.get(0).getTransactionDate().getMonth().maxLength();
        int firstDate;
        int secondDate = maxMonthDays;
        int period;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            firstDate = transaction.getTransactionDate().getDayOfMonth();
            balance = calcBalanceBeforeTransaction(transaction, accountId, balance);
            if (i - 1 > 0) {
                secondDate = transactions.get(i - 1).getTransactionDate().getDayOfMonth();
            }
            period = firstDate - secondDate;
            if (i == 0) {
                period = firstDate;
            }
            balanceOverPeriods.add(new BalanceOverPeriod(balance, period));
        }
        return balanceOverPeriods;
    }

    double calcDailyInterest(SavingAccount savingAccount) {
        if (savingAccount == null) {
            throw new IllegalArgumentException("Saving account is null");
        }
        Calendar calendar = Calendar.getInstance();
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        return savingAccount.getInterestRate() / daysInYear;
    }

    BigDecimal calcInterest(double dailyInterest, List<BalanceOverPeriod> balances) {
        if (dailyInterest <= 0) {
            throw new IllegalArgumentException("Daily interest is 0 or less");
        }
        if (balances.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal calculatedInterest = BigDecimal.ZERO;
        BigDecimal hundredPercent = BigDecimal.valueOf(100);
        BigDecimal periodInterest;
        for (BalanceOverPeriod balanceOverPeriod : balances) {
            periodInterest = BigDecimal.valueOf(dailyInterest * balanceOverPeriod.getDays());
            calculatedInterest = calculatedInterest.
                    add(balanceOverPeriod.getBalance().multiply(periodInterest)
                            .divide(hundredPercent));
        }
        return calculatedInterest;
    }

    BigDecimal getAccountBalanceAtMonthEnd(SavingAccount account, int month) {
        if (account == null || month <= 0 || month > 12) {
            throw new IllegalArgumentException("Saving account is null, or incorrect month value");
        }
        int accountId = account.getId();
        List<Transaction> allTransactions = transactionDao.getTransactionsOlderThan(accountId, month);
        if (allTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Transaction transaction = allTransactions.get(allTransactions.size() - 1);
        BigDecimal accountBalance = account.getBalance();
        LocalDateTime transactionDate = transaction.getTransactionDate();
        int transactionMonth = transactionDate.getMonthValue();
        int transactionDay = transactionDate.getDayOfMonth();
        int maxMonthDays = transactionDate.getMonth().maxLength();
        if (transactionMonth == month && transactionDay != maxMonthDays) {
            return calcBalanceBeforeTransaction(transaction, accountId, accountBalance);
        }
        for (int i = allTransactions.size() - 2; i >= 0; i--) {
            transaction = allTransactions.get(i);
            accountBalance = calcBalanceBeforeTransaction(transaction, accountId, accountBalance);
            transactionMonth = transaction.getTransactionDate().getMonthValue();
            if (transactionMonth == month && transactionDay != maxMonthDays) {
                return accountBalance;
            }
        }
        return accountBalance;
    }

    BigDecimal calcBalanceBeforeTransaction(Transaction transaction, int accountId, BigDecimal accountBalance) {
        if (transaction == null || accountId <= 0) {
            throw new IllegalArgumentException("Transaction is null, or accountId is less then 0");
        }
        BigDecimal balance = accountBalance;
        if (transaction.getFromAccountId() == accountId) {
            balance = balance.add(transaction.getAmount());
        } else {
            balance = balance.subtract(transaction.getAmount());
        }
        return balance;
    }
}

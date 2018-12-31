package com.alexslo.bank.service;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.alexslo.bank.model.ServiceAccountType.SAVING_INTEREST;

public class InterestCalculationService {
    private TransactionDao transactionDao;
    private ServiceAccount serviceAccount;

    public InterestCalculationService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
        this.serviceAccount = new ServiceAccount();
        Random random = new Random();
        this.serviceAccount.setAccountId(Math.abs(random.nextInt()));
        this.serviceAccount.setAccountServiceType(SAVING_INTEREST);
    }

    public void addInterestToBalance(SavingAccount savingAccount, int month) {
        if (savingAccount == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        int accountId = savingAccount.getId();
        BigDecimal calculatedInterest = calcSavingAccountInterest(savingAccount, month);
        Transaction serviceTransaction = new Transaction(this.serviceAccount.getAccountId(), accountId, calculatedInterest);
        savingAccount.addToBalance(calculatedInterest);
        this.transactionDao.addTransaction(accountId, serviceTransaction);
    }

    public BigDecimal calcSavingAccountInterest(SavingAccount savingAccount, int month) {
        if (savingAccount == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        int accountId = savingAccount.getId();
        double dailyInterest = calcDailyInterest(savingAccount);
        BigDecimal accountBalance = getAccountBalanceAtMonthEnd(savingAccount, month);
        List<Transaction> monthTransaction = getAccountTransactionsForMonth(savingAccount, month);
        List<BalanceOverPeriod> balanceOverPeriods = getBalancesOverPeriods(monthTransaction, accountId, accountBalance);
        return calcInterest(dailyInterest, balanceOverPeriods);
    }

    public List<Transaction> getAccountTransactionsForMonth(Account account, int month) {
        if (account == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Saving account is null or your month is not in 1-12 range");
        }
        List<Transaction> monthTransactions = new ArrayList<>();
        List<Transaction> allAccountTransactions = this.transactionDao.getTransactionsByAccountId(account.getId());
        for (Transaction transaction : allAccountTransactions) {
            int transactionMonth = transaction.getTransactionDate().getMonthValue();
            if (transactionMonth == month) {
                monthTransactions.add(transaction);
            }
        }
        return monthTransactions;
    }

    public List<BalanceOverPeriod> getBalancesOverPeriods(List<Transaction> transactions, int accountId, BigDecimal accountBalance) {
        if (transactions.isEmpty() || accountId <= 0 || accountBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transactions are empty, or accountId and accountBalance = 0");
        }
        LinkedList<BalanceOverPeriod> balanceOverPeriods = new LinkedList<>();
        int maxMonthDays = transactions.get(0).getTransactionDate().getMonth().maxLength();
        int firstDate;
        int secondDate = maxMonthDays;
        int period;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            firstDate = transaction.getTransactionDate().getDayOfMonth();
            accountBalance = calcTransactionBalance(transaction, accountId, accountBalance);
            if (i - 1 > 0) {
                secondDate = transactions.get(i - 1).getTransactionDate().getDayOfMonth();
            }
            period = firstDate - secondDate;
            if (i == 0) {
                period = firstDate;
            }
            balanceOverPeriods.add(new BalanceOverPeriod(accountBalance, period));
        }
        return balanceOverPeriods;
    }

    public double calcDailyInterest(SavingAccount savingAccount) {
        if (savingAccount == null) {
            throw new IllegalArgumentException("Saving account is null");
        }
        Calendar calendar = Calendar.getInstance();
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        return savingAccount.getInterestRate() / daysInYear;
    }

    public BigDecimal calcInterest(double dailyInterest, List<BalanceOverPeriod> balances) {
        if (dailyInterest <= 0 || balances.isEmpty()) {
            throw new IllegalArgumentException("Daily interest is 0 or less, or List<BalanceOverPeriod> is empty");
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

    public BigDecimal getAccountBalanceAtMonthEnd(SavingAccount account, int month) {
        if (account == null || month <= 0 || month > 12) {
            throw new IllegalArgumentException("Saving account is null, or incorrect month value");
        }
        int accountId = account.getId();
        List<Transaction> allTransactions = transactionDao.getTransactionsByAccountId(account.getId());
        if (allTransactions.isEmpty()) {
            throw new RuntimeException("Empty transactions");
        }
        Transaction transaction = allTransactions.get(allTransactions.size() - 1);
        BigDecimal accountBalance = account.getBalance();
        int transactionMonth = transaction.getTransactionDate().getMonthValue();
        int transactionDay = transaction.getTransactionDate().getDayOfMonth();
        int maxMonthDays = transaction.getTransactionDate().getMonth().maxLength();
        if (transactionMonth == month && transactionDay != maxMonthDays) {
            return calcTransactionBalance(transaction, accountId, accountBalance);
        }
        for (int i = allTransactions.size() - 2; i >= 0; i--) {
            transaction = allTransactions.get(i);
            accountBalance = calcTransactionBalance(transaction, accountId, accountBalance);
            transactionMonth = transaction.getTransactionDate().getMonthValue();
            if (transactionMonth == month && transactionDay != maxMonthDays) {
                return accountBalance;
            }
        }
        return accountBalance;
    }

    public BigDecimal calcTransactionBalance(Transaction transaction, int accountId, BigDecimal accountBalance) {
        if (transaction == null || accountId <= 0) {
            throw new IllegalArgumentException("Transaction is null, or accountId is less then 0");
        }
        if (transaction.getFromAccountId() == accountId) {
            accountBalance = accountBalance.add(transaction.getAmount());
        } else {
            accountBalance = accountBalance.subtract(transaction.getAmount());
        }
        return accountBalance;
    }
}

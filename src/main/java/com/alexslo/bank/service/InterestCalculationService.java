package com.alexslo.bank.service;

import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.SavingAccount;
import com.alexslo.bank.model.ServiceAccount;
import com.alexslo.bank.model.Transaction;

import java.math.BigDecimal;
import java.util.*;

public class InterestCalculationService {
    private TransactionDao transactionDao;

    public InterestCalculationService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void addInterestToBalance(Account account, BigDecimal interest) {
        if (interest.compareTo(BigDecimal.ZERO) <= 0 || account == null) {
            throw new IllegalArgumentException("Incorrect input");
        }
        ServiceAccount serviceAccount = new ServiceAccount();
        int accountId = account.getId();
        Transaction serviceTransaction = new Transaction(serviceAccount.getAccountId(), accountId, interest);
        account.addToBalance(interest);
        transactionDao.addTransaction(accountId, serviceTransaction);
    }

    public BigDecimal countSavingAccountInterest(SavingAccount account, int month) {
        if (account == null || month <= 0 || month > 12) {
            throw new IllegalArgumentException("incorrect input");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int numberOfDaysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        //Account balance at the start of needed month
        BigDecimal accountBalance = getAccountBalanceAtMonthStart(account, month);
        // variable that will contain amount of periods ( how much balance was changed
        int periodCounter = 0;
        BigDecimal hundredPercent = BigDecimal.valueOf(100);
        // Daily interest is calculated as account interestRate divided to number of days in current year
        BigDecimal dailyInterest = BigDecimal.valueOf(account.getInterestRate() / numberOfDaysInYear);
        // Sum of daily interest for certain period and balance that was unchanged
        BigDecimal periodInterest;
        // Variable which will contain sum of calculated balance depending on periodInterest
        BigDecimal accuredInterest = BigDecimal.ZERO;
        List<Transaction> accountTransactions = getAccountTransactionsForNeededMonth(account, month);
        List<BigDecimal> accountBalanceForMonth = getAccountMonthBalance(accountTransactions, account.getId(), accountBalance);
        List<Integer> accountPeriods = getNumberOfBalanceChangesPeriodInDays(accountTransactions);
        for (BigDecimal periodBalance : accountBalanceForMonth) {
            periodInterest = dailyInterest.multiply(BigDecimal.valueOf(accountPeriods.get(periodCounter)));
            // Sorry for this mess, dont know how to write long operation correctly
            accuredInterest = accuredInterest.
                    add(periodBalance.multiply(periodInterest)
                            .divide(hundredPercent));
            periodCounter++;
        }
        return accuredInterest;
    }

    /**
     * Return all transactions that given account has on needed month
     *
     * @param account
     * @return
     */
    private List<Transaction> getAccountTransactionsForNeededMonth(Account account, int month) {
        if (account == null || month <= 0 || month > 12) {
            throw new IllegalArgumentException("incorrect input");
        }
        List<Transaction> monthTransactions = new ArrayList<>();
        // Get all account transactions
        List<Transaction> allAccountTransaction = transactionDao.getTransactionsByAccountId(account.getId());
        // Sort transactions by comparing their month date to needed one
        for (Transaction anAllAccountTransaction : allAccountTransaction) {
            int transactionMonth = anAllAccountTransaction.getTransactionDate().getMonthValue();
            if (transactionMonth == month) {
                monthTransactions.add(anAllAccountTransaction);
            }
        }
        return monthTransactions;
    }

    /**
     * Returns set of integer values which represent number of days when balance was unchanged
     *
     * @param monthTransactions List of account transactions for a certain month period
     * @return
     * @see #getAccountTransactionsForNeededMonth(Account, int)
     */
    private List<Integer> getNumberOfBalanceChangesPeriodInDays(List<Transaction> monthTransactions) {
        if (monthTransactions == null || monthTransactions.isEmpty()) {
            throw new IllegalArgumentException("Incorrect input");
        }
        LinkedList<Integer> linkedList = new LinkedList<>();
        int firstDate;
        int secondDate;
        Transaction nextTransaction;
        // Add first period ( number of days when balance was unchanged until first transaction date )
        linkedList.add(monthTransactions.get(0).getTransactionDate().getDayOfMonth());
        for (int i = 0; i < monthTransactions.size(); i++) {
            Transaction transaction = monthTransactions.get(i);
            firstDate = transaction.getTransactionDate().getDayOfMonth();
            // second day = last day of month as constant
            secondDate = transaction.getTransactionDate().getMonth().maxLength();
            // if index does not belongs to the last-to-last transaction or last,
            // the nextTransaction variable is assigned the next transaction from the list
            if (i <= monthTransactions.size() - 2) {
                nextTransaction = monthTransactions.get(i + 1);
                secondDate = nextTransaction.getTransactionDate().getDayOfMonth();
            }
            // period, that is, how many days the balance did not change -
            // the difference between the day of the month of the next transaction and the current one
            int period = secondDate - firstDate;
            if (period != 0) {
                linkedList.add(period);
            }
        }
        return linkedList;
    }

    /**
     * Returns all list of account balance for given period of time ( month)
     *
     * @param monthTransactions List of account transactions for a certain month period, account id and account balance
     *                          at the start of month
     * @return
     * @see #getAccountBalanceAtMonthStart(Account, int)
     */
    //todo-me Fix! Make transactions at the same day as one balance element( do not add all one-day tr into list, only one)
    private List<BigDecimal> getAccountMonthBalance(List<Transaction> monthTransactions,
                                                    int accountId, BigDecimal accountBalance) {
        if (monthTransactions == null || monthTransactions.isEmpty() ||
                accountId <= 0 || accountBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("incorrect input");
        }
        LinkedList<BigDecimal> monthBalance = new LinkedList<>();
        //Add account balance that was at the start of month
        monthBalance.add(accountBalance);
        int maxMonthDays = monthTransactions.get(0).getTransactionDate().getMonth().maxLength();
        int currentDayNumber;
        int nextDayNumber;
        //Count balance according to transaction info and put all balance into LinkedList
        for (int i = 0; i < monthTransactions.size(); i++) {
            Transaction monthTransaction = monthTransactions.get(i);
            if (monthTransaction.getFromAccountId() == accountId) {
                accountBalance = accountBalance.subtract(monthTransaction.getAmount());
            } else {
                accountBalance = accountBalance.add(monthTransaction.getAmount());
            }
            currentDayNumber = monthTransaction.getTransactionDate().getDayOfMonth();
            //If transaction was made on the last day of month balance won`t  be added
            //If next transaction is on the same day as current, balance will be calculated but not added
            if (currentDayNumber != maxMonthDays) {
                if (i + 1 < monthTransactions.size()) {
                    nextDayNumber = monthTransactions.get(i + 1).getTransactionDate().getDayOfMonth();
                    if (currentDayNumber == nextDayNumber) {
                        continue;
                    }
                }
                monthBalance.add(accountBalance);
            }
        }
        return monthBalance;
    }

    /**
     * Returns account balance at the start of needed month
     *
     * @param account
     * @param month
     * @return
     */
    private BigDecimal getAccountBalanceAtMonthStart(Account account, int month) {
        if (account == null || month <= 0 || month > 12) {
            throw new IllegalArgumentException("incorrect input");
        }
        List<Transaction> accTransactions = transactionDao.getTransactionsByAccountId(account.getId());
        BigDecimal accountBalance = account.getBalance();
        int accountId = account.getId();
        int transactionMonth;
        Transaction transaction;
        for (int i = accTransactions.size() - 1; i >= 0; i--) {
            transaction = accTransactions.get(i);
            transactionMonth = transaction.getTransactionDate().getDayOfMonth();
            //if fromAccountId = accountId it means that balance in this transaction was subtracted
            // so we add subtracted amount back
            if (transaction.getFromAccountId() == accountId) {
                accountBalance = accountBalance.add(transaction.getAmount());
            } else {
                accountBalance = accountBalance.subtract(transaction.getAmount());
            }
            // if transaction belongs to needed month and it is not last
            // check if previous transaction is from other month
            if (i != 0 && transactionMonth == month) {
                Transaction previousTransaction = accTransactions.get(i - 1);
                int previousTransactionMonth = previousTransaction.getTransactionDate().getDayOfMonth();
                //if it is from another month than do add/subtract operation and return account balance, that
                //was at the start of the needed month
                if (previousTransactionMonth != month) {
                    if (transaction.getFromAccountId() == accountId) {
                        accountBalance = accountBalance.add(previousTransaction.getAmount());
                    } else {
                        accountBalance = accountBalance.subtract(previousTransaction.getAmount());
                    }
                    return accountBalance;
                }
            }
        }
        return accountBalance;
    }
}

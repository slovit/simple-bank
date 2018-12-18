package com.alexslo.bank.service;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.exception.AccountDoNotExistException;
import com.alexslo.bank.exception.NotEnoughMoneyException;
import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.CreditAccount;
import com.alexslo.bank.model.SavingAccount;
import com.alexslo.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class AccountService {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public AccountService(AccountDao accDao, TransactionDao trDao) {
        this.accountDao = accDao;
        this.transactionDao = trDao;
    }

    public SavingAccount createSavingAccount(int userId, int accountId, double interestRate) {
        if (userId <= 0 || accountId <= 0 || interestRate <= 0) {
            throw new IllegalArgumentException("Incorrect userId or negative interestRate!");
        }
        SavingAccount savingAccount = new SavingAccount(userId, accountId, interestRate);
        accountDao.addAccount(userId, savingAccount);
        return savingAccount;
    }

    public CreditAccount createCreditAccount(int userId, int accountId) {
        if (userId <= 0 || accountId <= 0) {
            throw new IllegalArgumentException("Incorrect userId or negative interestRate!");
        }
        CreditAccount creditAccount = new CreditAccount(userId, accountId);
        accountDao.addAccount(userId, creditAccount);
        return creditAccount;
    }

    private void addToBalance(Account account, BigDecimal amount) {
        account.addToBalance(amount);
    }

    private void subtractCreditBalance(CreditAccount account, BigDecimal amount) {
        BigDecimal accountBalance = account.getBalance();
        BigDecimal creditLimit = account.getCreditLimit();

        if (accountBalance.compareTo(creditLimit) >= 0
                && accountBalance.subtract(amount).compareTo(creditLimit) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
        }
    }

    private void subtractSavingBalance(SavingAccount account, BigDecimal amount) {
        if (!isEnoughMoney(account, amount)) {
            throw new NotEnoughMoneyException();
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    public void deposit(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || toAccountId <= 0 || fromAccountId < 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account account = accountDao.getAccountById(toAccountId);
        if (account == null) {
            throw new AccountDoNotExistException();
        }
        addToBalance(account, amount);
        transactionDao.addTransaction(toAccountId, new Transaction(fromAccountId, toAccountId, amount, account.getBalance()));
    }

    public void withdraw(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || fromAccountId <= 0 || toAccountId < 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account account = accountDao.getAccountById(fromAccountId);
        if (account == null) {
            throw new AccountDoNotExistException();
        }
        if (account instanceof SavingAccount) {
            subtractSavingBalance((SavingAccount) account, amount);
        }
        if (account instanceof CreditAccount) {
            subtractCreditBalance((CreditAccount) account, amount);
        }
        transactionDao.addTransaction(fromAccountId, new Transaction(fromAccountId, toAccountId, amount, account.getBalance()));
    }

    public void transferFromToAccount(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId <= 0 || toAccountId <= 0 || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Incorrect accountId or amount is negative!");
        }
        Account fromAccount = accountDao.getAccountById(fromAccountId);
        Account toAccount = accountDao.getAccountById(toAccountId);
        if (fromAccount == null || toAccount == null) {
            throw new AccountDoNotExistException();
        }
        withdraw(fromAccountId, toAccountId, amount);
        deposit(fromAccountId, toAccountId, amount);
    }

    private boolean isEnoughMoney(Account account, BigDecimal amount) {
        int result = account.getBalance().compareTo(amount);
        switch (result) {
            case -1:
                return false;
            case 0:
                return true;
            case 1:
                return true;
            default:
                return false;
        }
    }

    public BigDecimal countAndAddCreditInterest(Account account) {
        Calendar calendar = Calendar.getInstance();
        // Current day of month
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int firstDayOfMonth = 1;
        // variable that will contain amount of periods ( how much balance was changed
        int periodCounter = 0;
        BigDecimal hundredPercent = BigDecimal.valueOf(100);
        // do nothing if today date is not start of the month
        if (dayOfMonth != firstDayOfMonth) {
            return null;
        }
        // Daily interest is calculated as account interest( considered as interest for year)
        // divided on amount of days in year
        BigDecimal dailyInterest = BigDecimal.valueOf(account.getDailyInterest());
        // Sum of daily interest for certain period and balance that was unchanged
        BigDecimal periodInterest;
        // Variable which will contain sum of calculated balance depending on periodInterest
        BigDecimal accuredInterest = account.getCreditInterest();
        List<Transaction> accountTransactions = getAccountTransactionsForCurrentMonth(account);
        List<BigDecimal> accountBalanceForMonth = getAccountMonthBalance(accountTransactions);
        List<Integer> accountPeriods = getNumberOfBalanceChangesPeriodInDays(accountTransactions);
        for (BigDecimal periodBalance : accountBalanceForMonth) {
            periodInterest = dailyInterest.multiply(BigDecimal.valueOf(accountPeriods.get(periodCounter)));
            // Sorry for this mess, dont know how to write long operation correctly
            accuredInterest = accuredInterest.
                    add(periodBalance.multiply(periodInterest)
                            .divide(hundredPercent));
            periodCounter++;
        }
        account.setBalance(account.getBalance().add(accuredInterest));
        return accuredInterest;
    }

    /**
     * Return all transactions that given account has on needed month
     *
     * @param account
     * @return
     */
    private List<Transaction> getAccountTransactionsForCurrentMonth(Account account) {
        List<Transaction> monthTransactions = new ArrayList<>();
        // Get all account transactions
        List<Transaction> allAccountTransaction = transactionDao.getTransactionsByAccountId(account.getId());
        LocalDateTime currentTime = LocalDateTime.now();
        // Sort transactions by comparing their month date to needed one
        for (int i = 0; i < allAccountTransaction.size(); i++) {
            LocalDateTime transactionDate = allAccountTransaction.get(i).getTransactionDate();
            if (transactionDate.getMonth().equals(currentTime.getMonth())) {
                //when firs transaction from needed mont found, add previous transaction to the list
                // to understand what balance account had before first transaction in needed month
                if (monthTransactions.size() == 0) {
                    monthTransactions.add(allAccountTransaction.get(i - 1));
                }
                monthTransactions.add(allAccountTransaction.get(i));
            }
        }
        return monthTransactions;
    }

    /**
     * Returns set of integer values which represent number of days when balance was unchanged
     *
     * @param monthTransactions List of account transactions for a certain month period
     * @return
     * @see #getAccountTransactionsForCurrentMonth(Account)
     */
    private List<Integer> getNumberOfBalanceChangesPeriodInDays(List<Transaction> monthTransactions) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < monthTransactions.size(); i++) {
            Transaction transaction = monthTransactions.get(i);
            // By default next transaction from the list = null
            Transaction nextTransaction = null;
            // First day of a month will be 0 as constant
            int firstDate = 0;
            int secondDate;
            // if index does not belong to the first transaction from the List,
            // then firstDate acquires the value of the date (day of the month) of the current transaction
            if (i != 0) {
                firstDate = transaction.getTransactionDate().getDayOfMonth();
            }
            // if index does not belongs to the last-to-last transaction,
            // the nextTransaction variable is assigned the next transaction from the list
            if (i != monthTransactions.size() - 1) {
                nextTransaction = monthTransactions.get(i + 1);
            }
            // if nextTransaction not null, then secondDate gets the value of the nextTransaction date
            if (nextTransaction != null) {
                secondDate = nextTransaction.getTransactionDate().getDayOfMonth();
            } else {
                // else secondDate gets value of the last day in month
                secondDate = LocalDateTime.now().getMonth().maxLength();
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
     * @param monthTransactions List of account transactions for a certain month period
     * @return
     * @see #getAccountTransactionsForCurrentMonth(Account)
     */
    private List<BigDecimal> getAccountMonthBalance(List<Transaction> monthTransactions) {
        LinkedList<BigDecimal> linkedList = new LinkedList<>();
        int currentDayNumber;
        int previousDayNumber;
        //put all balance into LinkedList
        for (int i = 0; i < monthTransactions.size(); i++) {
            linkedList.add(monthTransactions.get(i).getAccountBalance());
            // If there are couple or more transaction on the same date, only last recorded balance on that 
            // day will be added. Other previous balances from that day will be deleted
            if (i != 0) {
                previousDayNumber = monthTransactions.get(i - 1).getTransactionDate().getDayOfMonth();
                currentDayNumber = monthTransactions.get(i).getTransactionDate().getDayOfMonth();
                if (currentDayNumber == previousDayNumber) {
                    linkedList.remove(monthTransactions.get(i - 1).getAccountBalance());
                }
                //If transaction was made on the last day of month balance will be removed
                if (currentDayNumber == LocalDateTime.now().getMonth().maxLength()) {
                    linkedList.remove(monthTransactions.get(i).getAccountBalance());
                }
            }
        }
        return linkedList;
    }
}

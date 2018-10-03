package com.alexslo.bank.dao;

public interface DaoFactory {

    public UserDao createUserDao();

    public AccountDao createAccountDao();

    public TransactionDao createTransactionDao();

}

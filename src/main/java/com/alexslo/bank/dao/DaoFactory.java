package com.alexslo.bank.dao;

import com.alexslo.bank.mem.AccountDaoImpl;
import com.alexslo.bank.mem.TransactionDaoImpl;
import com.alexslo.bank.mem.UserDaoImpl;

public interface DaoFactory{

    public UserDaoImpl createUserDao();

    public AccountDaoImpl createAccountDao();

    public TransactionDaoImpl createTransactionDao();

}

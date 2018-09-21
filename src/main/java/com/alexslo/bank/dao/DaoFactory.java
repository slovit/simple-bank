package com.alexslo.bank.dao;

import com.alexslo.bank.dao.mem.AccountDaoImpl;
import com.alexslo.bank.dao.mem.TransactionDaoImpl;
import com.alexslo.bank.dao.mem.UserDaoImpl;

public interface DaoFactory{

    public UserDaoImpl createUserDao();

    public AccountDaoImpl createAccountDao();

    public TransactionDaoImpl createTransactionDao();

}

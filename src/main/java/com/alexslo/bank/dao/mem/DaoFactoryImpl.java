package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.DaoFactory;

public class DaoFactoryImpl implements DaoFactory {
    @Override
    public UserDaoImpl createUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public AccountDaoImpl createAccountDao() {
        return new AccountDaoImpl();
    }

    @Override
    public TransactionDaoImpl createTransactionDao() {
        return new TransactionDaoImpl();
    }
}

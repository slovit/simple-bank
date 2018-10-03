package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.dao.DaoFactory;
import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.dao.UserDao;

public class DaoFactoryImpl implements DaoFactory {
    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public AccountDao createAccountDao() {
        return new AccountDaoImpl();
    }

    @Override
    public TransactionDao createTransactionDao() {
        return new TransactionDaoImpl();
    }
}

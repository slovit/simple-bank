package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.AccountDao;
import com.alexslo.bank.dao.DaoFactory;
import com.alexslo.bank.dao.TransactionDao;
import com.alexslo.bank.dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DaoFactoryImplTest {
    private DaoFactory daoFactory;

    @Before
    public void ibit() {
        daoFactory = new DaoFactoryImpl();
    }

    @Test
    public void testCreateUserDao() {
        UserDao userDao;
        userDao = daoFactory.createUserDao();
        Assert.assertNotNull(userDao);
    }

    @Test
    public void testCreateAccountDao() {
        AccountDao accountDao;
        accountDao = daoFactory.createAccountDao();
        Assert.assertNotNull(accountDao);
    }

    @Test
    public void testCreateTransactionDao() {
        TransactionDao transactionDao;
        transactionDao = daoFactory.createTransactionDao();
        Assert.assertNotNull(transactionDao);
    }
}

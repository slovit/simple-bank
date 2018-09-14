package com.alexslo.bank.dao;

import com.alexslo.bank.mem.TransactionDaoImpl;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract AccountDao createAccountDao();
    public abstract TransactionDaoImpl createTransactionDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null){
            synchronized (DaoFactory.class){
                if(daoFactory == null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}

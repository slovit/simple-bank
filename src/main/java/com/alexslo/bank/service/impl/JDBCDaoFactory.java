package com.alexslo.bank.service.impl;

import com.alexslo.bank.dao.DaoFactory;
import com.alexslo.bank.dao.UserDao;


public class JDBCDaoFactory extends DaoFactory {

    @Override
   public UserDao createUserDao() {
        return null;
   }

    @Override
    public AccountDao createAccountDao() {
        return null;
    }

    @Override
    public TransactionDao createTransactionDao() {
        return null;
    }

//    private Connection getConnection(){
//
//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        } catch (ClassNotFoundException | SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}

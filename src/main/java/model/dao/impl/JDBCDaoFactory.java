package model.dao.impl;

import model.dao.DaoFactory;
import model.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static view.DbConstants.*;

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

    private Connection getConnection(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

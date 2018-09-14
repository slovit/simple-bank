package com.alexslo.bank;


import com.alexslo.bank.model.Account;
import com.alexslo.bank.model.AccountType;
import com.alexslo.bank.model.User;
import com.alexslo.bank.model.UserRole;
import com.alexslo.bank.mem.AccountDaoImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        //initializing temporary data
        User Sashko = new User(1,"user","user",UserRole.USER);
        User AlexSlo = new User(2,"admin","admin",UserRole.USER);
        LocalDateTime time = LocalDateTime.now();
        BigDecimal ammount = new BigDecimal(5000);
        Account account = new Account(1,1,ammount,AccountType.DEPOSIT,time);
        AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
        System.out.println( accountDaoImpl.getAllAccountInfo(account));
    }
}

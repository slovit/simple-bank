package com.alexslo.bank;

import com.alexslo.bank.model.entity.User;
import com.alexslo.bank.model.entity.UserRole;

public class Application {
    public static void main(String[] args) {
        //initializing temporary data
        User Sashko = new User(1,"user","user",UserRole.USER);
        User AlexSlo = new User(2,"admin","admin",UserRole.ADMIN);
    }
}

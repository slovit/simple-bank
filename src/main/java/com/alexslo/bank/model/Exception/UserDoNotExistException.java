package com.alexslo.bank.model.Exception;

public class UserDoNotExistException extends Exception{

    public UserDoNotExistException(){
        super("User with such login doesn't exist!");
    }
}

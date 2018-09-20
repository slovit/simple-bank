package com.alexslo.bank.model.Exception;

public class NotCorrectPasswordException extends Exception {
    public NotCorrectPasswordException(){
        super("Your password is incorrect");
    }
}

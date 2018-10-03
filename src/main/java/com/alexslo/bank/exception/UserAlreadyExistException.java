package com.alexslo.bank.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("Such User already exist");
    }
}

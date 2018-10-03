package com.alexslo.bank.exception;

public class AccountDoNotExistException extends RuntimeException {
    public AccountDoNotExistException() {
        super("Such account does`nt exist");
    }
}

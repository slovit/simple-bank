package com.alexslo.bank.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(){
        super("Not enough money on your balance!");
    }
}

package com.alexslo.bank.model;

final class AccountIdSequence {

    private static int accountIdCounter = 1;

    private AccountIdSequence(){}

    static int getNextAccountId(){
        return accountIdCounter++;
    }
}

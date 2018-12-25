package com.alexslo.bank.model;

public class ServiceAccount {
    private int accountId;

    public ServiceAccount (){
        this.accountId = 0;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

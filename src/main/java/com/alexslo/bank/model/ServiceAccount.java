package com.alexslo.bank.model;

public class ServiceAccount {
    private int accountId;
    private Enum accountServiceType;

    public ServiceAccount(int accountId, Enum accountType) {
        this.accountId = accountId;
        this.accountServiceType = accountType;
    }

    public Enum getAccountServiceType() {
        return accountServiceType;
    }

    public void setAccountServiceType(Enum accountServiceType) {
        this.accountServiceType = accountServiceType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

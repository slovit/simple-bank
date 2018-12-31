package com.alexslo.bank.model;

public class ServiceAccount {
    private int accountId;
    private Enum accountServiceType;

    public ServiceAccount() {
    }

    public Enum getAccountServiceType() {
        return accountServiceType;
    }

    public void setAccountServiceType(Enum accoutServiceType) {
        this.accountServiceType = accoutServiceType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

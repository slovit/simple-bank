package com.alexslo.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SavingAccount extends Account {
    private int userId;
    private int accountId;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private BigDecimal balance;

    public SavingAccount(int userId, int accountId, LocalDateTime creationDate) {
        this.userId = userId;
        this.accountId = accountId;
        this.creationDate = creationDate;
        this.accountType = AccountType.SAVING;
    }

    public int getId(){
        return accountId;
    }


}

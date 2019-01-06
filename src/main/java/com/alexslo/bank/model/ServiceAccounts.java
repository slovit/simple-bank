package com.alexslo.bank.model;

import static com.alexslo.bank.model.AccountIdSequence.getNextAccountId;

public final class ServiceAccounts {

    private ServiceAccounts() {
    }

    public static final ServiceAccount ATM = new ServiceAccount(getNextAccountId(), ServiceAccountType.ATM);
    public static final ServiceAccount SAVING_INTEREST = new ServiceAccount(getNextAccountId(), ServiceAccountType.SAVING_INTEREST);
}

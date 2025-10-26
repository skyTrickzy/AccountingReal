package model.entities;

import model.enums.AccountType;

public class Account {
    private final String account;
    private final AccountType type;

    public Account(String account, AccountType type) {
        this.account = account;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public AccountType getType() {
        return type;
    }
}

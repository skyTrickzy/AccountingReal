package model.entities;

/**
 * kay ang list man ani sa accounts compara sa uban naay fixed size
 * we gonna be using a custom class for a list to display
 */
public final class Accounts {
    Account account;
    int amount;

    public Accounts(Account account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}


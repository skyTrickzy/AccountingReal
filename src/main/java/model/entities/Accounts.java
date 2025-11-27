package model.entities;

/**
 * kay ang list man ani sa accounts compara sa uban naay fixed size
 * we gonna be using a custom class for a list to display
 */
public final class Accounts {
    Account account;
    double amount;

    public Accounts(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}


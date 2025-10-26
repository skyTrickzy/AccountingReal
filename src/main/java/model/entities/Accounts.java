package model.entities;

import model.tables.AccountsTableModel;

/**
 * used for accounts list
 *
 * @see AccountsTableModel
 */
public class Accounts {
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

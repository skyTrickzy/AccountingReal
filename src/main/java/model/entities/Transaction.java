package model.entities;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class Transaction {
    private LocalDate date;
    private String description;
    private Account debitAccount;
    private Account creditAccount;
    private double amount;

    private String ID;

    public Transaction(
            LocalDate date,
            String description,
            Account debitAccount,
            Account creditAccount,
            int amount)
    {

        this.date = date;
        this.description = description;
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
        this.amount = amount;
        ID = UUID.randomUUID().toString();

    }

    public Transaction(
            String ID,
            LocalDate date,
            String description,
            Account debitAccount,
            Account creditAccount,
            int amount
    )
    {
        this.ID = ID;
        this.date = date;
        this.description = description;
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Account getCreditAccount() {
        return creditAccount;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return ID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

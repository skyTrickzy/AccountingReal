package services;

import interfaces.FilterableService;
import model.entities.*;
import model.enums.AccountType;
import utils.Constants;
import view.components.tables.TransactionTable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountsListService {
    private ArrayList<Accounts> list = new ArrayList<>();
    Integer amounts[] = new Integer[list.size()];

    public AccountsListService() {
        initialize();
    }

    private void initialize() {
        for (String a: Constants.ACCOUNTS) {
            String replaced = a.replaceAll("\\s*\\[.*\\]", "");

            switch (replaced) {
                // ASSET
                case "Cash":
                case "Accounts Receivable":
                case "Inventory":
                case "Prepaid Expenses":
                case "Equipment":
                    list.add(new Accounts(new Account(replaced, AccountType.ASSET), 0));
                    break;

                    // liability
                case "Accounts Payable":
                case "Notes Payable":
                    list.add(new Accounts(new Account(replaced, AccountType.LIABILITY), 0));
                    break;

                    // EQUITY
                case "Owner's Capital":
                    list.add(new Accounts(new Account(replaced, AccountType.EQUITY), 0));
                    break;

                case "Sales Revenue":
                case "Service Income":
                    list.add(new Accounts(new Account(replaced, AccountType.INCOME), 0));
                    break;

                case "Cost of Goods Sold":
                case "Rent Expense":
                case "Salaries Expense":
                case "Utilities Expense":
                    list.add(new Accounts(new Account(replaced, AccountType.EXPENSE), 0));
                    break;
            }
        }
    }

    public ArrayList<Accounts> getList() {
        return list;
    }

    public void calculateAll() {
        for (Accounts a : list) {
            a.setAmount(calculateTotalAmount(a.getAccount().getAccount()));
        }
    }

    public int calculateTotalAmount(String account) {
        int amount = 0;
        ArrayList<Transaction> list = TransactionList.getInstance();

        for(Transaction obj : list) {
            if (obj.getDebitAccount().getAccount().contains(account)) {
                AccountType type = obj.getDebitAccount().getType();
                switch (type) {
                    case ASSET, EXPENSE ->
                        amount += obj.getAmount();

                    case LIABILITY, EQUITY, REVENUE, INCOME -> {
                        amount -= obj.getAmount();
                    }
                }
            }
        }


        for(Transaction obj : list) {
            if (obj.getCreditAccount().getAccount().contains(account)) {
                AccountType type = obj.getCreditAccount().getType();

                switch(type) {
                    case ASSET, EXPENSE ->
                            amount -= obj.getAmount();
                    case LIABILITY, EQUITY, REVENUE, INCOME -> {
                        amount += obj.getAmount();
                    }
                }
            }
        }

        return amount;
    }
}

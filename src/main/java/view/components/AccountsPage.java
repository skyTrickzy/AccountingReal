package view.components;

import controller.TransactionController;
import model.entities.Account;
import model.entities.Accounts;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;
import utils.Constants;
import view.custom.JTableDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

final public class AccountsPage extends JPanel {
    AccountsTableModel tableModel = new AccountsTableModel();
    AccountsTable table = new AccountsTable(tableModel);
    TransactionController controller = new TransactionController(tableModel);

    public AccountsPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JScrollPane pane = new JScrollPane(table);
        add(pane);

    }

    public ArrayList<Accounts> getListInstance() {
        return new AccountsListService().getInstance();
    }
}

/**
 * JTable is a stand alone UI doesnt have to do anything
 * who sets the logic of how a data is gonna be displayed is the JAbstractTableModel
 * that's extend into JTableDisplay
 * so in other words table is the view, The model is the data processor
 */
final class AccountsTable extends JTable {

    public AccountsTable(JTableDisplay model) {
        setModel(model);

        setOpaque(false);
        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}

// a data that handles how the data is processed or logic or doing some calculation
final class AccountsListService {
    private static ArrayList<Accounts> list = new ArrayList<>();

    public AccountsListService() {
        if (list.isEmpty()) {
            initialize();
        }
    }

    public ArrayList<Accounts> getInstance() {
        return list;
    }

    /**
     * can be ignored
     * this is initializes the list for a table to display something
     */
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

    /**
     * responsible for updating the balance of each account
     * basically we loop through the Account list then, we
     * set the amount of each accounts
     */
    public void calculateAll() {
        for (Accounts a : list) {
            a.setAmount(calculateTotalAmount(a.getAccount().getAccount()));
        }
    }

    public int calculateTotalAmount(String account) {
        int amount = 0;
        ArrayList<Transaction> list = TransactionList.getInstance();


        //TODO: NEEDS SOME FIXING PARTICULARLY WHERE THE ACCOUNT WILL STAY 0 for a certain transaction
        // because of the fact that when looping through the transaction there's it gets negative.


        /**
         *
         * so how this works is that it has a parameter account and tries to match the account if
         * theres a transaction of that said account, then we are gonna add that
         * so we loop first through the debit account side, then the next is credit account
         * for example we search for cash account in the debit side
         * if cash is found then we add that
         * and then for cash credit side
         * if cash is found since cash is now a credit we subtract it
         */
        AccountType current = null;

        for(Transaction obj : list) {
            if (obj.getDebitAccount().getAccount().contains(account)) {

                current = obj.getDebitAccount().getType();

                AccountType type = obj.getDebitAccount().getType();

                switch (type) {
                    case ASSET, EXPENSE ->
                            amount += obj.getAmount();

                    case LIABILITY, EQUITY, REVENUE, INCOME ->
                        amount -= obj.getAmount();
                }
            }
        }

        /**
         * looks for the credit side of an account
         */
        for(Transaction obj : list) {
            if (obj.getCreditAccount().getAccount().contains(account)) {
                AccountType type = obj.getCreditAccount().getType();
                switch(type) {
                    case ASSET, EXPENSE ->
                        amount -= (int) obj.getAmount();
                    case LIABILITY, EQUITY, REVENUE, INCOME ->
                        amount += (int) obj.getAmount();
                }
            }
        }
        // we dont want to let the internal amount be set to zero
        return amount;
    }
}


/**
 * A custom filterable for certain tables that needs a way to categorize some list by any means
 * if you dont see a filterable that means theres no filterable being implemented
 * this is the model responsible for how the data is gonna be displayed
 * model is basically the one controls how data is gonna be displayed
 * JTableDisplay is an extension of the JAbstractTableModel
 */
final class AccountsTableModel extends JTableDisplay {
    private final AccountsListService service = new AccountsListService();
    private final List<Accounts> list = service.getList();

    private final String[] columnNames = {"Account", "Type", "Balance"};

    /**
     * displayList is the one responsible for firing the table to basically update again
     * the reason we need this is because we need a unified way of updating the UI
     * so that is why we need the controller updates the UI if you look at the code
     * of {@code TransactionController} this method is invoked there
     * @param query can be {@code null} if default
     */
    @Override
    public void displayList(String query) {
        service.calculateAll();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * the main responsiblity of the model how everything is going to be displayed
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Accounts acc = list.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return acc.getAccount().getAccount();
            }

            case 1 -> {
                return acc.getAccount().getType();
            }

            case 2 -> {
                return "₱" + (Math.max(acc.getAmount(), 0));
            }

            default -> {
                return null;
            }
        }
    }
}

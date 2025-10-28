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
}

class AccountsTable extends JTable {

    public AccountsTable(JTableDisplay model) {
        setModel(model);

        setOpaque(false);
        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}


class AccountsListService {
    private ArrayList<Accounts> list = new ArrayList<>();

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

                    case LIABILITY, EQUITY, REVENUE, INCOME ->
                        amount -= obj.getAmount();
                }
            }
        }


        for(Transaction obj : list) {
            if (obj.getCreditAccount().getAccount().contains(account)) {
                AccountType type = obj.getCreditAccount().getType();

                switch(type) {
                    case ASSET, EXPENSE ->
                        amount -= obj.getAmount();
                    case LIABILITY, EQUITY, REVENUE, INCOME ->
                        amount += obj.getAmount();
                }
            }
        }

        return amount < 0 ? 0 : amount;
    }
}

class AccountsTableModel extends JTableDisplay {
    private AccountsListService service = new AccountsListService();
    private List<Accounts> list = service.getList();

    private final String[] columnNames = {"Account", "Type", "Balance"};

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
                return acc.getAmount();
            }

            default -> {
                return null;
            }
        }
    }
}


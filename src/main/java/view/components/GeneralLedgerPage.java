package view.components;

import controller.TransactionController;
import interfaces.FilterableService;
import model.entities.Account;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;
import utils.Constants;
import view.custom.JTableDisplay;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static utils.Constants.reverseIfNegative;

public class GeneralLedgerPage extends JPanel {
    GeneralLedgerModel model = new GeneralLedgerModel();
    GeneralLedgerTable table = new GeneralLedgerTable(model);
    TransactionController controller = new TransactionController(model);

    private JButton searchButton;
    private JComboBox<java.lang.String> searchComboBox;

    public GeneralLedgerPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(searchPanel());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.white);
        add(scrollPane);
    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);

        searchPanel.add(searchComboBox = createSearchComboBox()); // Changed to JComboBox
        searchPanel.add(searchButton = clickEvent());

        return searchPanel;
    }

    private JButton clickEvent() {
        JButton btn = new JButton("Search");

        btn.addActionListener(l -> {
            controller.filterEvent(searchComboBox.getSelectedItem().toString()); // Changed to JComboBox getSelectedItem
        });

        return btn;
    }

    private JComboBox<String> createSearchComboBox() {// Example options, adjust as needed
        JComboBox<String> comboBox = new JComboBox<>(Constants.ACCOUNTS);
        comboBox.setPreferredSize(new Dimension(200, 20));
        comboBox.setMaximumSize(new Dimension(200, 20));

        return comboBox;
    }
}

/**
 * JTable is a stand alone UI doesnt have to do anything
 * who sets the logic of how a data is gonna be displayed is the JAbstractTableModel
 * that's extend into JTableDisplay
 * so in other words table is the view, The model is the data processor
 */
final class GeneralLedgerTable extends JTable {
    public GeneralLedgerTable(AbstractTableModel model) {
        if (model instanceof AbstractTableModel)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
        setOpaque(false);
    }
}

/**
 * A custom filterable for certain tables that needs a way to categorize some list by any means
 * this is the model responsible for how the logic is gonna be displayed
 * model is basically the one controls how data is gonna be displayed
 * JTableDisplay is an extension of the JAbstractTableModel
 */
final class GeneralLedgerModel extends JTableDisplay implements FilterableService {
    java.lang.String[] headers = {"Date", "Description", "Debit Account", "Credit Account", "Amount", "Balance"};
    ArrayList<Transaction> list = TransactionList.getInstance();
    ArrayList<Transaction> currentList = new ArrayList<>();
    private String query = "Cash";

    private static double balanceAtThatTime = 0;

    ArrayList<LedgerAccounts> actualList = new ArrayList<>();

    @Override
    /**
     * This method creates a list based on the query being passed
     * basically for example in a list of transaction if a single element contains an account of cash
     * then it gets added to the currentList;
     **/
    public ArrayList<Transaction> filterable(String e) {

        return (ArrayList<Transaction>) list.stream().filter(i -> {
            if (i.getCreditAccount().getAccountName().contains(e.toString()) ||
                    i.getDebitAccount().getAccountName().contains(e.toString())) return true;

            return false;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param column  the column being queried
     * @return index of a header
     */
    @Override
    public java.lang.String getColumnName(int column) {
        return headers[column];
    }


    /**
     * displayList is the one responsible for firing the table to basically update again
     * the reason we need this is because we need a unified way of updating the UI
     * so that is why we need the controller updates the UI if you look at the code
     * of {@code TransactionController} this method is invoked there
     * @param query can be {@code null} if default
     */
    @Override
    public void displayList(String query) {
        if (query == null || query.isEmpty()) {
            query = this.query; // we set up a default query which is cash
        }

        this.query = query;

        currentList = filterable(query);
        currentList =  (ArrayList<Transaction>) currentList.stream()
                .sorted(Comparator.comparing(Transaction::getDate))  // Sort by LocalDate
                .collect(Collectors.toList());
        actualList.clear();


        for (int i = 0; i < currentList.size(); i++) {
            Transaction current = currentList.get(i);

            if (!current.getDebitAccount().getAccountName().contains(query) &&
                    !current.getCreditAccount().getAccountName().contains(query)) continue;

            double cur = getBalanceAtThatTime(query, current.getDebitAccount(), current.getCreditAccount(), current.getAmount());

            actualList.add(new LedgerAccounts(current, cur));
        }

        balanceAtThatTime = 0;

        fireTableDataChanged();
    }

    public double getBalanceAtThatTime(String query, Account debit, Account credit, double amount) {
        if (debit.getAccountName().contains(query)) {
            AccountType type = debit.getType();
            switch(type) {
                case ASSET, EXPENSE ->
                    balanceAtThatTime += amount;
                case INCOME, LIABILITY, EQUITY -> balanceAtThatTime -= amount;
            }
            return balanceAtThatTime;
        }

        else {
            AccountType type = credit.getType();
            switch(type) {
                case ASSET, EXPENSE ->
                        balanceAtThatTime -= amount;
                case INCOME, LIABILITY, EQUITY -> balanceAtThatTime += amount;
            }
            return balanceAtThatTime;
        }
    }

    /**
     * determines how many rows should be inserted into the table
     * @return length
     */
    @Override
    public int getRowCount() {
        return actualList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    /**
     * the main responsiblity of the model how everything is going to be displayed
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction a = actualList.get(rowIndex).getTransaction();
        double balance = actualList.get(rowIndex).getBalance();
        return switch(columnIndex) {
            case 0 -> a.getDate();
            case 1 -> a.getDescription();
            case 2 -> a.getDebitAccount().getAccountName().replaceAll("\\s*\\[.*\\]", "");
            case 3 -> a.getCreditAccount().getAccountName().replaceAll("\\s*\\[.*\\]", "");
            case 4 -> reverseIfNegative(a.getAmount());
            case 5 -> reverseIfNegative(balance);
            default -> null;
        };
    }
}

class LedgerAccounts {
    Transaction transaction;
    double balance;

    LedgerAccounts(Transaction transaction, double balance) {
        this.transaction = transaction;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
package view.components;

import controller.TransactionController;
import interfaces.FilterableService;
import model.entities.EventPassed;
import model.entities.Transaction;
import model.entities.TransactionList;
import view.custom.JTableDisplay;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final public class TransactionsPage extends JPanel {
    private TransactionTableModel tableModel;
    private TransactionController controller;
    private TransactionTable table = null;
    private JButton searchButton;
    private JTextField searchBar;

    public TransactionsPage() {
        tableModel = new TransactionTableModel();
        table = new TransactionTable(tableModel);
        controller = new TransactionController(tableModel);

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(searchPanel());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        searchPanel.add(searchBar = searchBar());
        searchPanel.add(searchButton = clickEvent());


        return searchPanel;
    }

    private JButton clickEvent() {
        JButton btn = new JButton("Search");

        btn.addActionListener(l -> {
            controller.filterEvent(new EventPassed<>(searchBar.getText()));
        });

        return btn;
    }

    private JTextField searchBar() {
        JTextField tmp = new JTextField();
        tmp.setPreferredSize(new Dimension(200, 20));
        tmp.setMaximumSize(new Dimension(200, 20));

        return tmp;
    }

    /**
     * method that updates the view using the {@code onUpdate} of {@link TransactionController}
     *
     * @see TransactionController
     */
}

class TransactionTable extends JTable {

    public TransactionTable(AbstractTableModel model) {
        if (model instanceof AbstractTableModel)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}

class TransactionSearchService implements FilterableService {
    java.util.List<Transaction> list;

    @Override
    public ArrayList<Transaction> filterable(EventPassed<?> e) {


        String query = e.getType() == null ? "" : (String) e.getType();

        if (query.trim().isEmpty()) {
            return TransactionList.getInstance();
        }


        List<Transaction> list = TransactionList.getInstance()
                .stream()
                .filter(item -> item.getDescription().contains(query))
                .collect(Collectors.toList());

        return (ArrayList<Transaction>) list;
    }
}

class TransactionTableModel extends JTableDisplay {
    private TransactionSearchService searchService = new TransactionSearchService();
    private  List<Transaction> transactionList = searchService.filterable(new EventPassed<>(null));
    private final String[] columnNames = {"Date", "Description", "Debit", "Credit", "Amount"};

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }


    @Override
    public void displayList(String query) {
        transactionList = searchService.filterable(new EventPassed<>(query));
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return transactionList.size();
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

        Transaction transaction = transactionList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return transaction.getDate();
            case 1:
                return transaction.getDescription();
            case 2:
                String debit = transaction.getDebitAccount().getAccount();
                return debit.replaceAll("\\s*\\[.*\\]", "");
            case 3:
                String credit = transaction.getCreditAccount().getAccount();
                return credit.replaceAll("\\s*\\[.*\\]", "");
            case 4:
                return transaction.getAmount();

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
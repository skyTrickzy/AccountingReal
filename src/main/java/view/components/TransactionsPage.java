package view.components;

import controller.TransactionController;
import interfaces.FilterableService;
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
    private TransactionTableModel tableModel = new TransactionTableModel();
    private TransactionController controller = new TransactionController(tableModel);
    private TransactionTable table = new TransactionTable(tableModel);

    private JButton searchButton;
    private JTextField searchBar;

    public TransactionsPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(searchPanel());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);

        searchPanel.add(searchBar = searchBar());
        searchPanel.add(searchButton = clickEvent());


        return searchPanel;
    }

    private JButton clickEvent() {
        JButton btn = new JButton("Search");

        btn.addActionListener(l -> {
            controller.filterEvent(searchBar.getText());
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

/**
 * JTable is a stand alone UI doesnt have to do anything
 * who sets the logic of how a data is gonna be displayed is the JAbstractTableModel
 * that's extend into JTableDisplay
 * so in other words table is the view, The model is the data processor
 */
class TransactionTable extends JTable {

    public TransactionTable(AbstractTableModel model) {
        if (model instanceof AbstractTableModel)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
        setOpaque(false);
    }
}

class TransactionSearchService implements FilterableService {



    @Override
    public ArrayList<Transaction> filterable(String query) {
        System.out.println(TransactionList.getInstance().size());


        if (query == null) {
            query = "";
        }

        if (query.trim().isEmpty()) {
            return TransactionList.getInstance();
        }


        String finalQuery = query;
        List<Transaction> list = TransactionList.getInstance()
                .stream()
                .filter(item -> item.getDescription().contains(finalQuery))
                .collect(Collectors.toList());

        return (ArrayList<Transaction>) list;
    }
}
/**
 * A custom filterable for certain tables that needs a way to categorize some list by any means
 * if you dont see a filterable that means theres no filterable being implemented
 * this is the model responsible for how the data is gonna be displayed
 * model is basically the one controls how data is gonna be displayed
 * JTableDisplay is an extension of the JAbstractTableModel
 */
class TransactionTableModel extends JTableDisplay {
    private TransactionSearchService searchService = new TransactionSearchService();
    private  List<Transaction> transactionList = searchService.filterable("");
    private final String[] columnNames = {"Date", "Description", "Debit", "Credit", "Amount"};

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
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
        transactionList = searchService.filterable(query);
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

    /**
     * the main responsiblity of the model how everything is going to be displayed
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     */
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
                return "₱" + transaction.getAmount();

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
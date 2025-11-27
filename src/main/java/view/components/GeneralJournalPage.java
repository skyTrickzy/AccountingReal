package view.components;

import controller.TransactionController;
import model.entities.Transaction;
import model.entities.TransactionList;
import view.custom.JTableDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GeneralJournalPage extends JPanel {
    GeneralJournalModel model = new GeneralJournalModel();
    GeneralJournalTable table = new GeneralJournalTable(model);
    TransactionController t = new TransactionController(model);
    ArrayList<Transaction> list = TransactionList.getInstance();

    public GeneralJournalPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JScrollPane pane = new JScrollPane(table);
        add(pane);

        System.out.println(list.size());
    }
}

final class GeneralJournalTable extends JTable {

    public GeneralJournalTable (JTableDisplay model) {
        setModel(model);

        setOpaque(false);
        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}

final class GeneralJournalModel extends JTableDisplay {
    String[] header = {"Date", "Description", "Account", "Debit", "Credit"};
    ArrayList<Transaction> list = TransactionList.getInstance();


    @Override
    public void displayList(String query) {
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size() * 2;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        // Ensure that the rowIndex is valid
        if (rowIndex < 0 || rowIndex >= list.size() * 2) {
            return null;
        }

        if (rowIndex % 2 == 0) {
            // Even row: Access the transaction normally
            Transaction transaction = list.get(rowIndex / 2); // Divide by 2 to access the correct transaction

            return switch (columnIndex) {
                case 0 -> transaction.getDate(); // Date
                case 1 -> transaction.getDescription(); // Description
                case 2 -> transaction.getDebitAccount().getAccountName(); // Debit Account
                case 3 -> transaction.getAmount(); // Debit Amount
                default -> null; // For any other columns, return null
            };
        } else {
            // Odd row: Access the previous transaction (rowIndex - 1)
            Transaction transaction = list.get((rowIndex - 1) / 2); // Divide by 2 to access the correct transaction

            return switch (columnIndex) {
                case 2 -> transaction.getCreditAccount().getAccountName(); // Credit Account
                case 4 -> transaction.getAmount(); // Credit Amount
                default -> null; // For any other columns, return null
            };
        }
    }
}


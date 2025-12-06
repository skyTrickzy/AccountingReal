package view.components;

import controller.TransactionController;
import model.entities.Transaction;
import model.entities.TransactionList;
import utils.Constants;
import view.custom.JTableDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GeneralJournalPage extends JPanel {
    GeneralJournalModel model = new GeneralJournalModel();
    GeneralJournalTable table = new GeneralJournalTable(model);
    TransactionController t = new TransactionController(model);
    ArrayList<Transaction> list = TransactionList.getInstance();


    public GeneralJournalPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JScrollPane pane = new JScrollPane(table);
        pane.getViewport().setBackground(Color.WHITE);
        add(pane);
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
    ArrayList<Transaction> copiedList = sortList();

    @Override
    public void displayList(String query) {
        copiedList = sortList();
        fireTableDataChanged();
    }

    private ArrayList<Transaction> sortList() {
        ArrayList<Transaction> copiedList = (ArrayList<Transaction>) list.stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())  // Sort by LocalDate
                .collect(Collectors.toList());
        return copiedList;
    }

    @Override
    public int getRowCount() {
        return copiedList.size() * 2;
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
        if (rowIndex < 0 || rowIndex >= copiedList.size() * 2) {
            return null;
        }

        if (rowIndex % 2 == 0) {
            // Even row: Access the transaction normally
            Transaction transaction = copiedList.get(rowIndex / 2); // Divide by 2 to access the correct transaction

            return switch (columnIndex) {
                case 0 -> transaction.getDate(); // Date
                case 1 -> transaction.getDescription(); // Description
                case 2 -> {
                   String debit = transaction.getDebitAccount().getAccountName();
                   yield debit.replaceAll("\\s*\\[.*\\]", "");
                } // Debit Account
                case 3 -> Constants.reverseIfNegative(transaction.getAmount()); // Debit Amount
                default -> null; // For any other columns, return null
            };
        } else {
            // Odd row: Access the previous transaction (rowIndex - 1)
            Transaction transaction = copiedList.get((rowIndex - 1) / 2); // Divide by 2 to access the correct transaction

            return switch (columnIndex) {
                case 2 -> {
                    String credit = transaction.getCreditAccount().getAccountName();
                    yield credit.replaceAll("\\s*\\[.*\\]", "");
                } // Credit Account
                case 4 -> Constants.reverseIfNegative(transaction.getAmount()); // Credit Amount
                default -> null; // For any other columns, return null
            };
        }
    }
}


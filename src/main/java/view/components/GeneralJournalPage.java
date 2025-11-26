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

        if (rowIndex % 2 == 0) {
            Transaction transaction = list.get(rowIndex);


            return switch(columnIndex) {
                // gamiti ang methods provided by transcation
                // example transaction.getDescription();
                // para sa accounts kay getDebitAccount.getAccount();q

                case 0 -> transaction.getDate(); // Date
                case 1 -> transaction.getDescription(); // Description
                case 2 -> transaction.getDebitAccount().getAccount(); // Account
                case 3 -> transaction.getAmount(); // Debit
                default -> null; // none
            };
        }
        else {
            Transaction transaction = list.get(rowIndex - 1);
            return switch(columnIndex) {
                case 2 -> transaction.getCreditAccount().getAccount(); // Account
                case 4 -> transaction.getAmount(); // Cash
                default -> null;
            };
        }
    }
}


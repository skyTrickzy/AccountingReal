package model.tables;

import model.entities.EventPassed;
import model.entities.Transaction;
import services.TransactionSearchService;
import view.custom.JTableDisplay;

import java.util.List;


public class TransactionTableModel extends JTableDisplay {
    private TransactionSearchService searchService = new TransactionSearchService();
    private  List<Transaction> transactionList = searchService.filterable(new EventPassed<>(null));
    private final String[] columnNames = {"Date", "Description", "Debit", "Credit", "Amount"};

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void updateView() {
        fireTableDataChanged();
    }

    @Override
    public void displayList(String query) {
       transactionList = searchService.filterable(new EventPassed<>(query));
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
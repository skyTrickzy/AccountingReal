package model.tables;

import model.entities.Account;
import model.entities.Accounts;
import model.entities.EventPassed;
import model.entities.TransactionList;
import model.enums.AccountType;
import services.AccountsListService;
import view.custom.JTableDisplay;

import java.util.List;

public class AccountsTableModel extends JTableDisplay {
    private AccountsListService service = new AccountsListService();
    private List<Accounts> list = service.getList();

    private final String[] columnNames = {"Account", "Type", "Balance"};

    public void updateView() {
        fireTableDataChanged();
    }

    @Override
    public void displayList(String query) {
        service.calculateAll();
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


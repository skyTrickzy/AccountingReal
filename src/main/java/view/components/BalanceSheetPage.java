package view.components;

import controller.TransactionController;
import model.entities.Account;
import model.entities.Accounts;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;
import view.custom.JTableDisplay;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BalanceSheetPage extends JPanel {
    AssetSideModel assetSideModel = new AssetSideModel();
    EquityLiabilityModel equityLiabilityModel = new EquityLiabilityModel();
    TransactionController controller1 = new TransactionController(assetSideModel);
    TransactionController controller2 = new TransactionController(equityLiabilityModel);
    AssetSideTable assetSideTable = new AssetSideTable(assetSideModel);
    EquityLiabilityTable equityLiabilityTable = new EquityLiabilityTable(equityLiabilityModel);

    JPanel leftSide = leftSide();
    JPanel rightSide = rightSide();

    public BalanceSheetPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(leftSide);
        add(rightSide);

        JScrollPane left = new JScrollPane(assetSideTable);
        JScrollPane right = new JScrollPane(equityLiabilityTable);

        left.getViewport().setOpaque(false);
        right.getViewport().setOpaque(false);

        leftSide.add(left);
        rightSide.add(right);
    }

    private JPanel leftSide() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(600, 600));
        panel.setMaximumSize(new Dimension(600, 600));

        return panel;
    }

    private JPanel rightSide() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(600, 600));
        panel.setMaximumSize(new Dimension(600, 600));

        return panel;
    }
}

class AssetSideTable extends JTable {
    AssetSideTable(JTableDisplay model) {
        if (model != null)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
        setOpaque(false);
    }
}

class EquityLiabilityTable extends JTable {
    EquityLiabilityTable(AbstractTableModel model) {
        if (model != null)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
        setOpaque(false);
    }
}

class AssetSideModel extends JTableDisplay{
    String[] headers = {"Asset", "Amount"};
    ArrayList<Accounts> list = new AccountsPage().getListInstance();
    ArrayList<AccountAmount> currentList = new ArrayList<>();


    public void updateList() {
        ArrayList<AccountAmount> tempList = new ArrayList<>();

        for (Accounts temp : list) {
            if (temp.getAccount().getType() == AccountType.ASSET) {
                if (temp.getAmount() < 0 || temp.getAmount() > 0) {
                    tempList.add(new AccountAmount(new Account(temp.getAccount().getAccount(), temp.getAccount().getType()), temp.getAmount()));
                }
            }
        }

        currentList = tempList;
    }

    public int calculateAssets() {
            int totalAssets = 0;

            for (AccountAmount acc : currentList) {
                totalAssets += acc.getAmount();
            }

            return totalAssets;
    }

    @Override
    public void displayList(String query) {
        updateList();
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public int getRowCount() {
        return currentList.size() + 2;
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex == currentList.size()) {
            return null;
        }

        if (rowIndex == currentList.size() + 1) {
            return switch (columnIndex) {
                case 0 -> "Total Assets: ";
                default -> calculateAssets();
            };
        }

        AccountAmount temp = currentList.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> temp.getAccount().getAccount();
            default -> "P" + temp.getAmount();
        };
    }
}

class EquityLiabilityModel extends JTableDisplay implements Charts {
    String[] headers = {"Equity and Liabilities", "Amount"};

    @Override
    public void getAccounts() {
    }

    @Override
    public void displayList(String query) {

    }
    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        return switch (columnIndex) {
            case 1 -> "hello";
            default -> "god";
        };
    }
}

interface Charts {
    public void getAccounts();
}

class AccountAmount {
    private Account account;
    private int amount;

    AccountAmount(Account account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
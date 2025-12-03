package view.components;

import controller.TransactionController;
import model.entities.Account;
import model.entities.Accounts;
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
            System.out.println(temp.getAccount().getType() + " " +  temp.getAmount());

            AccountType currentType = temp.getAccount().getType();
            if (currentType == AccountType.ASSET) {
                if (temp.getAmount() < 0 || temp.getAmount() > 0) {
                    tempList.add(new AccountAmount(new Account(temp.getAccount().getAccountName(), temp.getAccount().getType()), temp.getAmount()));
                }
            }
        }

        currentList = tempList;
    }

    public double calculateAssets() {
            double totalAssets = 0;

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
                default -> "₱" + calculateAssets();
            };
        }

        AccountAmount temp = currentList.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> temp.getAccount().getAccountName();
            default -> "₱" + temp.getAmount();
        };
    }
}

class EquityLiabilityModel extends JTableDisplay {
    String[] headers = {"Equity and Liabilities", "Amount"};
    ArrayList<AccountAmount> currentList = new ArrayList<>();
    ArrayList<Accounts> list = new AccountsPage().getListInstance();
    ArrayList<Accounts> copiedList = new ArrayList<>(list);

    public void updateList() {
        ArrayList<AccountAmount> tempList = new ArrayList<>();

        for (Accounts temp : copiedList) {

            AccountType currentType = temp.getAccount().getType();

            if (currentType == AccountType.LIABILITY) {
                if (temp.getAmount() < 0 || temp.getAmount() > 0) {
                    tempList.add(new AccountAmount(new Account(temp.getAccount().getAccountName(), temp.getAccount().getType()), temp.getAmount()));
                }

                continue;
            }

            if (currentType == AccountType.EQUITY) {
                temp.setAmount(totalNet() + temp.getAmount());
                if (temp.getAmount() < 0 || temp.getAmount() > 0) {
                    tempList.add(new AccountAmount(new Account(temp.getAccount().getAccountName(), temp.getAccount().getType()), temp.getAmount()));
                }
            }
        }

        currentList = tempList;
    }

    private double calculateEquityAndLiability() {
        double totalAssets = 0;

        for (AccountAmount acc : currentList) {
            totalAssets += acc.getAmount();
        }

        return totalAssets;
    }

    private Double totalNet() {
        double netIncome = 0.0;

        for (Accounts temp : list) {
            AccountType currentType = temp.getAccount().getType();

            if (currentType == AccountType.INCOME) {
                netIncome += temp.getAmount();
                System.out.println(netIncome);
            } else if (currentType == AccountType.EXPENSE) {
                netIncome -= temp.getAmount();
                System.out.println(netIncome);
            }
        }

        return netIncome;
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
                case 0 -> "Total Equity and Liabilities: ";
                default -> "₱" + calculateEquityAndLiability();
            };
        }

        AccountAmount temp = currentList.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> temp.getAccount().getAccountName();
            default -> "₱" + temp.getAmount();
        };
    }
}

class AccountAmount {
    private Account account;
    private double amount;

    AccountAmount(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
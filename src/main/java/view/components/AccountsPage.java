package view.components;

import controller.TransactionController;
import model.tables.AccountsTableModel;
import view.components.tables.AccountsTable;

import javax.swing.*;
import java.awt.*;

public class AccountsPage extends JPanel {
    AccountsTableModel tableModel = new AccountsTableModel();
    AccountsTable table = new AccountsTable(tableModel);
    TransactionController controller = new TransactionController(tableModel);

    public AccountsPage() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JScrollPane pane = new JScrollPane(table);
        add(pane);

        updateView();
    }

    private void updateView() {
        controller.onUpdate(() -> {
            tableModel.updateView();
        });
    }
}

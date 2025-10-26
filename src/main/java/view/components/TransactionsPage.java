package view.components;

import controller.TransactionController;
import model.entities.EventPassed;
import model.tables.TransactionTableModel;
import view.components.tables.TransactionTable;

import javax.swing.*;
import java.awt.*;

public class TransactionsPage extends JPanel {
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


        updateView();
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
    // worst mistake
    public void updateView() {
        controller.onUpdate(() -> {
            tableModel.updateView();
        });
    }
}
package view.components.tables;

import view.custom.JTableDisplay;

import javax.swing.*;
import java.awt.*;

public class AccountsTable extends JTable {

    public AccountsTable(JTableDisplay model) {
        setModel(model);

        setOpaque(false);
        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}

package view.components.tables;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class TransactionTable extends JTable {

    public TransactionTable(AbstractTableModel model) {
        if (model instanceof AbstractTableModel)
            setModel(model);

        setRowHeight(50);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}

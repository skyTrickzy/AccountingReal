package view.components;


import controller.TransactionController;

import javax.swing.*;
import java.awt.*;

public class GeneralJournalPage extends JPanel {
    TransactionController t = new TransactionController();

    public GeneralJournalPage() {
        setOpaque(false);
        setLayout(new GridBagLayout());
    }
}

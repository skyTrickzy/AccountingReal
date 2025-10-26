package view.components;

import controller.TransactionController;
import model.entities.TransactionList;
import utils.Constants;
import view.custom.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class Body extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new RoundedPanel(10);

    private final int PANEL_HEIGHT = Constants.FRAME_HEIGHT - 200; // 500
    private final int PANEL_WIDTH = Constants.FRAME_WIDTH; // 500

    public Body() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Constants.BG_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new SwitcherPanels(cardLayout, cardPanel));
        addCardPanels();


        JPanel listPanel = listPanel();
        add(listPanel);
        listPanel.add(cardPanel);
    }

    private void addCardPanels() {

        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(Color.white);
        cardPanel.setMaximumSize(new Dimension(950, 500));
        cardPanel.add(new TransactionCreatorPage(), Constants.INDICATORS[0]);
        cardPanel.add(new TransactionsPage(), Constants.INDICATORS[1]);
        cardPanel.add(new AccountsPage(), Constants.INDICATORS[2]);
        cardPanel.add(new GeneralJournalPage(), Constants.INDICATORS[3]);
        cardPanel.add(new GeneralLedgerPage(), Constants.INDICATORS[4]);
        cardPanel.add(new BalanceSheetPage(), Constants.INDICATORS[5]);
    }

    private JPanel listPanel() {
        JPanel listPanel = new JPanel();

        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));

        return listPanel;
    }
}


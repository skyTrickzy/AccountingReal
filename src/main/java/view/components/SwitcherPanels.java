package view.components;

import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class SwitcherPanels extends JPanel {
    private CardLayout cl;
    private JPanel cardPanel;

    private final int PANEL_HEIGHT = 50;

    private final JButton[] buttons = new JButton[6];

    public SwitcherPanels(CardLayout cl, JPanel cardPanel) {
        this.cl = cl;
        this.cardPanel = cardPanel;

        setPreferredSize(new Dimension(Constants.FRAME_WIDTH, PANEL_HEIGHT));
        setMaximumSize(new Dimension(Constants.FRAME_WIDTH, PANEL_HEIGHT));
        setBackground(Constants.BG_COLOR);
        setLayout(new FlowLayout(FlowLayout.LEFT,2, 2));

        int len = Constants.INDICATORS.length;

        for(int i = 0; i < len; i++) {
            buttons[i] = new JButton(Constants.INDICATORS[i]);
            int finalI = i;
            buttons[i].addActionListener(e -> cl.show(cardPanel, Constants.INDICATORS[finalI]));
            add(buttons[i]);
        }
    }
}
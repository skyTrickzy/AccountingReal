package view.components;

import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private final int PANEL_HEIGHT = 100;
    GridBagConstraints gbc = new GridBagConstraints();
    public Header() {
        setPreferredSize(new Dimension(Constants.FRAME_WIDTH, PANEL_HEIGHT));
        setMaximumSize(new Dimension(Constants.FRAME_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(36, 49, 87));
        setLayout(new GridBagLayout());


        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        add(headerTitle(), gbc);
    }

    private JLabel headerTitle() {
        JLabel title = new JLabel("Accounting System");
        title.setFont(Constants.HEADER_FONT_ARIAL);
        title.setForeground(Color.WHITE);
        return title;
    }
}
package view.custom;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int borderRadius;

    public RoundedPanel(int radius) {
        this.borderRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
    }

    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }
}
package view.window;

import utils.Constants;
import view.components.Body;
import view.components.Header;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(String title) {
        super(title);
        setSize(new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(Constants.BG_COLOR);


        add(new Header());
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(new Body());
    }
}
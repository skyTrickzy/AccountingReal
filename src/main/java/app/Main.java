package app;

import view.window.MainWindow;

import javax.swing.*;

// ug naa ka diri adto sa model/tables para sa logic ug unsaon pag display ang data
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow("Accounting System");
        });
    }
}

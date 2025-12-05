package view.components;

import controller.TransactionController;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class TransactionCreatorPage extends JPanel {
    TransactionController controller =  new TransactionController();

    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel formPanel;

    private JTextField date = new JTextField();
    private JTextField desc = new JTextField();
    private JComboBox<String> debitAccount = new JComboBox<>(Constants.ACCOUNTS);
    private JComboBox<String> creditAccount = new JComboBox<>(Constants.ACCOUNTS);
    private JTextField amount = new JTextField();

    JButton button = new JButton();

    public TransactionCreatorPage() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        panels();
    }

    // unfinished
    private void onClickEvent() {
        String dateStr = date.getText();
        String descStr = desc.getText();
        String selectedDebit = (String) debitAccount.getSelectedItem();
        String selectedCredit = (String) creditAccount.getSelectedItem();
        String strAmount = amount.getText();

        DecimalFormat df = new DecimalFormat("#.##");
        try {
            LocalDate newDate = LocalDate.parse(dateStr);
            double amounts = Double.parseDouble(strAmount);
            double formatted = Double.parseDouble(df.format(amounts));

            boolean isTransactionSuccessful = controller.addTransaction(newDate, descStr, selectedDebit, selectedCredit, formatted);
            if (isTransactionSuccessful) {
                date.setText("");
                desc.setText("");
                amount.setText("");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clearAllTransactions() {
        controller.clearTransactions();
    }



    private void formFields() {
        formPanel.add(panelFields("Date: (YYYY-MM-DD)", date));
        formPanel.add(panelFields("Description:", desc));
        formPanel.add(panelFields("Debit Account:", debitAccount));
        formPanel.add(panelFields("Credit Account:", creditAccount));
        formPanel.add(panelFields("Amount:", amount));

        formPanel.add(buttonPanel());
    }

    private JPanel buttonPanel() {
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setPreferredSize(new Dimension(500, 30));
        panel.setMaximumSize(new Dimension(500, 30));
        panel.setOpaque(false);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(e -> onClickEvent());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);

        panel.add(btn1, gbc);

        JButton btn2 = new JButton("Remove All Transactions");
        btn2.addActionListener(e -> clearAllTransactions());

        gbc.gridx = 1;

        panel.add(btn2, gbc);

        return panel;
    }

    private JPanel panelFields(String label, JTextField field) {
        GridBagConstraints gbc = new GridBagConstraints();

        field.setPreferredSize(new Dimension(400, 30));

        JPanel panel = new JPanel();
        JPanel childPanel1 = new JPanel();
        JPanel childPanel2 = new JPanel();

        childPanel2.setOpaque(false);
        childPanel1.setOpaque(false);

        childPanel1.setPreferredSize(new Dimension(200, 0));

        panel.setPreferredSize(new Dimension(500, 30));
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(childPanel1, gbc);

        gbc.gridx = 1;
        gbc.weightx = 4;

        panel.add(childPanel2, gbc);

        panel.setOpaque(false);

        childPanel1.add(new JLabel(label));
        childPanel2.add(field);

        return panel;

    }

    private JPanel panelFields(String label, JComboBox field) {
        GridBagConstraints gbc = new GridBagConstraints();

        field.setPreferredSize(new Dimension(400, 30));

        JPanel panel = new JPanel();
        JPanel childPanel1 = new JPanel();
        JPanel childPanel2 = new JPanel();

        childPanel2.setOpaque(false);
        childPanel1.setOpaque(false);

        childPanel1.setPreferredSize(new Dimension(200, 0));

        panel.setPreferredSize(new Dimension(500, 30));
        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(childPanel1, gbc);

        gbc.gridx = 1;
        gbc.weightx = 4;

        panel.add(childPanel2, gbc);

        panel.setOpaque(false);

        childPanel1.add(new JLabel(label));
        childPanel2.add(field);

        return panel;

    }

    private void panels() {
        JLabel label = new JLabel("Transaction Form");
        formPanel = new JPanel();
        JPanel extraPanel = new JPanel();

        label.setFont(new Font("Arial", Font.BOLD, 24));
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        extraPanel.setOpaque(false);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = .2;

        add(label, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(formPanel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(extraPanel, gbc);

        formFields();

    }
}
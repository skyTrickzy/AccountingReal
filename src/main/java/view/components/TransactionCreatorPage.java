package view.components;

import controller.TransactionController;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
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
        String amountStr = amount.getText();

        try {
            LocalDate newDate = LocalDate.parse(dateStr);
            int intAmount = Integer.parseInt(amountStr);
            controller.addTransaction(newDate, descStr, selectedDebit, selectedCredit, intAmount);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        // transcationController.add(date, description, debit, credit, amount);
        // "CASH" is the debit and "INVENTORY ASSET" to credit, Controller handles the what type of
        // transaction controller handles the type they are. ,
        // CASH becomes ASSET, and so is inventory becoming asset,
        // Cash is then inserted into the debit group and inventory is added to credit group,
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
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(500, 30));
        panel.setOpaque(false);

        button.setPreferredSize(new Dimension(500, 25));
        button.setText("Submit");

        button.addActionListener(e -> onClickEvent());

        panel.add(button);

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
        JLabel label = new JLabel("model.entities.Transaction Form");
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
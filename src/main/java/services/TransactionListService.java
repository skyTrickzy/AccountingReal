package services;

import model.entities.Account;
import model.entities.Accounts;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;
import view.components.AccountsPage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class responsible for modifying the actual list of the {@link TransactionList}
 */
final public class TransactionListService {
    private Map<String, AccountType> accountTypeMap;

    private ArrayList<Transaction> list = TransactionList.getInstance();



    /// note that the accounts may not be accurate as this was done to get the prototype working,
    /// may be changed once the system is nearly complete

    /**
     * an initializer constructor that creates key-value pairs for {@link Account} and its Account Type
     */
    public TransactionListService() {
        accountTypeMap = new HashMap<>();

        accountTypeMap.put("Cash [Asset]", AccountType.ASSET);
        accountTypeMap.put("Accounts Receivable [Asset]", AccountType.ASSET);
        accountTypeMap.put("Inventory [Asset]", AccountType.ASSET);
        accountTypeMap.put("Prepaid Expenses [Asset]", AccountType.ASSET);
        accountTypeMap.put("Equipment [Asset]", AccountType.ASSET);

        accountTypeMap.put("Accounts Payable [Liability]", AccountType.LIABILITY);
        accountTypeMap.put("Notes Payable [Liability]", AccountType.LIABILITY);

        accountTypeMap.put("Owner's Capital [Equity]", AccountType.EQUITY);

        accountTypeMap.put("Sales Revenue [Income]", AccountType.INCOME);
        accountTypeMap.put("Service Revenue [Income]", AccountType.INCOME);

        accountTypeMap.put("Cost of Goods Sold [Expense]", AccountType.EXPENSE);
        accountTypeMap.put("Rent Expense [Expense]", AccountType.EXPENSE);
        accountTypeMap.put("Salaries Expense [Expense]", AccountType.EXPENSE);
        accountTypeMap.put("Utilities Expense [Expense]", AccountType.EXPENSE);


    }


    /**
     * Adds a new transaction to the {@link TransactionList}
     * when adding it uses the key-value pairs instantiated by the constructor
     * to determine the type of account that the debit or credit account has
     *
     *
     *
     * @param date The date of the transaction
     * @param description the description of the transaction
     * @param debit the type of debit of the transaction
     * @param credit the type of credit of the transaction
     * @param amount the amount of the transaction
     *
     * @see Map
     */
    public void add(LocalDate date,
                    String description,
                    String debit,
                    String credit,
                    double amount) {

        Account debitAccount = null;
        Account creditAccount = null;


        if (accountTypeMap.containsKey(debit)) {
            AccountType debitType = accountTypeMap.get(debit);
            debitAccount = new Account(debit, debitType);
        }

        if (accountTypeMap.containsKey(credit)) {
            AccountType creditType = accountTypeMap.get(credit);
            creditAccount = new Account(credit, creditType);
        }

        int state = validate(debitAccount, creditAccount, amount);

        if (state == -1) return;

        Transaction transaction = new Transaction(date, description, debitAccount, creditAccount, amount);
        list.add(transaction);

    }

    public void clearAll() {
        list.clear();
    }

    private int validate(Account debitAccount, Account creditAccount, double amount) {
        ArrayList<Accounts> list = new AccountsPage().getListInstance();

        if (Objects.equals(debitAccount.getAccountName(), creditAccount.getAccountName())) {
            JOptionPane.showMessageDialog(null, "Error: Transaction should not involved having" +
                    "having both the same account be credited and debited eg. Cash on credit and debit", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        if (debitAccount.getType() == AccountType.EQUITY || creditAccount.getType() == AccountType.EQUITY) {
            // Check if either debit or credit account is of type INCOME or EXPENSE
            if (debitAccount.getType() == AccountType.INCOME || debitAccount.getType() == AccountType.EXPENSE ||
                    creditAccount.getType() == AccountType.INCOME || creditAccount.getType() == AccountType.EXPENSE) {

                JOptionPane.showMessageDialog(null, "Error: Equity and Income or Expense should not be directly transacted. Please avoid this transaction.", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
        if (debitAccount.getType() == AccountType.INCOME || creditAccount.getType() == AccountType.INCOME) {
            if (debitAccount.getType() == AccountType.EXPENSE || creditAccount.getType() == AccountType.EXPENSE) {
                JOptionPane.showMessageDialog(null, "Error: Income and Expense accounts should not be directly transacted.", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }



        String errorMessage = null;

        if (creditAccount.getType() == AccountType.ASSET || creditAccount.getType() == AccountType.EXPENSE) {

            String currentCreditAsset = creditAccount.getAccountName();
            String theCreditAccountToCompare = null;

            AccountType currentType = creditAccount.getType();

            for (Accounts a : list) {

                errorMessage = "Credit " + "Error: "  + "in " + a.getAccount().getAccountName() + ", you tried to input an amount of " + amount + ", "
                        + "but the balance for your " + a.getAccount().getAccountName() + " is: "
                        + "P" + a.getAmount();

                AccountType temp = a.getAccount().getType();
                if (temp == currentType)
                    theCreditAccountToCompare = a.getAccount().getAccountName();
                else continue;

                if (currentCreditAsset.contains(theCreditAccountToCompare)) {
                    double totalBalanceAccount = a.getAmount();
                    if (amount > totalBalanceAccount) {
                        JOptionPane.showMessageDialog(null, errorMessage, "Insufficient balance", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                }
            }
        }


        if (debitAccount.getType() == AccountType.EQUITY ||
                debitAccount.getType() == AccountType.LIABILITY ||
            debitAccount.getType() == AccountType.INCOME) {

            String currentCreditAsset = debitAccount.getAccountName();
            String theCreditAccountToCompare = null;

            AccountType currentType = debitAccount.getType();

            for (Accounts a : list) {
                errorMessage = "Debit " + "Error: "  + "in " + a.getAccount().getAccountName() + ", you tried to input an amount of " + amount + ", "
                        + "but the balance for your " + a.getAccount().getAccountName() + " is: "
                        + "P" + a.getAmount() + " Either you don't have balance or you paid more than what is needed";

                AccountType temp = a.getAccount().getType();
                if (temp == currentType)
                    theCreditAccountToCompare = a.getAccount().getAccountName();
                else continue;

                if (currentCreditAsset.contains(theCreditAccountToCompare)) {
                    double totalBalanceAccount = a.getAmount();
                    if (amount > totalBalanceAccount) {
                        JOptionPane.showMessageDialog(null, errorMessage, "Insufficient balance, or Overpaying", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
}
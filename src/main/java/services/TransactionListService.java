package services;

import model.entities.Account;
import model.entities.Accounts;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;
import view.components.AccountsPage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

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

        int state = validate( debitAccount, creditAccount, amount, date);

        if (state == -1) return;

        Transaction transaction = new Transaction(date, description, debitAccount, creditAccount, amount);
        list.add(transaction);

        String message = "The entry has been succesfully submitted.\n"
                + "Date: " + date + "\n"
                + "Description: " + description + "\n"
                + "Debit Account: " + debitAccount.getAccountName() + "\n"
                + "Credit Account: " + creditAccount.getAccountName() + "\n"
                + "Amount: " + amount + "\n"
                + "Thank you for using our application!";

        JOptionPane.showMessageDialog(null, message, "Entry submitted Successfully", JOptionPane.INFORMATION_MESSAGE);

    }

    public void clearAll() {
        list.clear();
    }

    private int validate(Account debitAccount, Account creditAccount, double amount, LocalDate date) {
        ArrayList<Accounts> list = new AccountsPage().getListInstance();

        if (date.isAfter(LocalDate.now())) {
            String message = "Error: The entry date (" + date + ") cannot be in the future!";
            JOptionPane.showMessageDialog(null, message, "Date Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        if (amount <= 0 ) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error: Transaction amounts cannot be zero or negative.\n" +
                            "All debit and credit amounts must be greater than zero to maintain accounting integrity\n" +
                            "and ensure proper balance of the accounting equation.",
                    "Invalid Entry",
                    JOptionPane.ERROR_MESSAGE
            );
            return -1;
        }

        if (Objects.equals(debitAccount.getAccountName(), creditAccount.getAccountName())) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error: A transaction cannot involve the same account being both credited and debited.\n" +
                            "Please check your entries to ensure each account is used correctly.",
                    "Invalid Transaction",
                    JOptionPane.ERROR_MESSAGE
            );

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

                errorMessage = "Credit Error: Invalid input for account '" + a.getAccount().getAccountName() + "'.\n" +
                        "You attempted to input an amount of P" + amount + ", but the current balance for this account is P" + a.getAmount() + ".\n" +
                        "Please ensure that the transaction amount is within the available balance.";

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
                errorMessage = "Error: Invalid debit entry for account '" + a.getAccount().getAccountName() + "'.\n" +
                        "You attempted to debit an amount of P" + amount + ", but the current balance for this account is P" + a.getAmount() + ".\n" +
                        "Either the account has insufficient funds, or the amount debited exceeds the available balance.";


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
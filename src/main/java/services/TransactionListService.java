package services;

import model.entities.Account;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.enums.AccountType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            System.out.println(debit);
            AccountType debitType = accountTypeMap.get(debit);
            debitAccount = new Account(debit, debitType);
        }

        if (accountTypeMap.containsKey(credit)) {
            AccountType creditType = accountTypeMap.get(credit);
            creditAccount = new Account(credit, creditType);
        }

        Transaction transaction = new Transaction(date, description, debitAccount, creditAccount, amount);
        list.add(transaction);



    }

    public void clearAll() {
        list.clear();
    }
}
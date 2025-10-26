package model.entities;

import java.util.ArrayList;


public class TransactionList {
    /**
     * A final instance so that we dont have to instantiate again
     */
    private static final ArrayList<Transaction> instance = new ArrayList<>();

    /**
     * Privately instantiated to avoid instantiated on outside
     */
    private TransactionList() {}

    /**
     * retrieves the instance that represents the original list of the transactions
     *
     * @return {@code instance}
     */
    public static ArrayList<Transaction> getInstance() {
        return instance;
    }
}
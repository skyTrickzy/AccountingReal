package interfaces;

import model.entities.Transaction;

import java.util.ArrayList;

/**
 * This is only used for tables that have filters or search bar,
 */
public interface FilterableService {

    /**
     * An interface for filterable that customizes the list based on certain inputs,
     * only applicable if the page has certain more features like SearchBar or Filter category
     *
     * @param e
     * @return void
     */
    ArrayList<Transaction> filterable(String e);
}

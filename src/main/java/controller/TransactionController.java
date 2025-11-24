package controller;

import services.TransactionListService;
import view.components.TransactionsPage;
import view.custom.JTableDisplay;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

final public class TransactionController {
    private JTableDisplay tableModel;
    private TransactionListService service = new TransactionListService();

    /**
     * {@code models} represents the list of {@link JTableDisplay} that extends the {@link AbstractTableModel}
     * <p>The reason why the variable is declared as static and final is we dont want to recreate the variables
     * <p>Everytime we want to create an instance of an object {@link TransactionController}
     */
    private static final ArrayList<JTableDisplay> models = new ArrayList<>();

    /**
     * <p> constructor that takes {@link JTableDisplay} as
     * its parameter
     *
     * @param tableModel this is crucial so we can update the UI of each table
     */
    public TransactionController(JTableDisplay tableModel) {
        this.tableModel = tableModel;
        models.add(tableModel);
    }

    public TransactionController() {}

    public void addTransaction(LocalDate date,
                               java.lang.String description,
                               java.lang.String debit,
                               java.lang.String credit,
                               double amount) {

        service.add(date, description, debit, credit, amount);

        models.forEach(i ->
            i.displayList(""));
    }

    /**
     * A method that manipulates the list based on the event passed to the Parameter
     *
     * For Example:
     *
     *
     * <blockquote>
     *     <pre>
     * // String
     * filterEvent(new EventPassed<>("String"));
     * filterEvent(new EventPassed<>(1));
     * filterEvent(new EventPassed<>(Object));
     *     </pre>
     * </blockquote>
     * </pre>
     *
     * See the Implementation at {@link TransactionsPage}
     *
     * @param event Represents an event with a custom Variable Datatype accepted
     */
    public void filterEvent(String event) {
            tableModel.displayList(event);
    }

    public void clearTransactions() {
        service.clearAll();
        models.forEach(i -> i.displayList(null));
    }
}

package controller;

import interfaces.FilterableService;
import interfaces.UpdateListener;
import model.entities.EventPassed;
import model.entities.Executor;
import model.entities.Transaction;
import model.entities.TransactionList;
import model.tables.TransactionTableModel;
import services.TransactionListService;
import view.components.TransactionsPage;
import view.custom.JTableDisplay;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionController {
    private JTableDisplay tableModel;
    private TransactionListService service = new TransactionListService();

    /**
     * {@code models} represents the list of {@link JTableDisplay} that extends the {@link AbstractTableModel}
     * <p>The reason why the variable is declared as static and final is we dont want to recreate the variables
     * <p>Everytime we want to create an instance of an object {@link TransactionController}
     */
    private static final ArrayList<JTableDisplay> models = new ArrayList<>();
    private static final Executor executor = new Executor();

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
                               String description,
                               String debit,
                               String credit,
                               int amount) {

        service.add(date, description, debit, credit, amount);
        models.forEach(i -> i.displayList(null));
        executor.execute();
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
    public void filterEvent(EventPassed<?> event) {
        if (event.getType() instanceof String) {
            String query = (String) event.getType();
            tableModel.displayList(query);
            executor.execute();
        }
    }


    /**
     * <p>a method that updates the UI.
     * see {@link TransactionsPage}
     * for the implementation of this method
     * @param a
     */
    public void onUpdate(UpdateListener a) {
        executor.add(a);
    }
}

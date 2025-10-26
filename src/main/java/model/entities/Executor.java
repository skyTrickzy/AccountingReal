package model.entities;

import controller.TransactionController;
import interfaces.UpdateListener;

import java.util.ArrayList;

/**
 * A class that contains the list of {@code Interface UpdateListener}
 * this class is responsible for executing the {@code onEvent} method.
 *
 */
public class Executor {
    ArrayList<UpdateListener> updateListeners = new ArrayList<>();

    /**
     * It adds the updateListener to the ArrayList updateListeners
     *
     * @param l represents the {@code onEvent} method
     */
    public void add(UpdateListener l) {
        updateListeners.add(l);
    }

    /**
     * it executes each {@link UpdateListener} {@code onEvent}  method
     *
     * <pre>Usage of the this class below</pre>
     * @see TransactionController
     *
     */
    public void execute() {
        for (UpdateListener a : updateListeners) {
            a.onEvent();
        }
    }
}

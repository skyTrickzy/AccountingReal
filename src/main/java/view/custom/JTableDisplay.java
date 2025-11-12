package view.custom;

import javax.swing.table.AbstractTableModel;

public abstract class JTableDisplay extends AbstractTableModel {
    /**
     * An Abstract method used to for the displayList method
     * despite its name it actually doesn't update the UI itself
     * but depending on how you write it, you can do that,
     * it calls for other services
     *
     * See its implementation in the package {@code tables} in {@code model} package
     *
     * @param query can be {@code null} if default
     */
    public abstract void displayList(String query);


    public void DebugPrint() {
        System.out.println(getClass().getName() + " Succesfuly executed");
    }
}

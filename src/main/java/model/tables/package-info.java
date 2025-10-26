/**
 * this package handles how the table should be displayed
 * it uses {@link javax.swing.table.AbstractTableModel} or the
 * Abstracted Model for the JTable if you don't know the JTable
 * can have custom model,
 * and if you want to get started you can just ask chatgpt how the AbstractTableModel works
 * and also you should use the {@link view.custom.JTableDisplay} instead of the AbstractModel
 * because theres an additional method that I've implemented just so that method {@code DisplayList}
 * that is called in {@link controller.TransactionController} can call it, see it four yourself
 *
 *
 * <p>Rule is please make a seperate file for each JTables, like for example GeneralLedge,
 * make a GeneralLedgerModel
 */
package model.tables;
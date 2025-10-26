/**
 * I don't know a thing about MVC sorry.
 * I don't know what to say
 * to ensure that your ui gets updated please use the
 * {@code TransactionController.onUpdate()} on the component itself, not on the table
 * or because why not?
 *
 *
 * <p>
 *  when going to any components that has tables in it, i mean not the tables but
 *  its parent instead. like for example BalanceSheetPage, to ensure the UI gets updated
 *  everytime the button is clicked make sure to do this at any parent comps
 *
 *  <blockquote>
 *      <pre>
 *   AccountsTableModel tableModel = new AccountsTableModel();
 *   AccountsTable table = new AccountsTable(tableModel);
 *   TransactionController controller = new TransactionController(tableModel);
 *      </pre>
 *  </blockquote>
 *
 *  <p>
 * the reason is that {@code tableModel} represents how you want the date to be displayed
 * the {@code table} is then instantiated,
 * you will want to pass the {@code tableModel} object
 * to the {@code table} because table has {@code setModel} that basically means a data provider
 * to the UI
 * so once again learn AbstractTableModel
 *
 * <p>
 * As i said earlier you may want to use the {@code onUpdate} provided by the {@link controller.TransactionController}
 * and put it on  any component
 * see {@link view.components.AccountsPage} or {@link view.components.TransactionsPage} for the implementation
 * you do not need to do extra stuff with it,
 * all you need to do is pretty much use the {@code onUpdate} to invoke the {@code DisplayList} from {@link view.custom.JTableDisplay}
 * </p>
 *
 * <p>
 * you don't need to adjust the UI you can start making the table right away lmao
 *
 * TLDR: I dont know what Im doing
 *
 * giatay!!!!!
 * waku kasabot sa kong gipanghimo!
 * </p>
 */
package view.components;
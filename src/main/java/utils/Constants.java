package utils;

import java.awt.*;

public class Constants {
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 700;
    public static final Font HEADER_FONT_ARIAL = new Font("Arial", Font.BOLD, 36);
    public static final String[] INDICATORS =
            {
                    "Transaction", "Transactions", "Accounts",
                    "General Journal", "General Ledger", "Balance Sheet"
            };
    public static final Color BG_COLOR = new Color(224, 224, 224);

    public static final String[] ACCOUNTS = {
            "Cash [Asset]",
            "Accounts Receivable [Asset]",
            "Inventory [Asset]",
            "Prepaid Expenses [Asset]",
            "Equipment [Asset]",
            "Accounts Payable [Liability]",
            "Notes Payable [Liability]",
            "Owner's Capital [Equity]",
            "Sales Revenue [Income]",
            "Service Revenue [Income]",
            "Cost of Goods Sold [Expense]",
            "Rent Expense [Expense]",
            "Salaries Expense [Expense]",
            "Utilities Expense [Expense]",
//            "Revenue [Income]",
//            "Cost of Goods Sold [Expense]",
//            "Operating Expenses [Expense]",
//            "Interest Expense [Expense]",
//            "Depreciation [Expense]",
//            "Income Tax Expense [Expense]",
//            "Other Income [Income]",
//            "Other Expenses [Expense]",
    };

}
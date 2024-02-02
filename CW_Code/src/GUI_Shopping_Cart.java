import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUI_Shopping_Cart {
    private static DefaultTableModel tableModel;
    private static JLabel totalPriceLabel;
    private static JLabel firstDiscountLabel;
    private static JLabel sameCategoryDiscountLabel;
    private static JLabel finalPriceLabel;

    public static void cart_GUI() {

        // Create frame
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(1000, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                GUI_Westminster_Cart.setShoppingCartOpen(false);
            }
        });

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        // Create JTable with the table model
        JTable shoppingCartTable = new JTable(tableModel);

        // Create JScrollPane to enable scrolling
        JScrollPane tableScrollPane = new JScrollPane(shoppingCartTable);

        // Set row height
        int rowHeight = 60;
        shoppingCartTable.setRowHeight(rowHeight);

        // Bold the column headers
        Font headerFont = new Font(shoppingCartTable.getTableHeader().getFont().getName(), Font.BOLD, shoppingCartTable.getTableHeader().getFont().getSize());
        shoppingCartTable.getTableHeader().setFont(headerFont);


        frame.setLayout(null);
        tableScrollPane.setBounds(50, 50, 900, 350);

        // Center align the content inside the table
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);


        // Set center alignment for each column
        for (int i = 0; i < shoppingCartTable.getColumnCount(); i++) {
            shoppingCartTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        frame.add(tableScrollPane);


        // Label for the total price
        totalPriceLabel = new JLabel("Total                    0.00 £");
        totalPriceLabel.setBounds(700, 450, 200, 30);
        frame.add(totalPriceLabel);

        // Label for the 10% discount
        firstDiscountLabel = new JLabel("First Purchase Discount (10%)                  - 0.00 £");
        firstDiscountLabel.setBounds(560, 480, 400, 30);
        frame.add(firstDiscountLabel);

        // Label for the 20% discount
        sameCategoryDiscountLabel = new JLabel("Three Items in same Category Discount (20%)                  - 0.00 £");
        sameCategoryDiscountLabel.setBounds(475, 510, 600, 30);
        frame.add(sameCategoryDiscountLabel);

        // Label for final price
        finalPriceLabel = new JLabel("Final Total                    0.00 £");
        finalPriceLabel.setBounds(675, 550, 200, 30);
        frame.add(finalPriceLabel);

        frame.setVisible(true);
    }

    /**
     * Add product to the shopping cart.
     * If the product is already in the cart, updates the quantity and total price.
     * Checks if the quantity exceeds the available items and prints a warning if it does.
     * @param product product to be added to the shopping cart.
     */
    public static void addToCart(Product product) {
        if (tableModel == null) {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Product");
            tableModel.addColumn("Quantity");
            tableModel.addColumn("Price");
        }

        // Find index of the product in the cart
        int rowIndex = findProductRowIndex(product);
        int availableItemCount = product.getAvailableItemCount();

        if (rowIndex != -1) {
            // If product is already in the cart, update the quantity
            String quantityValue = tableModel.getValueAt(rowIndex, 1).toString();
            int currentQuantity = quantityValue.isEmpty() ? 0 : Integer.parseInt(quantityValue);
            int newQuantity = currentQuantity + 1;

            // Checks if the quantity exceeds the available items
            if (newQuantity > availableItemCount) {
                System.out.println("Quantity exceeds the available items in the Westminster Shopping Cart!");
                return;
            }
            tableModel.setValueAt(newQuantity, rowIndex, 1);

            // Calculate total price
            double itemPrice = product.getItemPrice();
            double totalPrice = newQuantity * itemPrice;
            tableModel.setValueAt(totalPrice + "£", rowIndex, 2);
        } else {
            double totalPrice = product.getItemPrice();

            // Check if the quantity exceeds the available items
            if (1 > availableItemCount) {
                System.out.println("Quantity exceeds the available items in the Westminster Shopping Cart!");
                return;
            }
            tableModel.addRow(new Object[]{getProductInfoString(product), 1, totalPrice + "£"});
        }

        // Refresh the table to reflect the changes
        refreshTable();

        // Update the total price label
        updateTotalPriceLabel();
    }

    /**
     * Constructs an HTML-formatted string containing information about the given product.
     * @param product product for which information is generated.
     * @return HTML-formatted string containing product details.
     */
    private static String getProductInfoString(Product product) {
        StringBuilder productInfo = new StringBuilder("<html>");
        productInfo.append(product.getProductID()).append("<br>").append(product.getProductName());

        if (product instanceof Clothing clothingProduct) {
            productInfo.append("<br>").append(clothingProduct.getItemColour()).append(", ").append(clothingProduct.getItemSize());
        }

        if (product instanceof Electronics electronicsProduct) {
            productInfo.append("<br>").append(electronicsProduct.getItemBrand()).append(", ").append(electronicsProduct.getWarrantyPeriod()).append(" weeks");
        }

        productInfo.append("</html>");
        return productInfo.toString();
    }

    /**
     * Refreshes the user cart table
     */
    private static void refreshTable() {
        tableModel.fireTableDataChanged();
    }

    /**
     * Finds the row index of a product in the shopping cart table model.
     * @param product the checked row index of the product.
     * @return The row index of the product.
     * -1 is returned if the product is not found in the table.
     */
    private static int findProductRowIndex(Product product) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String productNameInCart = tableModel.getValueAt(i, 0).toString();
            if (productNameInCart.contains(product.getProductName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates the labels below the table
     * Calculates the total price, discounted prices, and final price.
     */
    private static void updateTotalPriceLabel() {
        double totalPrice = 0;
        double discountedPrice;
        double categoryDiscount = 0;
        double finalPrice;

        // Map to store quantities for each category
        Map<String, Integer> categoryQuantities = new HashMap<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String category = getCategoryFromProductInfo(tableModel.getValueAt(i, 0).toString());
            int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());

            categoryQuantities.put(category, categoryQuantities.getOrDefault(category, 0) + quantity);

            String totalPriceValue = tableModel.getValueAt(i, 2).toString();
            totalPrice += Double.parseDouble(totalPriceValue.replace("£", ""));
        }

        // Apply 10% discount
        discountedPrice = (totalPrice * 10/100);

        // Checks if there are three or more items from a category
        for (int quantity : categoryQuantities.values()) {
            if (quantity >= 3) {
                // Apply 20% discount
                categoryDiscount = (totalPrice * 20/100);
                break; // Exit the loop once discount is applied
            }
        }

        // Deduct Discounts
        finalPrice = totalPrice - (discountedPrice + categoryDiscount);

        // Update labels with the new prices
        totalPriceLabel.setText("Total                    " + String.format("%.2f £", totalPrice));
        firstDiscountLabel.setText("First Purchase Discount (10%)                  - " + String.format("%.2f £", discountedPrice));
        sameCategoryDiscountLabel.setText("Three Items in same Category Discount (20%)                  - " + String.format("%.2f £", categoryDiscount));
        finalPriceLabel.setText("Final Total                    " + String.format("%.2f £", finalPrice));

        // Make finalPriceLabel bold
        Font boldFont = new Font(finalPriceLabel.getFont().getName(), Font.BOLD, finalPriceLabel.getFont().getSize());
        finalPriceLabel.setFont(boldFont);
    }

    /**
     * Extracts the category from the product info of a product
     * @param productInfo The product information.
     * @return the category of the product
     */
    private static String getCategoryFromProductInfo(String productInfo) {
        if (productInfo.contains("Clothing")) {
            return "Clothing";
        } else if (productInfo.contains("Electronic")) {
            return "Electronic";
        } else {
            return "";
        }
    }
}

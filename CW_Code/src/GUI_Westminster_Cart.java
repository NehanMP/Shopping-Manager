import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUI_Westminster_Cart {
    ArrayList<Product> productList = new ArrayList<>();
    DefaultTableModel tableModel1;
    JComboBox<String> dropDown;
    private JPanel bottomPanel;
    private JTable productTable;
    public static boolean isShoppingCartOpen = false;

    public void printGUI() {
        // Initialize the product list of Westminster Shopping Manager
        productList = WestminsterShoppingManager.getProductList();

        // Create frame
        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setSize(1000, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top Panel to view the products
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1000, 400);
        topPanel.setLayout(null);
        frame.add(topPanel);

        // Create Shopping Cart button
        JButton shopCartButton = new JButton("Shopping Cart");
        shopCartButton.setBounds(850, 10, 120, 30);
        shopCartButton.setFocusable(false);
        topPanel.add(shopCartButton);

        // Add ActionListener to the shopCartButton
        shopCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Checks if the Shopping Cart is open
                if (!isShoppingCartOpen){
                    // Create GUI_Shopping_Cart with the frame
                    GUI_Shopping_Cart.cart_GUI();
                } else {
                    System.out.println("Shopping Cart is already open.");
                }
                isShoppingCartOpen = true;
            }
        });

        // Create Category Label
        JLabel categoryLabel = new JLabel("Select Product Category");

        // Increase font size
        Font labelFont = categoryLabel.getFont();
        categoryLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 15));

        categoryLabel.setBounds(260, 20, 200, 30);
        topPanel.add(categoryLabel);

        // Create drop down
        String[] productCategory = {"All", "Electronic", "Clothing"};
        dropDown = new JComboBox<>(productCategory);

        // Setting alignment to right for the drop-down items
        dropDown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel labelRenderer) {
                    labelRenderer.setHorizontalAlignment(value.equals(dropDown.getSelectedItem()) ? SwingConstants.RIGHT : SwingConstants.CENTER);
                }
                return renderer;
            }
        });
        dropDown.setBounds(450, 20, 200, 30);
        dropDown.setSelectedIndex(0);


        // Add ActionListener to the drop-down
        dropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableData();
            }
        });
        topPanel.add(dropDown);


        // Create table to show products
        tableModel1 = new DefaultTableModel();
        tableModel1.addColumn("ProductID");
        tableModel1.addColumn("Name");
        tableModel1.addColumn("Category");
        tableModel1.addColumn("Price(£)");
        tableModel1.addColumn("Info");

        updateTableData();

        // Center align the content inside the table
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);


        productTable = new JTable(tableModel1);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        // Set row height
        productTable.setRowHeight(40);

        // Making column headers bold
        Font headerFont = productTable.getTableHeader().getFont();
        Map<TextAttribute, Float> fontAttributes = new HashMap<>();


        fontAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        Font boldHeaderFont = headerFont.deriveFont(fontAttributes);
        JTableHeader header = productTable.getTableHeader();


        // Row Height of Column Name
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        header.setFont(boldHeaderFont);


        // Scroll Pane for the table
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBounds(50, 120, 900, 250);
        topPanel.add(tableScrollPane);


        // Bottom panel to view a selected item from the table
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBounds(0, 400, 1000, 400);
        bottomPanel.setLayout(null);
        frame.add(bottomPanel);

        // Create title Label
        JLabel titleLabel = new JLabel("Selected Product - Details");
        Font boldFont = new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize());
        titleLabel.setFont(boldFont);

        titleLabel.setBounds(40, 420, 200, 30);
        bottomPanel.add(titleLabel);

        // Add ListSelectionListener to the productTable
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the selected product
                Product selectedProduct = productList.get(selectedRow);

                // Update details in the bottom panel
                updateBottomPanel(selectedProduct);
            }
        });
        frame.setVisible(true);
    }

    /**
     * Updates the bottom panel with details of the selected product in the table.
     * @param selectedProduct Details that are displayed in the bottom panel for a selected product.
     */
    private void updateBottomPanel(Product selectedProduct) {
        // Ensure that bottomPanel is not null
        if (bottomPanel == null) {
            bottomPanel = new JPanel();
        }
        // Clear existing data in the bottom panel
        bottomPanel.removeAll();

        if (selectedProduct != null) {
            // Create title Label
            JLabel titleLabel = new JLabel("Selected Product - Details");
            Font boldFont = new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize());
            titleLabel.setFont(boldFont);


            titleLabel.setBounds(40, 420, 200, 30);
            bottomPanel.add(titleLabel);


            // Create labels for the details
            JLabel productIdLabel = new JLabel("Product Id: " + selectedProduct.getProductID());
            productIdLabel.setBounds(40, 460, 200, 30);
            bottomPanel.add(productIdLabel);


            JLabel categoryLabel = new JLabel("Category: " + getCategory(selectedProduct));
            categoryLabel.setBounds(40, 490, 200, 30);
            bottomPanel.add(categoryLabel);


            JLabel nameLabel = new JLabel("Name: " + selectedProduct.getProductName());
            nameLabel.setBounds(40, 520, 200, 30);
            bottomPanel.add(nameLabel);


            // Check category to add specific details for Clothing or Electronics Products
            if (selectedProduct instanceof Clothing) {
                JLabel colorLabel = new JLabel("Size: " + ((Clothing) selectedProduct).getItemColour());
                colorLabel.setBounds(40, 580, 200, 30);
                bottomPanel.add(colorLabel);

                JLabel sizeLabel = new JLabel("Color: " + ((Clothing) selectedProduct).getItemSize());
                sizeLabel.setBounds(40, 550, 200, 30);
                bottomPanel.add(sizeLabel);


            } else if (selectedProduct instanceof Electronics) {
                JLabel brandLabel = new JLabel("Brand: " + ((Electronics) selectedProduct).getItemBrand());
                brandLabel.setBounds(40, 550, 200, 30);
                bottomPanel.add(brandLabel);

                JLabel warrantyLabel = new JLabel("Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + " weeks");
                warrantyLabel.setBounds(40, 580, 200, 30);
                bottomPanel.add(warrantyLabel);
            }

            JLabel itemsAvailableLabel = new JLabel("Items Available: " + selectedProduct.getAvailableItemCount());
            itemsAvailableLabel.setBounds(40, 610, 200, 30);
            bottomPanel.add(itemsAvailableLabel);


            JButton addProductButton = new JButton("Add to shopping Cart");
            addProductButton.setBounds(400, 700, 200, 30);
            addProductButton.setFocusable(false);


            // Add ActionListener to add Product Button
            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the selected product
                        Product selectedProduct = productList.get(selectedRow);

                        // Add the selected product to the shopping cart
                        GUI_Shopping_Cart.addToCart(selectedProduct);
                    }
                }
            });
            bottomPanel.add(addProductButton);
        }

        // Refresh the bottom panel
        bottomPanel.revalidate();
        bottomPanel.repaint(0, 400, 1000, 400);
    }

    /**
     * Checks the category of a given product
     * @param selectedProduct product for which the category is checked.
     * @return the category of the product
     */
    private String getCategory(Product selectedProduct) {
        if (selectedProduct instanceof Clothing) {
            return "Clothing";
        } else if (selectedProduct instanceof Electronics) {
            return "Electronic";
        } else {
            return "";
        }
    }

    /**
     * Erase the existing data in the table
     * Filters the table data based on the category selected in the dropdown
     * Add rows for products matching the selected category.
     */
    private void updateTableData() {
        // Clear existing data
        while (tableModel1.getRowCount() > 0) {
            tableModel1.removeRow(0);
        }
        // Product selection in the drop-down
        String selectedCategory = (String) dropDown.getSelectedItem();

        for (Product product : productList) {
            assert selectedCategory != null;
            if (selectedCategory.equals("All") ||
                    (product instanceof Electronics && selectedCategory.equals("Electronic")) ||
                    (product instanceof Clothing && selectedCategory.equals("Clothing"))) {

                if (product instanceof Electronics) {
                    tableModel1.addRow(new Object[]{
                            product.getProductID(),
                            product.getProductName(),
                            "Electronic",
                            product.getItemPrice(),
                            ((Electronics) product).getItemBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty"
                    });

                } else if (product instanceof Clothing) {
                    tableModel1.addRow(new Object[]{
                            product.getProductID(),
                            product.getProductName(),
                            "Clothing",
                            product.getItemPrice(),
                            ((Clothing) product).getItemColour() + ", " + ((Clothing) product).getItemSize()
                    });
                }
            }
        }
        updateBottomPanel(null);
    }

    // Setter for ShoppingCartOpen
    public static void setShoppingCartOpen(boolean isOpen) {
        isShoppingCartOpen = isOpen;
    }

}

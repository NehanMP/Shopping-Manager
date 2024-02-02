import java.io.*;
import java.util.*;

public class WestminsterShoppingManager extends GUI_Westminster_Cart implements ShoppingManager {
    private final Scanner scanner;
    private static final String idCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Creating an arraylist instantiating the Product class
    static ArrayList<Product> productList = new ArrayList<>();

    // Declaring Scanner, so it can be used in all occasions throughout the program
    public WestminsterShoppingManager() {
        this.scanner = new Scanner(System.in);
    }

    // Initializing variables
    public static int command;
    public static int productType;

    @Override
    public void printMenu() {
        // Load data from the text file by giving the specific file path
        String filepath = "E:\\IIT\\2nd Year\\OOP\\CW\\CW_Code\\Products.txt";
        loadData(filepath, productList);

        while (true) {
            // Menu options
            System.out.println("***** Welcome to Online Westminster Shopping Manager *****");
            System.out.println();
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print product list");
            System.out.println("4. Save to a file");
            System.out.println("5. Open GUI_Westminster_Cart");
            System.out.println("6. Exit Shopping Manager");
            System.out.println();

            try {
                //Get user command with exception handling to repeat if user gives an invalid input
                System.out.print("Enter a command: ");
                command = Integer.parseInt(scanner.nextLine());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numerical command stated in the above menu options");
                continue;
            }
            // Get user input to add an Electronic or a Clothing item
            if (command == 1) {
                try {
                    System.out.print("Which product do you wish to add (1)Electronic (2)Clothing: ");
                    productType = Integer.parseInt(scanner.nextLine());
                    addProducts();
                } catch (NumberFormatException e) {
                    System.out.println("Enter (1) for Electronics item and enter (2) for Clothing item");
                }
            } else if (command == 2){
                removeProducts();

            } else if (command == 3) {
                printProducts();

            } else if (command == 4) {
                // File path of the file to erase data
                String filePathToErase = "E:\\IIT\\2nd Year\\OOP\\CW\\CW_Code\\Products.txt";

                // Erase already existing data in the text file
                eraseFileInfo(filePathToErase);

                // Give the filepath
                filepath = "E:\\IIT\\2nd Year\\OOP\\CW\\CW_Code\\Products.txt";

                // Write products to the file
                writeToFile(productList, filepath);

            } else if (command == 5) {
                System.out.print("Enter User Name: ");
                String userName = scanner.nextLine();

                System.out.print("Enter User password: ");
                String password = scanner.nextLine();

                System.out.print("Open GUI (1), Run in prompt (2): ");
                int promptGUI = Integer.parseInt(scanner.nextLine());

                ShoppingCart userSystem = new ShoppingCart(userName, password); // Create new object of ShoppingCart class
                userSystem.setUserProductList(productList); // Pass the productList to the class ShoppingCart

                if (promptGUI == 1){
                    shoppingCartGUI();
                } else if (promptGUI == 2){
                    userSystem.UserDetails();
                } else {
                    System.out.println("Invalid Input");
                }

            } else if (command == 6) {
                System.out.println("Thank you for shopping at Westminster Shopping Manager");
                return; // When false returns it exits the program
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    /**
     * Removes the product when user gives the ID of a specific Product
     * If ID does exist it will be removed and if ID does not exist a message will be displayed.
     * Exception Handling is implemented if user gives an invalid ID
     */
    @Override
    public void removeProducts() {
        try {
            System.out.print("Enter product ID to remove: ");
            String deleteProductID = scanner.nextLine();

            // Iterating through the product List
            Iterator<Product> iterator = productList.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getProductID().equals(deleteProductID)) {
                    iterator.remove();
                    System.out.println("ProductID: " + deleteProductID + " was removed from the product list.");
                    return;
                }
            }
            System.out.println("ProductID: " + deleteProductID + " does not exist in the product list");
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid ProductID");
        }
    }

    /**
     * Prints the products in the product List.
     * List is sorted using the sortProductList() method
     * If the List is empty, appropriate message will be displayed
     * Checks before printing whether the product is an Electronics or a Clothing
     */
    @Override
    public void printProducts(){
        // Check whether the Product list is empty
        if (productList.isEmpty()){
            System.out.println("There are no products in the list");
        } else {

            // Sorting out the ProductList
            sortProductList();

            // For loops which iterate through all products in the product list
            for (Product item : productList) {
                // Checking if the item is an electronic or a Clothing
                if (item instanceof Electronics){
                    System.out.println("Electronics Item");
                    System.out.println("================");
                    System.out.println("Product ID: " + item.getProductID());
                    System.out.println("Product Name: " + item.getProductName());
                    System.out.println("Available Product Count: " + item.getAvailableItemCount());
                    System.out.println("Product Price: " + item.getItemPrice());
                    System.out.println("Product Brand: " + ((Electronics) item).getItemBrand());
                    System.out.println("Product warranty period: " + ((Electronics) item).getWarrantyPeriod());
                } else {
                    System.out.println("Clothing Item");
                    System.out.println("=============");
                    System.out.println("Product ID: " + item.getProductID());
                    System.out.println("Product Name: " + item.getProductName());
                    System.out.println("Available Product Count: " + item.getAvailableItemCount());
                    System.out.println("Product Price: " + item.getItemPrice());
                    System.out.println("Product Colour: " + ((Clothing) item).getItemColour());
                    System.out.println("Product Size: " + ((Clothing) item).getItemSize());
                }
                System.out.println();
            }
        }
    }

    /**
     *  Calls method createProduct and add the products in it to the productList
     *  Checks whether the number of products exceeds 50.
     */
    @Override
    public void addProducts() {
        if (productList.size() >= 50) {
            System.out.println("50 products limit reached. Remove an existing product to add a new one.");
        } else {
            productList.add(createProduct());
            System.out.println("Product added successfully.");
        }
    }

    /**
     * Calls method printGUI from the GUI_Westminster_Cart
     */
    @Override
    public void shoppingCartGUI() {
        printGUI();
    }

    /**
     * Creates a new product based on user entered product details to both product types Electronics and Clothing
     * Checks whether the total Product count does not exceed and if exceeded product will not be added
     * @return the product created of instance Product class and null if the product is not created
     */
    public Product createProduct() {
        boolean productCreated = false;

        while (!productCreated) {
            try {
                String ProductID = generateID();
                System.out.println("ProductID: " + ProductID);

                System.out.print("Enter product name: ");
                String ProductName = scanner.nextLine();

                System.out.print("Enter available products: ");
                int availableItemCount = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter product price: Rs.");
                double itemPrice = Double.parseDouble(scanner.nextLine());

                if (productType == 1) {
                    // Creating an Electronic Product
                    System.out.print("Enter product brand: ");
                    String itemBrand = scanner.nextLine();

                    System.out.print("Enter product warranty Period (Weeks): ");
                    int warrantyPeriod = Integer.parseInt(scanner.nextLine());

                    productCreated = true;
                    // Returning values of the Electronic Item
                    return new Electronics(ProductID, ProductName, availableItemCount, itemPrice, itemBrand, warrantyPeriod);


                } else if (productType == 2) {
                    // Creating a Clothing Product
                    System.out.print("Enter product colour: ");
                    String itemColour = scanner.nextLine();

                    System.out.print("Enter product size (S - XL): ");
                    String itemSize = scanner.nextLine();

                    productCreated = true;
                    // Returning values of the Clothing Item
                    return new Clothing(ProductID, ProductName, availableItemCount, itemPrice, itemColour, itemSize);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid product values.");
            }
        }
        return null;
    }

    /**
     * Sorts the Product IDs of each an every Product and arrange it in ascending order
     * Uses a comparator to compare Product IDs.
     */
    public void sortProductList() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product item1, Product item2) {

                // Compares product IDs of each an every product
                return item1.getProductID().compareTo(item2.getProductID());
            }
        });
    }

    /**
     * Generate a random 10-digit number with numbers and letters
     * Uses a StringBuilder to construct the ID randomly selected characters from the defined variable idCharacters
     * @return the product ID generated
     */
    private String generateID(){
        // StringBuilder with 10 characters capacity
        StringBuilder stringBuilder = new StringBuilder(10);

        //Random object
        Random random = new Random();

        for(int i = 0; i< 10; i++){
            // Generates an index to select the character
            int randomNumber = random.nextInt(idCharacters.length());

            // Selects the random index character
            char randomCharacters = idCharacters.charAt(randomNumber);

            // Append random character to the stringBuilder
            stringBuilder.append(randomCharacters);
        }
        // Converts the StringBuilder ID to a string and returns it
        return stringBuilder.toString();
    }

    /**
     * Writes the products information to a text file
     * @param data Arraylist Containing Product objects to be written in the text file
     * @param Products The name of the text file
     */
    public static void writeToFile(ArrayList<Product> data, String Products){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Products))){

            // Iterating through the product List
            for (Product items : data){
                // Get type of product
                if (items instanceof Electronics) {
                    writer.write("Electronic Item" + "\n");
                } else {
                    writer.write("Clothing Item" + "\n");
                }
                // Product Information
                writer.write("Product ID - " + items.getProductID() + "\n");
                writer.write("Product Name - " + items.getProductName()+ "\n");
                writer.write("Available Product Count - " + items.getAvailableItemCount()+ "\n");
                writer.write("Product Price - " + (int) items.getItemPrice()+ "\n");

                if (items instanceof Electronics){
                    writer.write("Product Brand - " + ((Electronics) items).getItemBrand()+ "\n");
                    writer.write("Product Warranty Period - " + ((Electronics) items).getWarrantyPeriod()+ "\n");

                } else if (items instanceof Clothing) {
                    writer.write("Product Colour - " + ((Clothing) items).getItemColour()+ "\n");
                    writer.write("Product Size - " + ((Clothing) items).getItemSize()+ "\n");
                }
                // Line to separate each new product
                writer.newLine();
            }
            System.out.println("Products were saved to the file successfully");
        } catch (IOException e) {
            System.out.println();
        }
    }

    /**
     * Reads products information from a text file and adds the info to the Product List
     * @param Products Name of the text file
     * @param productList The ArrayList where the data will be loaded to
     */
    public static void loadData(String Products, ArrayList<Product> productList){

        try (BufferedReader reader = new BufferedReader(new FileReader(Products))){
            // Clear the existing product list
            productList.clear();

            String line;
            while ((line = reader.readLine()) != null){
                // Get the type of Product
                boolean isElectronic = line.equals("Electronic Item");

                // Read Product Information
                String productId = reader.readLine().split(" - ")[1];
                String productName = reader.readLine().split(" - ")[1];
                int availableItemCount = Integer.parseInt(reader.readLine().split(" - ")[1]);
                int productPrice = Integer.parseInt(reader.readLine().split(" - ")[1]);

                Product product;
                if (isElectronic) {
                    String brand = reader.readLine().split(" - ")[1];
                    int warrantyPeriod = Integer.parseInt(reader.readLine().split(" - ")[1]);

                    product = new Electronics(productId, productName, availableItemCount, productPrice, brand, warrantyPeriod);
                } else {
                    String color = reader.readLine().split(" - ")[1];
                    String size = reader.readLine().split(" - ")[1];

                    product = new Clothing(productId, productName, availableItemCount, productPrice, color, size);
                }

                // Add loaded data to the Product List
                productList.add(product);

                // Read newline separating the products
                reader.readLine();
            }
        } catch (IOException e){
            System.out.println();
        }
    }

    /**
     * Erase data in the text file if there are already existing info in it
     * @param Products name of the text file to erase data
     */
    public static void eraseFileInfo(String Products){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Products))) {
            // Empty the text file
            writer.write("");
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Getter fot the productList ArrayList
    public static ArrayList<Product> getProductList() {
        return productList;
    }
}

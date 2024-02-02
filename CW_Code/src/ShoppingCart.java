import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart extends User{
    private ArrayList<Product> cartList;
    ArrayList<Product> userCart = new ArrayList<>();
    private final Scanner scanner;
    public int userCommand;
    public int Quantity;
    int electronicItem;
    int clothingItem;
    public ShoppingCart(String userName, String password) {
        super(userName, password);
        this.cartList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Sets the productList to cartList
    public void setUserProductList(ArrayList<Product> productList) {
        this.cartList = productList;
    }

    public void UserDetails() {
        String userName = getUserName();
        String password = getPassword();

        System.out.println();
        System.out.println("User Name: " + userName);
        System.out.println("Password: " + password);

        while (true) {
            // Menu options
            System.out.println();
            System.out.println("Products of Westminster Shopping Manager is shown above.");
            System.out.println("Select a option to: ");
            System.out.println("    1. View Product List");
            System.out.println("    2. Add a product to the cart");
            System.out.println("    3. Remove product from the cart");
            System.out.println("    4. Calculate total cost");
            System.out.println("    5. View User Cart");
            System.out.println("    6. Exit shopping cart");
            System.out.println();

            try {
                //Get user command with exception handling to repeat if user gives an invalid input
                System.out.print("Enter a command: ");
                userCommand = Integer.parseInt(scanner.nextLine());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numerical command stated in the above menu options");
                continue;
            }

            if (userCommand == 1){
                printProducts();

            }else if (userCommand == 2){
                addToCart();

            } else if (userCommand == 3) {
                removeFromCart();

            } else if (userCommand == 4) {
                calTotalCost();

            } else if (userCommand == 5) {
                printUserCart();

            } else if (userCommand == 6) {
                    System.out.println("Thank you for shopping at Westminster Shopping Cart");
                    return; // Exit the program
            } else {
                    System.out.println("Enter valid Command");
            }
        }
    }

    /**
     * Gets productID from user to add to cart
     * Iterates through the cartList and adds the product with addProductID to the userCart
     */
    public void addToCart() {
        try {
            System.out.print("Enter product ID to add to cart: ");
            String addProductID = scanner.nextLine();

            System.out.print("Enter the quantity of the product to add: ");
            Quantity = Integer.parseInt(scanner.nextLine());

            boolean productFound = false;

            // Iterating through the product List
            for (Product product : cartList) {
                if (product.getProductID().equals(addProductID)) {
                    if (Quantity <= product.getAvailableItemCount()) {
                        product.setAvailableItemCount(product.getAvailableItemCount() - Quantity);
                        userCart.add(product);
                        System.out.println("Product: " + addProductID + " was added to the cart.");
                    } else {
                        System.out.println("The quantity you entered is insufficient to be added");
                    }
                    productFound = true;
                    break; // Breaks the for loop if product is found
                }
            }
            if (!productFound) {
                System.out.println("ProductID: " + addProductID + " does not exist in the product list");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid ProductID");
        }
    }

    /**
     * Gets product ID from user to remove from cart
     * Iterates through the products and removes the product with removeProductID from the userCart
     */
    public void removeFromCart() {
        try {
            System.out.print("Enter product ID to remove from the cart: ");
            String removeProductID = scanner.nextLine();

            boolean productFound = false;

            // Iterating through the product List
            for (Product product : userCart) {
                if (product.getProductID().equals(removeProductID)) {
                    userCart.remove(product);
                    System.out.println("Product: " + removeProductID + " was removed from the cart.");
                }
                productFound = true;
                break; // Breaks the for loop if product is found
            }
            if (!productFound) {
                System.out.println("ProductID: " + removeProductID + " does not exist in the product list");
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid ProductID");
        }
    }

    /**
     * This method iterated through all pri=cuts in the cartList getting the item price
     * The item price of each product multiplied with the quantity is added to the total Cost
     */
    public void calTotalCost() {
        double totalCost = 0;
        for (Product product : userCart) {
            if (product instanceof Electronics){
                electronicItem = electronicItem + 1;
            } else {
                clothingItem = clothingItem + 1;
            }
            totalCost = totalCost + (Quantity * product.getItemPrice());
        }

        System.out.println("Total cost of products is: " + totalCost);
    }

    /**
     * Print the user Cart based on Electronic or Clothing item
     */
    public void printUserCart() {
        if (userCart.isEmpty()){
            System.out.println("There are no products in the Cart");
        } else {
            for (Product product : userCart) {
                if (product instanceof Electronics) {
                    System.out.println("Electronics Item");
                    System.out.println("================");
                    System.out.println("Product ID: " + product.getProductID());
                    System.out.println("Product Name: " + product.getProductName());
                    System.out.println("Available Product Count: " + Quantity);
                    System.out.println("Product Price: " + product.getItemPrice());
                    System.out.println("Product Brand: " + ((Electronics) product).getItemBrand());
                    System.out.println("Product warranty period: " + ((Electronics) product).getWarrantyPeriod());
                    System.out.println();
                } else {
                    System.out.println("Clothing Item");
                    System.out.println("=============");
                    System.out.println("Product ID: " + product.getProductID());
                    System.out.println("Product Name: " + product.getProductName());
                    System.out.println("Available Product Count: " + Quantity);
                    System.out.println("Product Price: " + product.getItemPrice());
                    System.out.println("Product Colour: " + ((Clothing) product).getItemColour());
                    System.out.println("Product Size: " + ((Clothing) product).getItemSize());
                    System.out.println();
                }
            }
        }
    }

    /**
     * Prints the product list based on Electronic or Clothing
     */
    public void printProducts(){
        for (Product item : cartList) {
            // Checking if the item is an electronic or a Clothing and printing it
            if (item instanceof Electronics) {
                System.out.println("Electronics Item");
                System.out.println("================");
                System.out.println("Product ID: " + item.getProductID());
                System.out.println("Product Name: " + item.getProductName());
                System.out.println("Available Product Count: " + item.getAvailableItemCount());
                System.out.println("Product Price: " + item.getItemPrice());
                System.out.println("Product Brand: " + ((Electronics) item).getItemBrand());
                System.out.println("Product warranty period: " + ((Electronics) item).getWarrantyPeriod());
                System.out.println();
            } else {
                System.out.println("Clothing Item");
                System.out.println("=============");
                System.out.println("Product ID: " + item.getProductID());
                System.out.println("Product Name: " + item.getProductName());
                System.out.println("Available Product Count: " + item.getAvailableItemCount());
                System.out.println("Product Price: " + item.getItemPrice());
                System.out.println("Product Colour: " + ((Clothing) item).getItemColour());
                System.out.println("Product Size: " + ((Clothing) item).getItemSize());
                System.out.println();
            }
        }
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void addElectronicProductToTheList() {
        WestminsterShoppingManager test1 = new WestminsterShoppingManager();

        WestminsterShoppingManager.command = 1;
        WestminsterShoppingManager.productType = 1;
        test1.addProducts();

        assertFalse(WestminsterShoppingManager.getProductList().isEmpty());
        assertEquals(1, WestminsterShoppingManager.getProductList().size());
    }

    @Test
    void addClothingProductToTheList() {
        WestminsterShoppingManager test2 = new WestminsterShoppingManager();

        WestminsterShoppingManager.command = 1;
        WestminsterShoppingManager.productType = 2;
        test2.addProducts();

        assertFalse(WestminsterShoppingManager.getProductList().isEmpty());
        assertEquals(1, WestminsterShoppingManager.getProductList().size());
    }

    @Test
    void addMoreThanFiftyProductsToTheList() {
        WestminsterShoppingManager test3 = new WestminsterShoppingManager();

        for (int i = 0; i < 50; i++) {
            test3.addProducts();
        }
        assertTrue(WestminsterShoppingManager.getProductList().size() <= 50);
    }

    @Test
    void removeProductFromTheList() {
        WestminsterShoppingManager test4 = new WestminsterShoppingManager();

        WestminsterShoppingManager.command = 1;
        test4.addProducts();
        String productID = WestminsterShoppingManager.getProductList().get(0).getProductID();

        WestminsterShoppingManager.command = 2;
        test4.removeProducts();
        assertTrue(WestminsterShoppingManager.getProductList().isEmpty());
    }

    @Test
    void printProductsWithEmptyList() {
        WestminsterShoppingManager test5 = new WestminsterShoppingManager();
        test5.printProducts();
    }

    @Test
    void printProductList() {
        WestminsterShoppingManager test6 = new WestminsterShoppingManager();

        WestminsterShoppingManager.command = 1;
        WestminsterShoppingManager.productType = 1;
        test6.addProducts();

        test6.printProducts();
    }

    @Test
    void writeToFile() {
        WestminsterShoppingManager test7 = new WestminsterShoppingManager();

        WestminsterShoppingManager.command = 1;
        test7.addProducts();

        // Temporary file path for testing
        String testFilePath = "test_products.txt";

        WestminsterShoppingManager.command = 4;
        WestminsterShoppingManager.writeToFile(WestminsterShoppingManager.getProductList(), testFilePath);

        WestminsterShoppingManager.loadData(testFilePath, WestminsterShoppingManager.getProductList());

        assertFalse(WestminsterShoppingManager.getProductList().isEmpty());
        assertEquals(1, WestminsterShoppingManager.getProductList().size());
        assertEquals("test_products.txt", WestminsterShoppingManager.getProductList().get(0).getProductID());

        // Test writing to a file to check if the data is overwritten
        test7.addProducts();
        WestminsterShoppingManager.writeToFile(WestminsterShoppingManager.getProductList(), testFilePath);
        WestminsterShoppingManager.loadData(testFilePath, WestminsterShoppingManager.getProductList());
        assertEquals(1, WestminsterShoppingManager.getProductList().size());
    }
}

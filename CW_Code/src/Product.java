public abstract class Product {
    //Initializing Variables
    String productID;
    String productName;
    int availableItemCount;
    double itemPrice;

    public Product(String productID,String productName,int availableItemCount, double itemPrice){
        this.productID = productID;
        this.productName = productName;
        this.availableItemCount = availableItemCount;
        this.itemPrice = itemPrice;
    }

    //Getters
    public String getProductID(){
        return productID;
    }
    public String getProductName(){
        return productName;
    }
    public int getAvailableItemCount(){
        return availableItemCount;
    }
    public double getItemPrice(){
        return itemPrice;
    }

    //Setters
    public void setProductID(String productID){
        this.productID = productID;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public void setAvailableItemCount(int availableItemCount){
        this.availableItemCount = availableItemCount;
    }
    public  void  setItemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }
}


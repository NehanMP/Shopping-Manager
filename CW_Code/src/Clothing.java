public class Clothing extends Product{
    String itemSize;
    String itemColour;

    public Clothing(String productID, String productName, int availableItemCount, double itemPrice, String itemSize, String itemColour) {
        super(productID, productName, availableItemCount, itemPrice);
        this.itemColour = itemColour;
        this.itemSize = itemSize;
    }

    public String getItemSize(){
        return itemSize;
    }
    public String getItemColour(){
        return itemColour;
    }

    public void setItemSize(String itemSize){
        this.itemSize = itemSize;
    }
    public void setItemColour(String itemColour){
        this.itemColour = itemColour;
    }
}

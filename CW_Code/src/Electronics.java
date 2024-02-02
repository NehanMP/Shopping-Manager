public class Electronics extends Product{
    String itemBrand;
    int warrantyPeriod;

    public Electronics(String productID, String productName, int availableItemCount, double itemPrice, String itemBrand, int warrantyPeriod){
        super(productID, productName, availableItemCount, itemPrice);
        this.itemBrand = itemBrand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getItemBrand(){
        return itemBrand;
    }
    public int getWarrantyPeriod(){
        return warrantyPeriod;
    }

    public void setItemBrand(String itemBrand){
        this.itemBrand = itemBrand;
    }
    public void setWarrantyPeriod(int warrantyPeriod){
        this.warrantyPeriod = warrantyPeriod;
    }
}

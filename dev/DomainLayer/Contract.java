package DomainLayer;

import java.util.*;

public class Contract {
    private boolean[] supplyDays; //|7|
    private int supplyMaxDays; // 0+ or -1
    private boolean deliveryService;

    //private Dictionary<Integer,SupProduct> products;
    private final List<SupProduct> catalog;
    private final QuantityAgreement qa;

    public Contract(boolean[] supplyDays, int supplyMaxDays, boolean deliveryService){
        setSupplyDays(supplyDays);
        setSupplyMaxDays(supplyMaxDays);
        setDeliveryService(deliveryService);
        this.catalog = new LinkedList<>();
        this.qa = new QuantityAgreement();
    }

    // getters
    public boolean[] getSupplyDays() {
        return supplyDays;
    }
    public int getSupplyMaxDays() {
        return supplyMaxDays;
    }
    public boolean hasDeliveryService() {
        return deliveryService;
    }
    public List<SupProduct> getCatalog() {
        return catalog;
    }
    public QuantityAgreement getQa() {
        return qa;
    }

    //setters
    public void setSupplyDays(boolean[] supplyDays) {
        if (supplyDays == null || supplyDays.length > 7 )
            throw new IllegalArgumentException("trying to set supply days with incorrect format");

        this.supplyDays = supplyDays;
    }

    public void addSupplyDay(int day) {
        if (day > 7 || day < 1)
            throw new IllegalArgumentException("day out of range");

        supplyDays[day-1] = true;
    }

    public void removeSupplyDay(int day) {
        if (day > 7 || day < 1)
            throw new IllegalArgumentException("day out of range");

        supplyDays[day-1] = false;
    }

    public void setSupplyMaxDays(int supplyMaxDays) {
        if(supplyMaxDays < -1)
            throw new IllegalArgumentException("trying to set max days for supply with negative number");

        this.supplyMaxDays = supplyMaxDays;
    }

    public void setDeliveryService(boolean deliveryService) {
        this.deliveryService = deliveryService;
    }

    //products methods
    public void addProduct(String catalogNum, String name, float price) {
        if(hasProduct(catalogNum))
            throw new IllegalArgumentException("trying to add product with an existing catalog number");

        catalog.add(new SupProduct(catalogNum, name, price));
    }

    public void removeProduct(String catalogNum) {
        catalog.removeIf(supProduct -> supProduct.getCatalogNum().equals(catalogNum));
        qa.removeProduct(catalogNum);
    }

    public void updateProductCatalogNum(String oldCatalogNum, String newCatalogNum) {
        if(!hasProduct(oldCatalogNum))
            throw new IllegalArgumentException("trying to update a product which doesn't exist");

        catalog.stream().filter(supProduct -> supProduct.getCatalogNum().equals(oldCatalogNum)).findFirst().get().setCatalogNum(newCatalogNum);
        qa.updateProductCatalogNum(oldCatalogNum, newCatalogNum);
    }

    public void updateProductName(String catalogNum, String name) {
        if(!hasProduct(catalogNum))
            throw new IllegalArgumentException("trying to update a product which doesn't exist");

        catalog.stream().filter(supProduct -> supProduct.getCatalogNum().equals(catalogNum)).findFirst().get().setName(name);
    }

    public void updateProductPrice(String catalogNum, float price) {
        if(!hasProduct(catalogNum))
            throw new IllegalArgumentException("trying to update a product which doesn't exist");

        catalog.stream().filter(supProduct -> supProduct.getCatalogNum().equals(catalogNum)).findFirst().get().setPrice(price);
    }

    // Quantity Agreement methods
    public void addDiscountPerItem(String productID, int quantity, float discount){
        qa.addDiscountPerItem(productID, quantity, discount);
    }

    public void addDiscountPerOrder(String productID, int quantity, float discount){
        qa.addDiscountPerOrder(productID, quantity, discount);
    }

    public void updateDiscountPerItem(String productID, int quantity, float discount){
        qa.updateDiscountPerItem(productID, quantity, discount);
    }

    public void updateDiscountPerOrder(String productID, int quantity, float discount){
        qa.updateDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String productID, int quantity){
        qa.removeDiscountPerItem(productID, quantity);
    }

    public void removeDiscountPerOrder(String productID, int quantity) {
        qa.removeDiscountPerOrder(productID, quantity);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String productID){
        return qa.getDiscountsForProductPerItem(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String productID){
        return qa.getDiscountsForProductPerOrder(productID);
    }


    private boolean hasProduct(String catalogNum){
        return catalog.stream().anyMatch(supProduct -> supProduct.getCatalogNum().equals(catalogNum));
    }

}

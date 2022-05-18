package SuppliersModule.DomainLayer;

import java.util.*;

public class Contract {
    private boolean[] supplyDays; //|7|
    private int maxSupplyDays; // 0+ or -1
    private int supplyCycle; //will be used to determine periodic order when no supply days available
    private boolean deliveryService;
    private final List<CatalogProduct> catalog = new LinkedList<>();;
    private QuantityAgreement qa;

    // getters
    public boolean[] getSupplyDays() {
        return supplyDays;
    }
    public int getMaxSupplyDays() {
        return maxSupplyDays;
    }
    public int getSupplyCycle(){return supplyCycle;}
    public boolean hasDeliveryService() {
        return deliveryService;
    }

    //TODO fixing visibility of catalog and qa only to create DTO for presentation
    public List<CatalogProduct> getCatalog() {
        return catalog;
    }
    public QuantityAgreement getQa() {
        return qa;
    }

    //setters
    public void setSupplyDays(boolean[] supplyDays) {
        if (supplyDays == null || supplyDays.length != 7 )
            throw new IllegalArgumentException("trying to set supply days with incorrect format");
        this.supplyDays = supplyDays;
    }
    public void setMaxSupplyDays(int maxSupplyDays) {
        if(maxSupplyDays < -1)
            throw new IllegalArgumentException("trying to set max days for supply with negative number");

        this.maxSupplyDays = maxSupplyDays;
    }
    public void setSupplyCycle(int supplyCycle){this.supplyCycle = supplyCycle;}
    public void setDeliveryService(boolean deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void addQuantityAgreement(QuantityAgreement qa){
        if(getQa()==null)
            throw new RuntimeException("Can not add Quantity agreement because the supplier already has one.");
        this.qa = qa;
    }

    //  constructor
    public Contract(boolean[] supplyDays, int maxSupplyDays, int supplyCycle, boolean deliveryService,
                    String pId, String catalogNum, float price){
        setSupplyDays(supplyDays);
        setMaxSupplyDays(maxSupplyDays);
        setSupplyCycle(supplyCycle);
        setDeliveryService(deliveryService);
        catalog.add(new CatalogProduct(pId, catalogNum, price));
    }

    public void addSupplyDay(int day) {
        if (day > 7 || day < 1)
            throw new IllegalArgumentException("day out of week range.");
        supplyDays[day-1] = true;
    }
    public void removeSupplyDay(int day) {
        if (day > 7 || day < 1)
            throw new IllegalArgumentException("day out of week range");
        supplyDays[day-1] = false;
    }

    //products methods
    public void addProduct(String pId, String catalogNum, float price) {
        if(hasProduct(pId))
            throw new IllegalArgumentException("Product already exists.");
        if(hasCatalogNum(catalogNum))
            throw new IllegalArgumentException("trying to add product with a used catalog number.");
        catalog.add(new CatalogProduct(pId, catalogNum, price));
    }
    public boolean removeProduct(String pId) {  //TODO change functionality to delete supplier when when reached 0 catalog product
        catalog.removeIf(catalogProduct -> catalogProduct.getId().equals(pId));
        qa.removeProduct(pId);
        return catalog.isEmpty();
    }
    public void updateCatalogNum(String pId, String newCatalogNum) {
        if(!hasProduct(pId))
            throw new IllegalArgumentException("Product doesn't exists.");
        if(!hasCatalogNum(newCatalogNum))
            throw new IllegalArgumentException("trying to update product's catalog number with a used onr.");
        catalog.stream().filter(catalogProduct -> catalogProduct.getId().equals(pId)).findFirst().get().setCatalogNum(newCatalogNum);
    }
    public void updateProductPrice(String pId, float price) {
        if(!hasProduct(pId))
            throw new IllegalArgumentException("Product doesn't exists.");
        catalog.stream().filter(catalogProduct -> catalogProduct.getId().equals(pId)).findFirst().get().setPrice(price);
    }
    public boolean hasProduct(String pId){
        return catalog.stream().anyMatch(catalogProduct -> catalogProduct.getId().equals(pId));
    }
    private boolean hasCatalogNum(String catalogNum){
        return catalog.stream().anyMatch(supProduct -> supProduct.getCatalogNum().equals(catalogNum));
    }

    // Quantity Agreement methods
    public void updateDiscount(String pId, int quantity, float discount){
        if(!hasProduct(pId))
            throw new IllegalArgumentException("product not exist.");
        if(getQa()==null)
            addQuantityAgreement(new QuantityAgreement()); //TODO make contract the only creator of qa according to grasp
        qa.updateDiscount(pId, quantity, discount);
    }
    public Dictionary<Integer,Float> getDiscounts(String pId){
        if(!hasProduct(pId) || getQa() == null)
            return null;
        return qa.getDiscounts(pId);
    }
    public Dictionary<String, Dictionary<Integer, Float>> getDiscounts() {
        if(getQa() == null)
            return null;
        return qa.getDiscounts();
    }

    public List<CatalogProduct> searchProduct(String pId){
        List<CatalogProduct> p = new LinkedList<>();
        for(CatalogProduct cp :catalog){
            if(cp.getId().equals(pId))
                p.add(cp);
        }
        return p;
    }

    public String toString(){
        String suppDays = "";
        for (int i=0; i<getSupplyDays().length; i++) {
            if (getSupplyDays()[i])
                suppDays += suppDays + " " +  i;
        }
        return "delivery service: "  + hasDeliveryService() + "supply days:" + suppDays +
                " | max days for delivery " + getMaxSupplyDays() + " | number of item in catalog: " + getCatalog().size();
    }
}


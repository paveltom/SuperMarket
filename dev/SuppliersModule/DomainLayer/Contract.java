package SuppliersModule.DomainLayer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Contract {
    private SupplyTime supplyTime;
    private boolean deliveryService;
    private final List<CatalogProduct> catalog = new LinkedList<>();;
    private QuantityAgreement qa;

    // getters
    public boolean[] getDaysOfDelivery() {
        return supplyTime.getDaysOfDelivery();
    }
    public int getMaxDeliveryDuration() {
        return supplyTime.getMaxDeliveryDuration();
    }
    public int getOrderCycle(){return supplyTime.getOrderCycle();}
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
    public List<String> getOrderProduct(){
        return catalog.stream().filter(CatalogProduct::isInPeriodicOrder).map(CatalogProduct::getId).collect(Collectors.toList());
    }

    //setters
    public void setDeliveryService(boolean deliveryService) {
        this.deliveryService = deliveryService;
    }

    //  constructor
    public Contract(boolean[] daysOfDelivery, int maxDeliveryDuration, int orderCycle, boolean deliveryService,
                    String pId, String catalogNum, float price){
        supplyTime = new SupplyTime(daysOfDelivery, maxDeliveryDuration, orderCycle);
        setDeliveryService(deliveryService);
        catalog.add(new CatalogProduct(pId, catalogNum, price));
    }

    //return true if tomorrow there is a supply coming / time to order from the supplier
    public List<String> endDay(){
        supplyTime.endDay();
        return null; //TODO
    }

    public int getDaysToOrder(){ //returns the difference between the closest available order arrival and the next period order 
        return supplyTime.getDaysToOrder();
    }
    public void changeDaysOfDelivery(int day, boolean state) {
        supplyTime.changeDaysOfDelivery(day, state);
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
    /*public boolean hasInPeriodicOrder(String pId){
        return catalog.stream().anyMatch(catalogProduct -> catalogProduct.getId().equals(pId) & catalogProduct.isInPeriodicOrder());
    }*/
    private boolean hasCatalogNum(String catalogNum){
        return catalog.stream().anyMatch(supProduct -> supProduct.getCatalogNum().equals(catalogNum));
    }

    // Quantity Agreement methods
    public void addQuantityAgreement(QuantityAgreement qa){
        if(getQa()==null)
            throw new RuntimeException("Can not add Quantity agreement because the supplier already has one.");
        this.qa = qa;
    }
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
        for (int i = 0; i< getDaysOfDelivery().length; i++) {
            if (getDaysOfDelivery()[i])
                suppDays += suppDays + " " +  i;
        }
        return "delivery service: "  + hasDeliveryService() + "supply days:" + suppDays +
                " | max days for delivery " + getMaxDeliveryDuration() + " | number of item in catalog: " + getCatalog().size();
    }


    public void setDeliveryDuration(int maxSupplyDays) {
    }

    public void setSupplyCycle(int supplyCycle) {
    }


}


package SuppliersModule.DomainLayer;

import java.util.*;
import java.util.stream.Collectors;

public class Contract {
    private final SupplyTime supplyTime;
    private boolean deliveryService;
    private final List<CatalogProduct> catalog = new LinkedList<>();
    private QuantityAgreement qa;
    private final String sId;

    DAL.DataBaseConnection conn;
    // getters
    public String getsId(){return sId;}
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
    public void changeDaysOfDelivery(int day, boolean state) {supplyTime.changeDaysOfDelivery(day, state);}
    public void setDeliveryService(boolean deliveryService) {this.deliveryService = deliveryService;}
    public void setMaxDeliveryDuration(int maxDeliveryDuration) { supplyTime.setMaxDeliveryDuration(maxDeliveryDuration);}
    public void setOrderCycle(int orderCycle) { supplyTime.setOrderCycle(orderCycle);}

    //  constructor
    public Contract( String sId, boolean[] daysOfDelivery, int maxDeliveryDuration, int orderCycle, boolean deliveryService,
                    String pId, String catalogNum, float price){
        this.sId = sId;
        supplyTime = new SupplyTime(sId, daysOfDelivery, maxDeliveryDuration, orderCycle);
        setDeliveryService(deliveryService);
        catalog.add(new CatalogProduct(pId, catalogNum, price));
        this.conn = conn;
    }

    //  order methods

    //return list of products to order
    public List<String> endDay(){
        if(supplyTime.endDay())
            return getOrderProduct();
        return null;
    }
    public int getPeriodicOrderInterval(){
        return supplyTime.getPeriodicOrderInterval();
    }
    public int getDaysForShortageOrder(){
        return supplyTime.getDaysForShortageOrder();
    }

    //  products methods
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
    public Map<Integer, Float> getDiscount(String pId){
        if(!hasProduct(pId) || getQa() == null)
            return null;
        return qa.getDiscounts(pId);
    }
    public float getDiscount(String pId, int amount) {
        return qa.getDiscount(pId, amount);
    }
    public Map<String, Map<Integer, Float>> getDiscount() {
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
    public float getCatalogPrice(String pId) {
        return catalog.stream().filter(catalogProduct -> catalogProduct.getId().equals(pId)).findFirst().get().getPrice();
    }

    public float getFinalPrice(String pId, int amount) {
        float price = getCatalogPrice(pId);
        return price - price* getDiscount(pId, amount)/100;
    }

    public String toString(){
        StringBuilder suppDays = new StringBuilder();
        for (int i = 0; i< getDaysOfDelivery().length; i++) {
            if (getDaysOfDelivery()[i])
                suppDays.append(suppDays).append(" ").append(i);
        }
        return "delivery service: "  + hasDeliveryService() + "supply days:" + suppDays +
                " | max days for delivery " + getMaxDeliveryDuration() + " | number of item in catalog: " + getCatalog().size();
    }


}


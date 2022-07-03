package SuppliersModule.DomainLayer;

import DAL.Stock_Suppliers.DAOS.SupplierObjects.QuantityAgreementDao;

import java.util.*;
import java.util.stream.Collectors;

public class Contract {
    private final SupplyTime supplyTime;
    private final List<CatalogProduct> catalog = new LinkedList<>();
    private QuantityAgreement qa;
    private final String sId;
    // getters
    public String getsId(){return sId;}
    public boolean[] getDaysOfDelivery() {
        return supplyTime.getOrderingDays();
    }
    public int getOrderCycle(){return supplyTime.getOrderCycle();}
    public List<CatalogProduct> getCatalog() {
        return catalog;
    }
    public QuantityAgreement getQa() {
        return qa;
    }
    public List<String> getOrderProducts(){
        return catalog.stream().filter(CatalogProduct::isInPeriodicOrder).map(CatalogProduct::getId).collect(Collectors.toList());
    }

    //setters
    public void changeWeeklyOrdering(boolean[] days) {supplyTime.changeWeeklyOrdering(days);}
    public void setOrderCycle(int orderCycle) { supplyTime.setOrderCycle(orderCycle);}

    //  constructor
    public Contract( String sId, boolean[] orderingDays, int orderCycle,
                    String pId, String catalogNum, float price){
        this.sId = sId;
        supplyTime = new SupplyTime(sId, orderingDays, orderCycle);
        catalog.add(new CatalogProduct(sId, pId, catalogNum, price));
    }

    //from db
    public Contract(String sId, SupplyTime st, List<CatalogProduct> c, QuantityAgreement qa){
        this.sId = sId;
        supplyTime = st;
        if(c != null) {
            for (CatalogProduct p : c)
                catalog.add(p);
        }

        this.qa = qa;
    }


    //  order methods

    //return list of products to order
    public List<String> endDay(){
        if(supplyTime.endDay())
            return getOrderProducts();
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
        catalog.add(new CatalogProduct(sId, pId, catalogNum, price));
    }
    public boolean removeProduct(String pId) {
        catalog.removeIf(catalogProduct -> catalogProduct.getId().equals(pId));
        qa.removeProduct(pId);
        return catalog.isEmpty();
    }
    public void updateCatalogNum(String pId, String newCatalogNum) {
        if(!hasProduct(pId))
            throw new IllegalArgumentException("Product doesn't exists.");
        if(hasCatalogNum(newCatalogNum))
            throw new IllegalArgumentException("trying to update product's catalog number with a used one.");
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
        if(getQa()!=null)
            throw new RuntimeException("Can not add Quantity agreement because the supplier already has one.");
        this.qa = qa;
    }
    public void updateDiscount(String pId, int quantity, float discount){
        if(!hasProduct(pId))
            throw new IllegalArgumentException("product not exist.");
        if(getQa()==null)
            addQuantityAgreement(new QuantityAgreement(sId));
        qa.updateDiscount(pId, quantity, discount);

    }
    public Map<Integer, Float> getDiscount(String pId){
        if(!hasProduct(pId) || getQa() == null)
            return null;
        return qa.getDiscounts(pId);
    }
    public float getDiscount(String pId, int amount) {
        if(getQa() == null)
            return 0;
        return qa.getDiscount(pId, amount);
    }
    public Map<String, Map<Integer, Float>> getDiscount() {
        if(getQa() == null)
            return null;
        return qa.getDiscounts();
    }

    public CatalogProduct searchProduct(String pId){
        return catalog.stream().filter(catalogProduct -> catalogProduct.getId().equals(pId)).findFirst().orElse(null);
    }
    public float getCatalogPrice(String pId) {
        return catalog.stream().filter(catalogProduct -> catalogProduct.getId().equals(pId)).findFirst().get().getPrice();
    }

    public float getFinalPrice(String pId, int amount) {
        float price = getCatalogPrice(pId);
        return price - price* getDiscount(pId, amount)/100;
    }


    @Override
    public String toString() {
        return  "supplyTime=" + supplyTime +
                "\n catalog=" + catalog +
                "\nqa=" + qa;
    }

    public void updatePeriodicOrderProduct(String pId, boolean state) {
        CatalogProduct cp = searchProduct(pId);
        if(cp!=null)
            cp.setInPeriodicOrder(state);
    }

    public void delete(){
        for(CatalogProduct cp : catalog){
            removeProduct(cp.getId());
        }
        supplyTime.delete();
        qa.delete();
    }
}


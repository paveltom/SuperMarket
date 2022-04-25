package DomainLayer;

import Service.Response;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

public class SupplierController {
    List<Supplier> suppliers;
    private int supIds;

    public SupplierController(){
        suppliers = new LinkedList<>();
        supIds = 0;
    }

    public void addSupplier(String bankAccount, boolean cash, boolean credit, String contactName, String contactNum){
        suppliers.add(new Supplier(supIds + "", bankAccount, cash, credit, contactName, contactNum));
    }

    public void removeSupplier(String sid){
        suppliers.remove(findSupplier(sid));
    }

    private boolean supExsist(String sid){
        for(Supplier s:suppliers){
            if(s.getSid().equals(sid))
                return true;
        }
        return false;
    }

    private Supplier findSupplier(String sid){
        for(Supplier s:suppliers){
            if(s.getSid().equals(sid))
                return s;
        }
        throw new IllegalArgumentException("Supplier Doesnt Exists");
    }

    public void addContact(String sid, String contactName, String phoneNum){
        findSupplier(sid).addContact(contactName, phoneNum);
    }

    public void addContract(String sid, boolean[] supplyDays, int supplyMaxDays, boolean deliveryService){
        findSupplier(sid).addContract(supplyDays, supplyMaxDays, deliveryService);
    }

    public boolean[] getSupplyDays(String sid) {
        return findSupplier(sid).getSupplyDays();
    }
    public int getSupplyMaxDays(String sid) {
        return findSupplier(sid).getSupplyMaxDays();
    }
    public boolean hasDeliveryService(String sid) {
        return findSupplier(sid).hasDeliveryService();
    }
    public List<SupProduct> getCatalog(String sid) {
        return findSupplier(sid).getCatalog();
    }
    public QuantityAgreement getQa(String sid) {
        return findSupplier(sid).getQa();
    }


    public void setSupplyDays(String sid, boolean[] supplyDays) {
        findSupplier(sid).setSupplyDays(supplyDays);
    }

    public void setSupplyMaxDays(String sid, int supplyMaxDays) {
        findSupplier(sid).setSupplyMaxDays(supplyMaxDays);
    }

    public void setDeliveryService(String sid, boolean deliveryService) {
        findSupplier(sid).setDeliveryService(deliveryService);
    }

    //products methods
    public void addProduct(String sid, String catalogNum, String name, float price) {
        findSupplier(sid).addProduct(catalogNum, name, price);
    }

    public void removeProduct(String sid, String catalogNum) {
        findSupplier(sid).removeProduct(catalogNum);
    }

    public void updateProductCatalogNum(String sid, String oldCatalogNum, String newCatalogNum) {
        findSupplier(sid).updateProductCatalogNum(oldCatalogNum, newCatalogNum);
    }

    public void updateProductName(String sid, String catalogNum, String name) {
        findSupplier(sid).updateProductName(catalogNum, name);
    }

    public void updateProductPrice(String sid, String catalogNum, float price) {
        findSupplier(sid).updateProductPrice(catalogNum, price);
    }

    // Quantity Agreement methods
    public void addDiscountPerItem(String sid, String productID, int quantity, float discount){
        findSupplier(sid).addDiscountPerItem(productID, quantity, discount);
    }

    public void addDiscountPerOrder(String sid, String productID, int quantity, float discount){
        findSupplier(sid).addDiscountPerOrder(productID, quantity, discount);
    }

    public void updateDiscountPerItem(String sid, String productID, int quantity, float discount){
        findSupplier(sid).updateDiscountPerItem(productID, quantity, discount);
    }

    public void updateDiscountPerOrder(String sid, String productID, int quantity, float discount){
        findSupplier(sid).updateDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String sid, String productID, int quantity){
        findSupplier(sid).removeDiscountPerItem(productID, quantity);
    }

    public void removeDiscountPerOrder(String sid, String productID, int quantity) {
        findSupplier(sid).removeDiscountPerOrder(productID, quantity);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String sid, String productID){
        return findSupplier(sid).getDiscountsForProductPerItem(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String sid, String productID){
        return findSupplier(sid).getDiscountsForProductPerOrder(productID);
    }


}

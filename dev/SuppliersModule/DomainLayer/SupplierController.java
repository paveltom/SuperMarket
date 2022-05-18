package SuppliersModule.DomainLayer;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupplierController {
    private final List<Supplier> suppliers = new LinkedList<>();

    // getters
    //TODO fixing visibility of catalog and qa only to create DTO for presentation
    public List<Supplier> getSuppliers() {
        return suppliers;
    }
    public Contract getContract(String sId){
        checkSupplier(sId);
        return getSupplier(sId).getContract();
    }
    public List<CatalogProduct> getCatalog(String sId){
        checkSupplier(sId);
        return getSupplier(sId).getCatalog();
    }
    public QuantityAgreement getQa(String sId) {
        checkSupplier(sId);
        return getSupplier(sId).getQuantityAgreement();
    }
    public boolean[] getSupplyDays(String sId) {
        checkSupplier(sId);
        return getSupplier(sId).getSupplyDays();
    }
    public int getSupplyMaxDays(String sId) {
        checkSupplier(sId);
        return getSupplier(sId).getMaxSupplyDays();
    }
    public int getSupplyCycle(String sId){
        checkSupplier(sId);
        return getSupplier(sId).getSupplyCycle();}
    public boolean hasDeliveryService(String sId) {
        checkSupplier(sId);
        return getSupplier(sId).hasDeliveryService();
    }

    //  setters
    public void setSupplyDays(String sId, boolean[] supplyDays) {
        checkSupplier(sId);
        getSupplier(sId).setSupplyDays(supplyDays);
    }
    public void setMaxSupplyDays(String sId, int maxSupplyDays) {
        checkSupplier(sId);
        getSupplier(sId).setMaxSupplyDays(maxSupplyDays);
    }
    public void setSupplyCycle(String sId, int supplyCycle){
        checkSupplier(sId);
        getSupplier(sId).setSupplyCycle(supplyCycle);}
    public void setDeliveryService(String sId, boolean deliveryService) {
        checkSupplier(sId);
        getSupplier(sId).setDeliveryService(deliveryService);
    }

    //  other methods
    public void addSupplier(String sId, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum,
                            boolean[] supplyDays, int maxSupplyDays, int supplCycle, boolean deliveryService,
                            String pId, String catNumber, float price){
        if(hasSupp(sId))
            throw new IllegalArgumentException("supplier with id " + sId + " already exist!");
        suppliers.add(new Supplier(sId, bankAccount, cash, credit, contactName, phoneNum,  //TODO check if pId exist
                                    supplyDays, maxSupplyDays, supplCycle, deliveryService,
                                    pId, catNumber, price));
    }
    public void removeSupplier(String sId){
        suppliers.remove(getSupplier(sId));
    }
    public void addContact(String sId, String contactName, String phoneNum){
        checkSupplier(sId);
        getSupplier(sId).addContact(contactName, phoneNum);
    }
    public void removeContact(String sId, String name){
        checkSupplier(sId);
        getSupplier(sId).removeContact(name);
    }

    //products methods
    public void addProduct(String sId, String pId, String catalogNum, float price) {
        checkSupplier(sId);
        getSupplier(sId).addProduct(pId, catalogNum, price);
    }
    public void removeProduct(String sId, String pId) {
        checkSupplier(sId);
        getSupplier(sId).removeProduct(pId);
    }
    public void updateCatalogNum(String sId, String pId, String newCatalogNum) {
        checkSupplier(sId);
        getSupplier(sId).updateCatalogNum(pId, newCatalogNum);
    }
    public void updateProductPrice(String sId, String pId, float price) {
        checkSupplier(sId);
        getSupplier(sId).updateProductPrice(pId, price);
    }
    
    // Quantity Agreement methods
    public void updateDiscount(String sId, String pId, int quantity, float discount){
        checkSupplier(sId);
        getSupplier(sId).updateDiscount(pId, quantity, discount);
    }
    public Dictionary<Integer,Float> getDiscounts(String sId, String pId){
        checkSupplier(sId);
        return getSupplier(sId).getDiscounts(pId);
    }
    public Map<String,String> getContacts(String sId){
        checkSupplier(sId);
        return getSupplier(sId).getContacts();
    }
    public Dictionary<String, Dictionary<Integer, Float>> getDiscounts(String sId) {
        checkSupplier(sId);
        return getSupplier(sId).getDiscounts();
    }
    public List<CatalogProduct> searchProduct(String name){
        List<CatalogProduct> products = new LinkedList<>();
        for(Supplier s:suppliers){
            products.addAll(s.searchProduct(name));
        }
        return products;
    }

    private boolean hasSupp(String sId){
        for(Supplier s:suppliers){
            if(s.getsId().equals(sId))
                return true;
        }
        return false;
    }
    private Supplier getSupplier(String sId){
        List<Supplier> matchedSupp = suppliers.stream().filter(supplier -> supplier.getsId().equals(sId)).collect(Collectors.toList());
        if(matchedSupp.isEmpty())
            throw new IllegalArgumentException("there is no supplier with that id");
        return matchedSupp.get(0);
    }
    private void checkSupplier(String sId){
        if(!hasSupp(sId))
            throw new IllegalArgumentException("Supplier doesn't exists.");
    }
}

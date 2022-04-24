package DomainLayer;

import java.util.*;

public class Supplier {
    private String sid;
    private String bankAccount;
    private boolean[] paymentMethods;
    private Map<String,String> contacts;
    private Contract contract;

    public Supplier(String sid, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum){
        this.sid = sid;
        this.bankAccount = bankAccount;
        this.paymentMethods = new boolean[2];
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        this.contacts = new HashMap<>();
        addContact(contactName, phoneNum);
    }

    public void addContact(String contactName, String phoneNum){
        contacts.put(contactName, phoneNum);
    }

    public void addContract(boolean[] supplyDays, int supplyMaxDays, boolean deliveryService){
        contract = new Contract(supplyDays, supplyMaxDays, deliveryService);
    }

    public boolean[] getSupplyDays() {
        return contract.getSupplyDays();
    }
    public int getSupplyMaxDays() {
        return contract.getSupplyMaxDays();
    }
    public boolean hasDeliveryService() {
        return contract.hasDeliveryService();
    }
    public List<SupProduct> getCatalog() {
        return contract.getCatalog();
    }
    public QuantityAgreement getQa() {
        return contract.getQa();
    }

    public void setSupplyDays(boolean[] supplyDays) {
        contract.setSupplyDays(supplyDays);
    }

    public void setSupplyMaxDays(int supplyMaxDays) {
        contract.setSupplyMaxDays(supplyMaxDays);
    }

    public void setDeliveryService(boolean deliveryService) {
        contract.setDeliveryService(deliveryService);
    }

    //products methods
    public void addProduct(String catalogNum, String name, float price) {
        contract.addProduct(catalogNum, name, price);
    }

    public void removeProduct(String catalogNum) {
        contract.removeProduct(catalogNum);
    }

    public void updateProductCatalogNum(String oldCatalogNum, String newCatalogNum) {
        contract.updateProductCatalogNum(oldCatalogNum, newCatalogNum);
    }

    public void updateProductName(String catalogNum, String name) {
        contract.updateProductName(catalogNum, name);
    }

    public void updateProductPrice(String catalogNum, float price) {
        contract.updateProductPrice(catalogNum, price);
    }

    // Quantity Agreement methods
    public void addDiscountPerItem(String productID, int quantity, float discount){
        contract.addDiscountPerItem(productID, quantity, discount);
    }

    public void addDiscountPerOrder(String productID, int quantity, float discount){
        contract.addDiscountPerOrder(productID, quantity, discount);
    }

    public void updateDiscountPerItem(String productID, int quantity, float discount){
        contract.updateDiscountPerItem(productID, quantity, discount);
    }

    public void updateDiscountPerOrder(String productID, int quantity, float discount){
        contract.updateDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String productID, int quantity, float discount){
        contract.addDiscountPerItem(productID, quantity, discount);
    }

    public void removeDiscountPerOrder(String productID, int quantity, float discount) {
        contract.removeDiscountPerOrder(productID, quantity, discount);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String productID){
        return contract.getDiscountsForProductPerItem(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String productID){
        return contract.getDiscountsForProductPerOrder(productID);
    }
}

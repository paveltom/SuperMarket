package SuppliersModule.DomainLayer;

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
    private void hasContract(){
        if(contract == null){
            throw new IllegalArgumentException("no contract with supplier");
        }
    }

    public String getSid(){
        return sid;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public boolean hasCashPayment() {
        return paymentMethods[0];
    }

    public boolean hasCreditPayment() {
        return paymentMethods[1];
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public Contract getContract() {
        hasContract();
        return contract;
    }

    public void addContact(String contactName, String phoneNum){
        contacts.put(contactName, phoneNum);
    }

    public void addContract(boolean[] supplyDays, int supplyMaxDays, boolean deliveryService){
        contract = new Contract(supplyDays, supplyMaxDays, deliveryService);
    }

    public boolean[] getSupplyDays() {
        hasContract();
        return contract.getSupplyDays();
    }
    public int getSupplyMaxDays() {
        hasContract();
        return contract.getSupplyMaxDays();
    }
    public boolean hasDeliveryService() {
        hasContract();
        return contract.hasDeliveryService();
    }
    public List<CatalogProduct> getCatalog() {
        hasContract();
        return contract.getCatalog();
    }
    public QuantityAgreement getQa() {
        hasContract();
        return contract.getQa();
    }

    public void setSupplyDays(boolean[] supplyDays) {
        hasContract();
        contract.setSupplyDays(supplyDays);
    }

    public void setSupplyMaxDays(int supplyMaxDays) {
        hasContract();
        contract.setSupplyMaxDays(supplyMaxDays);
    }

    public void setDeliveryService(boolean deliveryService) {
        hasContract();
        contract.setDeliveryService(deliveryService);
    }

    //products methods
    public void addProduct(String catalogNum, String name, float price) {
        hasContract();
        contract.addProduct(catalogNum, name, price);
    }

    public void removeProduct(String catalogNum) {
        hasContract();
        contract.removeProduct(catalogNum);
    }

    public void updateProductCatalogNum(String oldCatalogNum, String newCatalogNum) {
        hasContract();
        contract.updateProductCatalogNum(oldCatalogNum, newCatalogNum);
    }

    public void updateProductName(String catalogNum, String name) {
        hasContract();
        contract.updateProductName(catalogNum, name);
    }

    public void updateProductPrice(String catalogNum, float price) {
        hasContract();
        contract.updateProductPrice(catalogNum, price);
    }

    // Quantity Agreement methods
    public void addDiscountPerItem(String productID, int quantity, float discount){
        hasContract();
        contract.addDiscountPerItem(productID, quantity, discount);
    }

    public void addDiscountPerOrder(String productID, int quantity, float discount){
        hasContract();
        contract.addDiscountPerOrder(productID, quantity, discount);
    }

    public void updateDiscountPerItem(String productID, int quantity, float discount){
        hasContract();
        contract.updateDiscountPerItem(productID, quantity, discount);
    }

    public void updateDiscountPerOrder(String productID, int quantity, float discount){
        hasContract();
        contract.updateDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String productID, int quantity){
        hasContract();
        contract.removeDiscountPerItem(productID, quantity);
    }

    public void removeDiscountPerOrder(String productID, int quantity) {
        hasContract();
        contract.removeDiscountPerOrder(productID, quantity);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String productID){
        hasContract();
        return contract.getDiscountsForProductPerItem(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String productID){
        hasContract();
        return contract.getDiscountsForProductPerOrder(productID);
    }

    public List<CatalogProduct> searchProduct(String name){
        if(contract == null)
            return new LinkedList<>();
        return contract.searchProduct(name);
    }

    public String toString(){
        String cash, credit;
        if (hasCashPayment())
            cash = "y";
        else cash = "n";
        if (hasCreditPayment())
            credit = "y";
        else credit = "n";

        return "supplier id: " + getSid() + " | Bank Account: " + getBankAccount() +
               " | payments: cash-" + cash + " credit-" + credit + " | contacts:\n" + getContacts().toString();
    }

    public void removeContact(String name){
        contacts.remove(name);
    }

    public Dictionary<String, Dictionary<Integer, Float>> getPerItem() {
        return contract.getPerItem();
    }

    public Dictionary<String, Dictionary<Integer, Float>> getPerOrder() {
        return contract.getPerOrder();
    }
}

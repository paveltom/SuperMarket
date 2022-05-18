package SuppliersModule.DomainLayer;

import java.util.*;

public class Supplier {
    private String sId;
    private String bankAccount;
    private final boolean[] paymentMethods = new boolean[2];
    private final Map<String,String> contacts = new HashMap<>();
    private final Contract contract;

    //  getters
    public String getsId(){
        return sId;
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
    public boolean[] getSupplyDays() {
        return contract.getSupplyDays();
    }
    public int getMaxSupplyDays() {
        return contract.getMaxSupplyDays();
    }
    public int getSupplyCycle(){return contract.getSupplyCycle();}
    public boolean hasDeliveryService() {
        return contract.hasDeliveryService();
    }
    //TODO 1. fixing visibility of catalog and qa only to create DTO for presentation 2. encapsulating getters with copies
    public Contract getContract(){
        return contract;
    }
    public List<CatalogProduct> getCatalog() {
        return contract.getCatalog();
    }
    public QuantityAgreement getQuantityAgreement() {
        return contract.getQa();
    }
    public Map<String, String> getContacts() {
        return contacts;
    }

    // setter
    public void setSupplyDays(boolean[] supplyDays) {
        contract.setSupplyDays(supplyDays);
    }
    public void setMaxSupplyDays(int maxSupplyDays) {
        contract.setMaxSupplyDays(maxSupplyDays);
    }
    public void setSupplyCycle(int supplyCycle){contract.setSupplyCycle(supplyCycle);}
    public void setDeliveryService(boolean deliveryService) {
        contract.setDeliveryService(deliveryService);
    }


    public Supplier(String sId, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum,
                    boolean[] supplyDays, int MaxSupplyDays, int supplCycle, boolean deliveryService,
                    String pId, String catNumber, float price){
        this.sId = sId;
        this.bankAccount = bankAccount;
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        addContact(contactName, phoneNum);
        contract = new Contract(supplyDays, MaxSupplyDays, supplCycle, deliveryService, pId, catNumber, price);
    }

    public void addContact(String contactName, String phoneNum){
        contacts.put(contactName, phoneNum);
    }
    public void removeContact(String name){
        contacts.remove(name);
    }

    //products methods
    public void addProduct(String pId, String catalogNum, float price) { //TODO 1.calculating and setting product in periodic order 2. checking that pid exist
        contract.addProduct(pId, catalogNum, price);
    }
    public boolean removeProduct(String pId) {
        return contract.removeProduct(pId);
    }
    public void updateCatalogNum(String pId, String newCatalogNum) {
        contract.updateCatalogNum(pId, newCatalogNum);
    }
    public void updateProductPrice(String pId, float price) {
        contract.updateProductPrice(pId, price);
    }

    // Quantity Agreement methods
    public void updateDiscount(String pId, int quantity, float discount){
        contract.updateDiscount(pId, quantity, discount);
    }
    public Dictionary<Integer,Float> getDiscounts(String pId){
        return contract.getDiscounts(pId);
    }
    public Dictionary<String, Dictionary<Integer, Float>> getDiscounts() {
        return contract.getDiscounts();
    }
    public List<CatalogProduct> searchProduct(String pId){
        return contract.searchProduct(pId);
    }

    public String toString(){
        String cash, credit;
        if (hasCashPayment())
            cash = "y";
        else cash = "n";
        if (hasCreditPayment())
            credit = "y";
        else credit = "n";

        return "supplier id: " + getsId() + " | Bank Account: " + getBankAccount() +
                " | payments: cash-" + cash + " credit-" + credit + " | contacts:\n" + contacts;
    }
}

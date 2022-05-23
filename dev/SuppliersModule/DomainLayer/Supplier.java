package com.company.SuppliersModule.DomainLayer;

import SuppliersModule.DomainLayer.Contract;

import java.time.LocalDate;
import java.util.*;

public class Supplier {
    private final String sId;
    private final String name;
    private final String address;
    private final String bankAccount;
    private final boolean[] paymentMethods = new boolean[2];
    private final Map<String,String> contacts = new HashMap<>();
    private final SuppliersModule.DomainLayer.Contract contract;
    private final SuppliersModule.DomainLayer.OrderController oc;
    private final List<SuppliersModule.DomainLayer.Order> orders = new LinkedList<>();

    //  getters
    public String getsId(){
        return sId;
    }
    public String getName(){return name;}
    public String getAddress(){return address;}
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
        return contract.getDaysOfDelivery();
    }
    public int getMaxSupplyDays() {
        return contract.getMaxDeliveryDuration();
    }
    public int getSupplyCycle(){return contract.getOrderCycle();}
    public boolean hasDeliveryService() {
        return contract.hasDeliveryService();
    }
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
    public List<Order> getOrders() {return orders;}

    // setters
    public void setMaxSupplyDays(int maxSupplyDays) {
        contract.setMaxDeliveryDuration(maxSupplyDays);
    }
    public void setSupplyCycle(int supplyCycle){contract.setOrderCycle(supplyCycle);}
    public void setDeliveryService(boolean deliveryService) {
        contract.setDeliveryService(deliveryService);
    }
    public void addContact(String contactName, String phoneNum){
        contacts.put(contactName, phoneNum);
    }
    public void removeContact(String name){contacts.remove(name);}
    public void changeDaysOfDelivery(int day, boolean state) {contract.changeDaysOfDelivery(day, state);}

    // constructor
    public Supplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum,
                    boolean[] supplyDays, int MaxSupplyDays, int supplCycle, boolean deliveryService,
                    String pId, String catNumber, float price){
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.bankAccount = bankAccount;
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        addContact(contactName, phoneNum);
        contract = new Contract(supplyDays, MaxSupplyDays, supplCycle, deliveryService, pId, catNumber, price);
        oc = OrderController.getInstance();
    }


    // order methods
    public void endDay(){
        List<String> cp = contract.endDay();
        if(cp != null) {
            Map<String, Integer> prodQuantities = oc.orderPeriodic(cp, contract.getPeriodicOrderInterval());
            String phone = contacts.entrySet().stream().findFirst().get().getValue();
            Order order = new Order("todo", sId, name, address, LocalDate.now(), phone); //TODO
            for (Map.Entry<String, Integer> entry : prodQuantities.entrySet()) {
                String pId = entry.getKey();
                int amount = entry.getValue();
                order.addProduct(pId, contract.getCatalogPrice(pId), amount, contract.getDiscount(pId, amount), contract.getFinalPrice(pId, amount));
            }
        }
    }
    public int getPeriodicOrderInterval(){return contract.getPeriodicOrderInterval();}
    public int getDaysForShortageOrder(){return contract.getDaysForShortageOrder();}

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
    public boolean hasProduct(String pId){ return contract.hasProduct(pId);}

    // Quantity Agreement methods
    public void updateDiscount(String pId, int quantity, float discount){contract.updateDiscount(pId, quantity, discount);}
    public Map<Integer, Float> getDiscounts(String pId){
        return contract.getDiscount(pId);
    }
    public Map<String, Map<Integer, Float>> getDiscounts() {
        return contract.getDiscount();
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

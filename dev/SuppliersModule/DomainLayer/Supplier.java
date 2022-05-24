package SuppliersModule.DomainLayer;

import DAL.DAO.SupplierDAO;
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
    private boolean deliveryService;
    private final SuppliersModule.DomainLayer.Contract contract;
    private final SuppliersModule.DomainLayer.OrderController oc;
    private final List<SuppliersModule.DomainLayer.Order> orders = new LinkedList<>();
    private final SupplierDAO dao;

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
        return deliveryService;
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
        this.deliveryService = deliveryService;
        dao.setDeliveryService(this);
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
        this.deliveryService = deliveryService;
        addContact(contactName, phoneNum);
        contract = new Contract(sId, supplyDays, MaxSupplyDays, supplCycle, pId, catNumber, price);
        oc = OrderController.getInstance();
        dao = new SupplierDAO();

        dao.insert(this);
    }

    public Supplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit,
                    Map<String,String> contact,
                    SupplyTime supplyTime,
                    List<CatalogProduct> catalogProducts){

        dao = new SupplierDAO();
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.bankAccount = bankAccount;
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        for(String key : contact.keySet()){
            addContact(key, contact.get(key));
        }
        contract = new Contract(sId, dao.getSupplyTimeFromDB(sId), dao.getCatalogProductsFromDB(this));

        oc = OrderController.getInstance();

        for ( Order o : dao.getOrdersFromDB(sId)){
            orders.add(o);
        }
    }


    // order methods
    public void endDay(){
        List<String> cp = contract.endDay();
        if(cp != null) {
            Map<String, Integer> prodQuantities = oc.orderPeriodic(cp, contract.getPeriodicOrderInterval());
            String phone = contacts.entrySet().stream().findFirst().get().getValue();
            Order order = new Order(sId+LocalDate.now(), sId, name, address, LocalDate.now(), phone);
            for (Map.Entry<String, Integer> entry : prodQuantities.entrySet()) {
                String pId = entry.getKey();
                int amount = entry.getValue();
                order.addProduct(pId, contract.getCatalogPrice(pId), amount, contract.getDiscount(pId, amount), contract.getFinalPrice(pId, amount));
            }
            addOrder(order);
        }

    }
    public void makeShortageOrder(String pId, int quantity, float discount) {
        String phone = contacts.entrySet().stream().findFirst().get().getValue();
        Order order = new Order(sId+LocalDate.now(), sId, name, address, LocalDate.now(), phone);
        order.addProduct(pId, contract.getCatalogPrice(pId), quantity, discount, contract.getFinalPrice(pId, quantity));
        addOrder(order);
    }
    private void addOrder(Order order){
        orders.add(order);
        //TODO save in db
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
    public CatalogProduct searchProduct(String pId){
        return contract.searchProduct(pId);
    }

    public String toString() {
        return "Supplier " + sId + "\n" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", paymentMethods=" + Arrays.toString(paymentMethods) +
                "\n contacts=" + contacts +
                "\n deliveryService=" + deliveryService +
                "\n contract=" + contract ;
    }


}

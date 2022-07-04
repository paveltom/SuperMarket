package SuppliersModule.DomainLayer;

import DAL.Stock_Suppliers.DAOS.StockObjects.ProductDao;
import DAL.Stock_Suppliers.DAOS.SupplierObjects.DeliveryErrorDao;
import DAL.Stock_Suppliers.DAOS.SupplierObjects.StoreInfo;
import DAL.Stock_Suppliers.DAOS.SupplierObjects.SupplierDao;
import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import DeliveryModule.BusinessLayer.Element.Date;
import DeliveryModule.BusinessLayer.Element.DeliveryOrder;
import DeliveryModule.BusinessLayer.Element.Product;
import DeliveryModule.BusinessLayer.Element.Receipt;
import DeliveryModule.BusinessLayer.Element.Site;
import DeliveryModule.BusinessLayer.Type.RetCode;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class Supplier {
    private final String sId;
    private final String name;
    private final String address;
    private final String bankAccount;
    private final boolean[] paymentMethods = new boolean[2];
    private final boolean[] workingDays;
    private final Map<String,String> contacts = new HashMap<>();
    private final SuppliersModule.DomainLayer.Contract contract;
    private final SuppliersModule.DomainLayer.OrderController oc;
    private final List<SuppliersModule.DomainLayer.Order> orders = new LinkedList<>();
    private final SupplierDao sDao;
    private final ProductDao pDao = new ProductDao();
    private final DeliveryController delC = DeliveryController.GetInstance();
    private final DeliveryErrorDao delErrDao = new DeliveryErrorDao();
    private final StoreInfo storeInfo = new StoreInfo();

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
    public boolean[] getWorkingDays() {return workingDays;}
    public boolean[] getSupplyDays() {
        return contract.getDaysOfDelivery();
    }
    public int getSupplyCycle(){return contract.getOrderCycle();}
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
    public void setSupplyCycle(int supplyCycle){contract.setOrderCycle(supplyCycle);}
    public void addContact(String contactName, String phoneNum){
        contacts.put(contactName, phoneNum);
        sDao.addContact(sId, contactName, phoneNum);
    }
    public void removeContact(String name){
        contacts.remove(name);
        sDao.removeContact(sId, name);
    }
    public void changeWeeklyOrdering(boolean[] days) {
        contract.changeWeeklyOrdering(days);
    }

    // constructor
    public Supplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit, boolean[] workingDays,
                    String contactName, String phoneNum,
                    boolean[] orderingDays, int supplCycle,
                    String pId, String catNumber, float price){
        sDao = new SupplierDao();
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.bankAccount = bankAccount;
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        this.workingDays = workingDays;
        addContact(contactName, phoneNum);
        contract = new Contract(sId, orderingDays, supplCycle, pId, catNumber, price);
        oc = OrderController.getInstance();

        sDao.insert(this);
    }

    //db
    public Supplier(String sId, String name, String address, String bankAccount, boolean[] workingDays,
                    boolean cash, boolean credit){
        sDao = new SupplierDao();
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.bankAccount = bankAccount;
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        this.workingDays = workingDays;
        oc = OrderController.getInstance();
        SupplyTime st = sDao.getSupplyTimeFromDB(sId);
        List<CatalogProduct> catalogProducts = sDao.getCatalogProductsFromDB(this);
        QuantityAgreement qa = sDao.getQuantityAgreementFromDB(sId);
        this.contract = new Contract(sId, st, catalogProducts, qa);

        Map<String,String> c = sDao.getContactsFromDB(sId);
        for(String key : c.keySet()){
            addContact(key, c.get(key));
        }

        List<Order> os = sDao.getOrdersFromDB(sId);
        orders.addAll(os);

        Map<String,String > contactsFromDB = sDao.getContactsFromDB(sId);;
        for ( String s : contactsFromDB.keySet()){
            contacts.put(s, contactsFromDB.get(s));
        }
    }


    // order methods
    public void endDay(){
        List<String> cp = contract.endDay();
        if(cp != null && !cp.isEmpty()) {
            Map<String, Integer> prodQuantities = oc.orderPeriodic(cp, contract.getPeriodicOrderInterval());
            if(prodQuantities.isEmpty())
               return;
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
        if(orders.stream().noneMatch(order1 -> order1.getId().equals(order.getId()))) {
            orders.add(order);
            sendOrderToDelivery(order);
        }
    }

    private void sendOrderToDelivery(Order order){
        String[] info = storeInfo.get();
        Site store = new Site(ShippingZone.CreateShippingZoneByName(info[0]), info[1], info[3], info[2]);
        Site supplier = new Site(ShippingZone.CreateShippingZoneByName(info[0]), getAddress(), getName(), order.getContactPhone());
        List<Product> products = new ArrayList<>();
        for (OrderProduct op : order.getProducts()){
            double weight = pDao.getProduct(op.getId()).getWeight();
            Product p = new Product(op.getId(), weight, op.getAmount());
            products.add(p);
        }
        int days = order.getDate().getDayOfMonth();
        int month = order.getDate().getMonthValue();
        int year = order.getDate().getYear();
        Date date =  new Date(days, month, year);
        DeliveryOrder delOrder = new DeliveryOrder(store, supplier, order.getId(), products, date, getWorkingDays());
        Receipt receipt = delC.Deliver(delOrder);
        if(!receipt.Status.equals(RetCode.SuccessfulDelivery)){
            delErrDao.insert(order.getId(), receipt.Status.toString());
        }
    }

    public int getDaysForShortageOrder(){return contract.getDaysForShortageOrder();}

    //products methods
    public void addProduct(String pId, String catalogNum, float price) {
        contract.addProduct(pId, catalogNum, price);
    }
    public void removeProduct(String pId) {
        if(contract.removeProduct(pId))
            sDao.delete(this);
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
                "name: " + name + ",\t\t" +
                "address: " + address + ",\t\t" +
                "bankAccount: " + bankAccount + ",\t\t" +
                "using cash: " + paymentMethods[0] + ",\t\tusing credit: " + paymentMethods[0] + "\n" +
                "contacts: " + contacts + "\n" +
                contract ;
    }

    public void delete(){
        contract.delete();
    }
}

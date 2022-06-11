package SuppliersModule.DomainLayer;

//import DAL.DAO.SupplierDAO;

import DAL.DAOS.SupplierObjects.SupplierDao;

import java.util.*;
import java.util.stream.Collectors;

public class SupplierController {
    private final SupplierDao dao;
    private final List<Supplier> suppliers;
    private final OrderController oc = OrderController.getInstance();

    public SupplierController(){
        suppliers = new LinkedList<>();
        dao = new SupplierDao();
    }

    public SupplierController(boolean loadData){
        suppliers = new LinkedList<>();
        dao = new SupplierDao();
        dao.getAll();
    }

    // getters
    public List<Supplier> getSuppliers() {
        //return suppliers;
        return dao.getAll();
    }
    public Contract getContract(String sId){
        return dao.get(sId).getContract();
    }
    public List<CatalogProduct> getCatalog(String sId){
        return dao.get(sId).getCatalog();
    }
    public QuantityAgreement getQa(String sId) {
        return dao.get(sId).getQuantityAgreement();
    }
    public boolean[] getSupplyDays(String sId) {
        return dao.get(sId).getSupplyDays();
    }
    public int getSupplyMaxDays(String sId) {
        return dao.get(sId).getMaxSupplyDays();
    }
    public int getSupplyCycle(String sId){
        return dao.get(sId).getSupplyCycle();}
    public boolean hasDeliveryService(String sId) {
//        checkSupplier(sId);
//        return getSupplier(sId).hasDeliveryService();

        return dao.get(sId).hasDeliveryService();
    }

    //  setters
    public void setMaxSupplyDays(String sId, int maxSupplyDays) {
        dao.get(sId).setMaxSupplyDays(maxSupplyDays);
    }
    public void setSupplyCycle(String sId, int supplyCycle) {
        dao.get(sId).setSupplyCycle(supplyCycle);
        List<String> products = getSupplier(sId).getContract().getOrderProducts();
        for (String pId : products) {
            updateBestSeller(pId);
        }
    }
    public void setDeliveryService(String sId, boolean deliveryService) {
        dao.get(sId).setDeliveryService(deliveryService);
    }

    //  order methods
    public void endDay(){
        for (Supplier s: suppliers ){
            s.endDay();}
    }
    public Map<Supplier, Integer> getSuppliersDays(String pId){
        List<Supplier> supplierList = suppliers.stream().filter(supplier -> supplier.hasProduct(pId)).collect(Collectors.toList());
        Map<Supplier, Integer> suppDays = new HashMap<>();
        for (Supplier supp: supplierList){
            int d = supp.getDaysForShortageOrder();
            suppDays.put(supp, d);
        }
        return suppDays;
    }

    public void orderShortage(Map<Supplier, Integer> suppQuantities, String pId){
        Supplier bestSupp = null;
        int bestQuantity = -1;
        float bestDiscount = -1;
        for (Map.Entry<Supplier, Integer> entry : suppQuantities.entrySet()){
            float currentDiscount = entry.getKey().getContract().getDiscount(pId, entry.getValue());
            if(currentDiscount > bestDiscount){
                bestSupp = entry.getKey();
                bestQuantity = entry.getValue();
                bestDiscount = currentDiscount;
            }
        }
//        if(bestSupp != null)
//            bestSupp.makeShortageOrder(pId, bestQuantity, bestDiscount);
    }

    //  other methods

    public void addSupplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum,
                            boolean[] supplyDays, int maxSupplyDays, int supplCycle, boolean deliveryService,
                            String pId, String catNumber, float price){
        if(hasSupp(sId))
            throw new IllegalArgumentException("supplier with id " + sId + " already exist!");
        // TODO if(pdm.getProduct())
        //    throw new IllegalArgumentException("no such product in stock system, first add product at stock");
        suppliers.add(new Supplier(sId, name, address, bankAccount, cash, credit,
                contactName, phoneNum, supplyDays, maxSupplyDays,
                supplCycle, deliveryService, pId, catNumber, price));

        updateBestSeller(pId);
    }

    public void removeSupplier(String sId){
        Supplier s = dao.get(sId);
        List<String> products = s.getContract().getOrderProducts();
        suppliers.remove(s);
        for(String pId : products){
            updateBestSeller(pId);
        }
    }
    public void addContact(String sId, String contactName, String phoneNum){
        dao.get(sId).addContact(contactName, phoneNum);
    }
    public void removeContact(String sId, String name){
        dao.get(sId).removeContact(name);
    }

    //products methods
    public void addProduct(String sId, String pId, String catalogNum, float price) {
        dao.get(sId).addProduct(pId, catalogNum, price);
        updateBestSeller(pId);
    }
    public void removeProduct(String sId, String pId) {
        dao.get(sId).removeProduct(pId);
        updateBestSeller(pId);
    }
    public void updateCatalogNum(String sId, String pId, String newCatalogNum) {
        dao.get(sId).updateCatalogNum(pId, newCatalogNum);
    }
    public void updateProductPrice(String sId, String pId, float price) {
        dao.get(sId).updateProductPrice(pId, price);
    }
    
    // Quantity Agreement methods
    public void updateDiscount(String sId, String pId, int quantity, float discount){
        dao.get(sId).updateDiscount(pId, quantity, discount);
        updateBestSeller(pId);
    }
    public Map<Integer, Float> getDiscounts(String sId, String pId){
        return dao.get(sId).getDiscounts(pId);
    }
    public Map<String,String> getContacts(String sId){
        return dao.get(sId).getContacts();
    }
    public Map<String, Map<Integer, Float>> getDiscounts(String sId) {
        return dao.get(sId).getDiscounts();
    }
    public List<Supplier> searchProduct(String pId){
        List<Supplier> prodSupp = new LinkedList<>();
        for(Supplier s: suppliers){
            if(s.searchProduct(pId)!=null)
                prodSupp.add(s);
        }
        return prodSupp;
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

    public void changeDaysOfDelivery(String sId, int day, boolean state) {
        dao.get(sId).changeDaysOfDelivery(day, state);
        List<String> products = getSupplier(sId).getContract().getOrderProducts();
        for(String pId : products){
            updateBestSeller(pId);
        }
    }

    private void updateBestSeller(String pId) {
        List<Supplier> providers = getSuppliers(pId);
        Map<Supplier, Integer> suppQuantities = new HashMap<>();
        for(Supplier s : providers){
            suppQuantities.put(s, oc.getQuantity(pId, s.getPeriodicOrderInterval()));
        }

        Supplier bestSupp = null;
        float bestDiscount = -1;
        for (Map.Entry<Supplier, Integer> entry : suppQuantities.entrySet()){
            float currentDiscount = entry.getKey().getContract().getDiscount(pId, entry.getValue());
            if(currentDiscount > bestDiscount){
                bestSupp = entry.getKey();
                bestDiscount = currentDiscount;
            }
        }
        if(bestSupp != null){
            for(Supplier s : providers){
                s.getContract().updatePeriodicOrderProduct(pId, false);
            }
            bestSupp.getContract().updatePeriodicOrderProduct(pId, true);
        }
    }

    public List<Supplier> getSuppliers(String pId){
        return suppliers.stream().filter(supplier -> supplier.hasProduct(pId)).collect(Collectors.toList());
    }

    public List<Order> getOrders(String sId) {
        return dao.get(sId).getOrders();
    }
}

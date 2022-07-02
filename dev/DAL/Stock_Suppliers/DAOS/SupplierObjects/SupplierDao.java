package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import DAL.Stock_Suppliers.IdentityMaps.SuppliersIdentityMap;
import SuppliersModule.DomainLayer.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SupplierDao extends DAO {

    private SuppliersIdentityMap suppliersIdentityMap;
    private boolean isAllLoaded;


    public SupplierDao(){
        suppliersIdentityMap = SuppliersIdentityMap.getInstance();
        isAllLoaded = false;
    }

    public void insert(Supplier s){
        String[] params = {s.getsId(), s.getName(), s.getAddress(), s.getBankAccount(),
                String.valueOf(s.hasCashPayment()), String.valueOf(s.hasCreditPayment()),
                String.valueOf(true)}; //todo : delete this has delivery service field
        insert("Suppliers", params);
        suppliersIdentityMap.cache(s);
    }

    public void delete(Supplier s){
        String[] keys = {"supplier_id"};
        String[] keysVals = {s.getsId()};
        delete("Suppliers", keys, keysVals);
        suppliersIdentityMap.remove(s);
    }

    public void setDeliveryService(Supplier s){
        String[] keys = {"supplier_id"};
        String[] keysVals = {s.getsId()};
        update("Suppliers", keys, keysVals, "deliveryService", String.valueOf(true)); // todo : delete this has delivery service field
    }

    public List<Supplier> getAll(){
        if(!isAllLoaded) {

            List<String[]> st = load("Suppliers", null, null);
            List<Supplier> output = new ArrayList<>();

            if(st != null) {
                for (String[] s : st) {
                    Supplier sup = new Supplier(s[0], s[2], s[3], s[1], Boolean.valueOf(s[4]), Boolean.valueOf(s[5]));
                    suppliersIdentityMap.cache(sup);
                    output.add(0, sup);
                }
            }
            isAllLoaded = true;
            return output;
        }
        else
            return suppliersIdentityMap.getAll();
    }

    public Supplier get(String sId){
        Supplier s = suppliersIdentityMap.isCached(sId);
        if(s == null) {
            String[] params = {"supplier_id"};
            String[] paramsWV = {sId};
            List<String[]> dbs = super.load("Suppliers", params, paramsWV); //assuming unique id

            if(dbs.isEmpty())
                throw new IllegalArgumentException("no suchSupplier with id " + sId);
            else{
                String[] str = dbs.get(0);
                s = new Supplier(str[0], str[1], str[2], str[3], Boolean.valueOf(str[4]), Boolean.valueOf(str[5]));
                suppliersIdentityMap.cache(s);
            }
        }
        return s;
    }

    public SupplyTime getSupplyTimeFromDB(String sId){
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> st = load("SupplyTimes",paramsW, paramsWV);

        //assuming unique sId
        return makeSupplyTimeFromDB(st.get(0));
    }
    private SupplyTime makeSupplyTimeFromDB(String[] s){
        String[] splitDaysOfDelivery = s[1].substring(1, s[1].length()-1).split(", ");
        boolean[] daysOfDelivery = new boolean[7];
        for(int i = 0; i < 7; i++){
            daysOfDelivery[i] = Boolean.valueOf(splitDaysOfDelivery[i]);
        }

        return new SupplyTime(s[0], daysOfDelivery, Integer.valueOf(s[2]), Integer.valueOf(s[3]), Integer.valueOf(s[4]));
    }

    public List<CatalogProduct> getCatalogProductsFromDB(Supplier s){
        List<CatalogProduct> output = new ArrayList<>();
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {s.getsId()};
        List<String[]> sFromDB = load("Product_Contract", paramsW, paramsWV);

        for(String[] p : sFromDB){
            output.add(0, makeCatalogProduct(p));
        }

        return output;
    }
    private CatalogProduct makeCatalogProduct(String[] p){
        return new CatalogProduct(p[0], p[1], p[4], Float.valueOf(p[2]), Boolean.valueOf(p[3]));
    }

    public QuantityAgreement getQuantityAgreementFromDB(String sId){
        String[] paramsW = {"supplier_id"} ;
        String[] paramsWV = {sId};
        List<String[]> discounts =load("QuantityAgreements", paramsW, paramsWV);

        if(discounts.isEmpty())
            return null;

        Map<String, Map<Integer, Float>> Discounts = new Hashtable<>();
        QuantityAgreement qa = new QuantityAgreement(sId);
        for(String[] s : discounts){
            qa.loadDiscountFromDB(s[1],Integer.valueOf(s[2]), Float.valueOf(s[3]));

        }
        return qa;
    }


    public List<Order> getOrdersFromDB(String sId){
        List<Order> output = new ArrayList<>();
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> sFromDB = load("Orders", paramsW, paramsWV);

        for(String[] o : sFromDB){
            output.add(0, makeOrder(o));
        }

        return output;
    }
    private Order makeOrder(String[] o){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = o[2];

        LocalDate localDate = LocalDate.parse(date, formatter);

        return new Order(o[1], o[0], o[4], o[5],
                localDate, o[3], true);
    }

    public Map<String,String> getContactsFromDB(String sId){
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> st = load("Contacts",paramsW, paramsWV);

        Map<String,String> contacts = new Hashtable<>();
        for(String[] s : st){
            contacts.put(s[1], s[2]);
        }

        return contacts;
    }

    public void addContact(String sId, String name, String num){
        String[] params = {sId, name, num};
        insert("Contacts", params);
    }

    public void removeContact(String sid, String name){
        String[] keys = {"supplier_id", "contactName"};
        String[] keysVals = {sid, name};
        delete("Contacts", keys, keysVals);
    }

}

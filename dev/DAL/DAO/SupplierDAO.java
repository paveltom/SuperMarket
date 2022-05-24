package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SupplierDAO {
    DataBaseConnection conn;
    public SupplierDAO(){ conn = new DataBaseConnection(); }

    public void insert(Supplier s){
        String[] params = {s.getsId(), s.getName(), s.getAddress(), s.getBankAccount(),
                String.valueOf(s.hasCashPayment()), String.valueOf(s.hasCreditPayment()),
                String.valueOf(s.hasDeliveryService())};
        conn.insert("suppliers", params);
    }

    public void delete(Supplier s){
        String[] keys = {"supplier_id"};
        String[] keysVals = {s.getsId()};
        conn.delete("Suppliers", keys, keysVals);
    }

    public void setDeliveryService(Supplier s){
        String[] keys = {"supplier_id"};
        String[] keysVals = {s.getsId()};
        conn.update("Suppliers", keys, keysVals, "deliveryService", String.valueOf(s.hasDeliveryService()));
    }

    public SupplyTime getSupplyTimeFromDB(String sId){
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> st = conn.select("SupplyTimes",paramsW, paramsWV);

        //assuming unique sId
        return makeSupplyTimeFromDB(st.get(0));
    }
    private SupplyTime makeSupplyTimeFromDB(String[] s){
        String[] splitDaysOfDelivery = s[1].substring(1, s[1].length()-2).split(",");
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
        List<String[]> sFromDB = conn.select("Product_Contract", paramsW, paramsWV);

        for(String[] p : sFromDB){
            output.add(0, makeCatalogProduct(p));
        }

        return output;
    }

    private CatalogProduct makeCatalogProduct(String[] p){
        return new CatalogProduct(p[0], p[1], p[4], Float.valueOf(p[2]), Boolean.valueOf(p[3]));
    }

    public List<Order> getOrdersFromDB(String sId){
        List<Order> output = new ArrayList<>();
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> sFromDB = conn.select("Orders", paramsW, paramsWV);

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

//    public QuantityAgreement getQuantityAgreementFromDB(Contract c){
//        String[] paramsW = {"supplier_id"} ;
//        String[] paramsWV = {c.getsId()};
//        List<String[]> discounts = conn.select("QuantityAgreements", paramsW, paramsWV);
//
//        Map<String, Map<Integer, Float>> Discounts = new Hashtable<>();
//
//
//        QuantityAgreement qa = new QuantityAgreement();
//    }
}

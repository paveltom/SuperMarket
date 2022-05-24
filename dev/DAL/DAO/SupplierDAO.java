package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.Supplier;

import java.util.Arrays;

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


}

package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class LocationDao extends DAO<String> {
    public void insert(String ShippingZone, String Adress){
        String[] params = {ShippingZone, Adress};
        insert("Location", params);
    }
    public String[] get(){
        List<String[]> sFromDB = load("Location", null, null);

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                return s;
            }
        }

        return null;
    }
}

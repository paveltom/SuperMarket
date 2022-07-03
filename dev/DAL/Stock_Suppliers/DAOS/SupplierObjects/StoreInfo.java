package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;

import java.util.List;

public class StoreInfo extends DAO<String> {
    public void insert(String ShippingZone, String Adress, String Phone, String Name){
        String[] params = {ShippingZone, Adress, Phone, Name};
        insert("StoreInfo", params);
    }
    public String[] get(){
        List<String[]> sFromDB = load("StoreInfo", null, null);

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                return s;
            }
        }

        return null;
    }

}

package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.QuantityAgreement;

public class QuantityAgreementDAO {
    DataBaseConnection conn;

    public QuantityAgreementDAO(){
        conn = new DataBaseConnection();
    }

    public void addDiscount(String sId, String pId, int quantity, float discount){
        String[] params = {sId, pId, String.valueOf((quantity)), String.valueOf(discount)};
        conn.insert("QuantityAgreements", params);
    }

    public void updateDiscount(String sId, String pId, int quantity, float discount){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        conn.update("QuantityAgreements", keys, keysVals, "discount", String.valueOf(discount));
    }
    public void removeDiscount(String sId, String pId, int quantity){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        conn.delete("QuantityAgreements", keys, keysVals);
    }




}

package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.QuantityAgreement;

public class QuantityAgreementDao extends DAO {

    public void addDiscount(String sId, String pId, int quantity, float discount){
        String[] params = {sId, pId, String.valueOf((quantity)), String.valueOf(discount)};
        insert("QuantityAgreements", params);
    }

    public void updateDiscount(String sId, String pId, int quantity, float discount){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        update("QuantityAgreements", keys, keysVals, "discount", String.valueOf(discount));
    }
    public void removeDiscount(String sId, String pId, int quantity){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        delete("QuantityAgreements", keys, keysVals);
    }

    public void delete(String sid){
        String[] keys = {"supplier_id"};
        String[] keysVals = {sid};
        delete("QuantityAgreements", keys, keysVals);
    }
}

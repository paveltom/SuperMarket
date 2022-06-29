package DAL.DAOS.SupplierObjects;

import DAL.DAOS.DAO;

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
}

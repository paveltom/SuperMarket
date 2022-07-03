package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.Order;

public class DeliveryErrorDao extends DAO<Order> {
    public void insert(String oId, String msg){
        String[] params = {oId, msg};
        insert("DeliveryErrors", params);
    }
    public void delete(String oId){
        String[] keys = {"order_id"};
        String[] keysVals = {oId};
        delete("DeliveryErrors", keys, keysVals);
    }
}

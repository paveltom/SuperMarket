package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.Order;
import java.util.LinkedList;
import java.util.List;

public class DeliveryErrorDao extends DAO<Order> {
    public void insert(String oId, String msg){
        String[] params = {oId, msg};
        insert("DeliveryErrors", params);
    }
    public List<String[]> getErrors(){
        List<String[]> sFromDB = load("DeliveryErrors", null, null);
        List<String[]> errors = new LinkedList<>();

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                errors.add(s);
            }
        }

        return errors;
    }
}

package DAL.DAOS.SupplierObjects;

import DAL.DAOS.DAO;
import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderDao extends DAO {

    public void insert(Order o){
        String[] params = {o.getSupId(), o.getId(), String.valueOf(o.getDate()), o.getContactPhone(),
                        o.getSupName(), o.getSupAddress()};
        insert("Orders", params);
    }
    public void delete(Order o){
        String[] keys = {"supplier_id", "id"};
        String[] keysVals = {o.getSupId(), o.getId()};
        delete("Orders", keys, keysVals);
    }

    public List<OrderProduct> loadAllOrderProducts(Order o){
        List<OrderProduct> output = new ArrayList<>();
        String[] paramsW = {"supplier_id", "order_id"};
        String[] paramsWV = {o.getSupId(), o.getId()};
        List<String[]> sFromDB = load("Product_Order", paramsW, paramsWV);

        for(String[] s : sFromDB){
            output.add(0, makeOrderProduct(s));
        }

        return output;
    }

    private OrderProduct makeOrderProduct(String[] s){
        return new OrderProduct(s[0], s[2], s[1], Float.valueOf(s[6]), Integer.valueOf(s[3]),
                Float.valueOf(s[4]), Float.valueOf(s[5]), true);
    }
}

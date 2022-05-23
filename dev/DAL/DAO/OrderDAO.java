package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.OrderProduct;

public class OrderDAO {
    DataBaseConnection conn;
    OrderProductDAO opDAO;

    public OrderDAO(){
        conn = new DataBaseConnection();
    }

    public void insert(Order o){
        String[] params = {o.getSupId(), o.getId(), String.valueOf(o.getDate()), o.getContactPhone(),
                        o.getSupName(), o.getSupAddress()};
        conn.insert("Orders", params);
    }
    public void delete(Order o){
        String[] keys = {"supplier_id", "id"};
        String[] keysVals = {o.getSupId(), o.getId()};
        conn.delete("Orders", keys, keysVals);
    }
    public void addProduct(OrderProduct op){}
    public void changeProduct(OrderProduct op){}
}

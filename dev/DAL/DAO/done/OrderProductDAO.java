//package DAL.DAO;
//
//import DAL.DataBaseConnection;
//import SuppliersModule.DomainLayer.OrderProduct;
//
//public class OrderProductDAO {
//    private DataBaseConnection conn;
//
//    public OrderProductDAO(){
//        conn = new DataBaseConnection();
//    }
//
//    public void insert(OrderProduct op){
//        String[] params = {op.getsId(), op.getoId(), op.getId(), String.valueOf(op.getAmount()), String.valueOf(op.getDiscount()),
//                        String.valueOf(op.getFinalPrice()), String.valueOf(op.getCatalogPrice())};
//        conn.insert("Product_Order", params);
//    }
//
//    public void delete(OrderProduct op){
//        String[] keys = {"supplier_id", "product_id", "order_id"};
//        String[] keysVals = {op.getsId(), op.getoId(), op.getId()};
//        conn.delete("Product_Order", keys, keysVals);
//    }
//
//    public void setAmount(OrderProduct op){
//        setAttribute(op, "quantity", String.valueOf(op.getAmount()));
//    }
//    public void setDiscount(OrderProduct op){
//        setAttribute(op, "discount", String.valueOf(op.getDiscount()));
//    }
//    public void setFinalPrice(OrderProduct op){
//        setAttribute(op, "finalPrice", String.valueOf(op.getFinalPrice()));
//    }
//
//    private void setAttribute(OrderProduct op, String attribute, String value){
//        String[] keys = {"supplier_id", "product_id", "order_id"};
//        String[] keysVals = {op.getsId(), op.getId(), op.getoId()};
//        conn.update("Product_Order", keys, keysVals, attribute, String.valueOf(value));
//    }
//
//
//}

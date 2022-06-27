//package DAL.DAO;
//
//import DAL.DataBaseConnection;
//import SuppliersModule.DomainLayer.CatalogProduct;
//import SuppliersModule.DomainLayer.OrderProduct;
//
//public class CatalogProductDAO {
//    private DataBaseConnection conn;
//
//    public CatalogProductDAO(){conn = new DataBaseConnection();}
//
//    public void insert(CatalogProduct cp){
//        String[] params = {cp.getsId(), cp.getId(), String.valueOf(cp.getPrice()), String.valueOf(cp.isInPeriodicOrder())
//                            , cp.getCatalogNum()};
//        conn.insert("Product_Contract", params);
//    }
//
//    public void delete(CatalogProduct cp){
//        String[] keys = {"supplier_id", "product_id"};
//        String[] keysVals = {cp.getsId(), cp.getId()};
//        conn.delete("Product_Contract", keys, keysVals);
//    }
//
//    public void setCatalogNum(CatalogProduct cp){
//        setAttribute(cp, "catalogNum", cp.getCatalogNum());
//    }
//
//    public void setPrice(CatalogProduct cp){
//        setAttribute(cp, "price", String.valueOf(cp.getPrice()));
//    }
//
//    public void setInPeriodicOrder(CatalogProduct cp){
//        setAttribute(cp, "is_periodic_order", String.valueOf(cp.isInPeriodicOrder()));
//    }
//
//    private void setAttribute(CatalogProduct cp, String attribute, String value){
//        String[] keys = {"supplier_id", "product_id"};
//        String[] keysVals = {cp.getsId(), cp.getId()};
//        conn.update("Product_Contract", keys, keysVals, attribute, String.valueOf(value));
//    }
//}

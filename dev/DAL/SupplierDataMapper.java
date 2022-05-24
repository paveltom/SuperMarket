//package DAL;
//
//import DAL.DataBaseConnection;
//import SuppliersModule.DomainLayer.CatalogProduct;
//import SuppliersModule.DomainLayer.Supplier;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class SupplierDataMapper {
//    DAL.DataBaseConnection conn;
//    public SupplierDataMapper(){
//        conn = new DataBaseConnection();
//    }
//
//    public List<Supplier> loadSuppliers(){
//        //load all suppliers + contract+ qa + catalogproducts
//        List<String[]> sups = conn.select("Suppliers", null, null);
//        List<Supplier> BSups = new ArrayList<>();
//        if(sups == null)
//            return null;
//        for(String[] s: sups){
//            //unpacking supdays
//            String[] splittedSupDays = s[6].substring(2, s[6].length()-2).split(",");
//            boolean[] supDays = new boolean[7];
//            for(int i = 0; i < 7; i++)
//                supDays[i] = Boolean.valueOf(splittedSupDays[i]);
//
//            //LoadCatalogProducts for this supplier
//            List<CatalogProduct> loadedCatalogProducts = loadCatalogProductForSup(s[0]);
//
//            //getting quantityAgreement
////            BSups.add(0, new Supplier(s[0], s[1], Boolean.valueOf(s[2]), Boolean.valueOf(s[3]),
////                    supDays, Integer.valueOf(s[5]), Integer.valueOf(s[6]), Boolean.valueOf(s[7])));
//        }
//        System.out.println("wow");
//        return null;
//    }
//
//    private List<CatalogProduct> loadCatalogProductForSup(String supID){
//        String[] paramsWhere = {"supplier_id"};
//        String[] paramsWhereValues = {supID};
//
//        List<String[]> catalogProducts = conn.select("Product_Contract", paramsWhere, paramsWhereValues);
//        List<CatalogProduct> loadedCatalogProducts = new ArrayList<>();
//        if(catalogProducts == null)
//            return null;
//
//        for(String[] s: catalogProducts){
//            loadedCatalogProducts.add(0, new CatalogProduct(s[1], s[4], Float.valueOf(s[2])));
//        }
//        return loadedCatalogProducts;
//    }
//    public void loadOrders(){
//
//    }
////    public void addSupplier(String sId, String name, String address, String BankAccount, boolean cash, boolean credit, boolean[] supDays, int maxSupDays, int supCycle, boolean hasDeliveryService){
////        String[] params = {sId,name, address, BankAccount, String.valueOf(cash), String.valueOf(credit), Arrays.toString(supDays), String.valueOf(maxSupDays), String.valueOf(supCycle),
////                String.valueOf(hasDeliveryService)};
////        conn.insert("suppliers", params);
////    }
////
////    public void removeSupplier(String sId){
////        String[] keys = {"supplier_id"};
////        String[] keysVals = {sId};
////        conn.delete("Suppliers", keys, keysVals);
////    }
//
//    public void addContact(String sId, String contactName, String phoneNum){
//        String[] params = {sId, contactName, phoneNum};
//        conn.insert("Contacts", params);
//    }
//
//    public void removeContact(String sId, String name, String phoneNum){
//        String[] keys = {"supplier_id", "name", "phoneNum"};
//        String[] keysVals = {sId, name, phoneNum};
//        conn.delete("Contacts", keys, keysVals);
//    }
//
//    public void addProduct(String sId, String pId, String catalogNum, float price, boolean isPeriodic){
//        String[] params = {sId, pId, String.valueOf((price)), String.valueOf(isPeriodic), catalogNum};
//        conn.insert("Product_Contract", params);
//    }
//
//    public void removeProduct(String sId, String pId){
//        String[] keys = {"supplier_id", "product_id"};
//        String[] keysVals = {sId, pId};
//        conn.delete("Product_Contract", keys, keysVals);
//    }
//    public void updateProductCatalogNum(String sId, String pId, String newCatalogNum){
//        String[] keys = {"supplier_id", "product_id"};
//        String[] keysVals = {sId, pId};
//        conn.update("Product_Contract", keys, keysVals, "catalogNum", newCatalogNum);
//    }
//    public void updateProductPrice(String sId, String pId, float price){
//        String[] keys = {"supplier_id", "product_id"};
//        String[] keysVals = {sId, pId};
//        conn.update("Product_Contract", keys, keysVals, "price", String.valueOf(price));
//    }
//
//    public void addDiscount(String sId, String pId, int quantity, float discount){
//        String[] params = {sId, pId, String.valueOf((quantity)), String.valueOf(discount)};
//        conn.insert("QuantityAgreement", params);
//    }
//
//    public void updateDiscount(String sId, String pId, int quantity, float discount){
//        String[] keys = {"supplier_id", "product_id", "quantity"};
//        String[] keysVals = {sId, pId, String.valueOf(quantity)};
//        conn.update("QuantityAgreement", keys, keysVals, "discount", String.valueOf(discount));
//    }
//    public void removeDiscount(String sId, String pId, int quantity){
//        String[] keys = {"supplier_id", "product_id", "quantity"};
//        String[] keysVals = {sId, pId, String.valueOf(quantity)};
//        conn.delete("QuantityAgreement", keys, keysVals);
//    }
////    public void changeDaysOfDelivery(String sId, boolean[] supDays) {
////        String[] keys = {"supplier_id"};
////        String[] keysVals = {sId};
////        conn.update("Suppliers", keys, keysVals, "supplyDays", String.valueOf(supDays));
////    }
//
//    public void addOrder(String orderId, String sId, String sName, String sAddress, String contactPhone){
//        String[] params = {orderId, sId, sName, sAddress, contactPhone};
//        conn.insert("Orders", params);
//    }
//
//
//
//
//}

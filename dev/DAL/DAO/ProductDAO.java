package DAL.DAO;

import DAL.DataBaseConnection;
import DAL.ProductIdentityMap;
import StockModule.BusinessLogicLayer.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProductDAO {
    private ProductIdentityMap identityMap;
    DataBaseConnection conn;

    public ProductDAO(){
        identityMap = ProductIdentityMap.getInstance();
        conn = new DataBaseConnection();
    }

    public Product getProduct(String pId){
        Product p = identityMap.isCached(pId);
        if (p == null){
            p = getProductFromDB(pId);
        }
        return p;
    }

    private Product getProductFromDB(String pId){
        String[] paramsW = {"product_id"};
        String[] paramsWV = {pId};
        List<String[]> p = conn.select("Products", paramsW, paramsWV);
        return makeProduct(p);
    }

    private Product makeProduct(List<String[]> p){
        //assuming uniqe pid's
        try {
            return new Product(p.get(0)[1], p.get(0)[2], Integer.valueOf(p.get(0)[3]),
                    Integer.valueOf(p.get(0)[4]),
                    new SimpleDateFormat("dd/MM/yyyy").parse(p.get(0)[4]), Integer.valueOf(p.get(0)[4]), true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Product p){
        String[] params = {p.getID(), p.getName(), p.getManufacturer(), String.valueOf(p.getAmountToNotify()),
                String.valueOf(p.getCategoryID()), String.valueOf(p.getSupplyTime()), String.valueOf(p.getDemand())};
        conn.insert("Products", params);
        identityMap.cach(p);
    }

    public void delete(Product p){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        conn.delete("Products", keys, keysVals);
        identityMap.remove(p);
    }
    public void updateAmount(Product p){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        conn.update("Products", keys, keysVals, "amount", String.valueOf(p.getAmount()));
    }
}

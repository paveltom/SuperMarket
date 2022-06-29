package DAL.DAOS.StockObjects;

import DAL.DAOS.DAO;
import DAL.IdentityMaps.ProductIdentityMap;
import StockModule.BusinessLogicLayer.Item;
import StockModule.BusinessLogicLayer.Product;
import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.OrderProduct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends DAO {
    private ProductIdentityMap identityMap;

    public ProductDao(){
        identityMap = ProductIdentityMap.getInstance();
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
        List<String[]> p = load("Products", paramsW, paramsWV);
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
        insert("Products", params);
        identityMap.cache(p);
    }

    public void delete(Product p){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        delete("Products", keys, keysVals);
        identityMap.remove(p);
    }

    public void updateAmount(Product p){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        update("Products", keys, keysVals, "amount", String.valueOf(p.getAmount()));
    }




    public List<Item> loadItems(String pId){
        List<Item> output = new ArrayList<>();
        String[] paramsW = {"product_id"};
        String[] paramsWV = {pId};
        List<String[]> sFromDB = load("Items", paramsW, paramsWV);

        for(String[] s : sFromDB){
            output.add(0, makeItem(s));
        }

        return output;
    }

    private Item makeItem(String[] s){
        try {
            return new Item(s[1], s[0],
                    new SimpleDateFormat("dd/MM/yyyy").parse(s[2]),
                    Boolean.valueOf(s[3]), Boolean.valueOf(s[4]), Integer.valueOf(s[5]),
                    true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

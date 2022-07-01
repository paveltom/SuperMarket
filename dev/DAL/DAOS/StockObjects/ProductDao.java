package DAL.DAOS.StockObjects;

import DAL.DAOS.DAO;
import DAL.IdentityMaps.ProductIdentityMap;
import StockModule.BusinessLogicLayer.Category;
import StockModule.BusinessLogicLayer.Item;
import StockModule.BusinessLogicLayer.Product;
import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.OrderProduct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProductDao extends DAO {
    private ProductIdentityMap identityMap;
    private CategoryDao cDao;

    public ProductDao(){
        identityMap = ProductIdentityMap.getInstance();
        cDao = new CategoryDao();
    }

    public Product getProduct(String pId){
        Product p = identityMap.isCached(pId);
        if (p == null){
            p = getProductFromDB(pId);
        }
        return p;
    }

    public List<Product> getAllProducts(){
        List<Product> ans = new LinkedList<>();
        List<String[]> p = load("Products", null, null);

        for(String[] s : p){
            List<String[]> tmp = new LinkedList<>();
            tmp.add(s);
            ans.add(makeProduct(tmp));
        }

        return ans;
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
            return new Product(p.get(0)[1], p.get(0)[2], Integer.parseInt(p.get(0)[3]),
                    Integer.parseInt(p.get(0)[4]), Integer.parseInt(p.get(0)[6]), true);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public void insert(Product p){
        String[] params = {p.getID(), p.getName(), p.getManufacturer(),
                String.valueOf(p.getCategoryID()), String.valueOf(p.getDemand())};
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
                    Boolean.parseBoolean(s[3]), Boolean.parseBoolean(s[4]), Integer.parseInt(s[5]),
                    true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> loadCategories(){
        List<Category> cats = new LinkedList();
        List<String[]> sFromDB = load("Category", null, null);
        Map<Integer, Category> catsMap  = new Hashtable<>();

        for(String[] s : sFromDB){
            Category c = new Category(Integer.valueOf(s[0]), s[1], true);
            cats.add(c);
            catsMap.put(c.getID(), c);
        }

        int[][] relations = cDao.loadRelations();
        Map<Integer, List<Category>> relMap = new Hashtable<>(); //catId, SonLIST
        for(int i = 0; i < relations.length; i++){
            catsMap.get(relations[i][0]).setChildFromDB(catsMap.get(relations[i][1]));
            catsMap.get(relations[i][1]).setParentFromDB(catsMap.get(relations[i][0]));
        }


        return cats;
    }

}

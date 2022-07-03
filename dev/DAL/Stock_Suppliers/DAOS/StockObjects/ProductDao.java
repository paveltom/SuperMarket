package DAL.Stock_Suppliers.DAOS.StockObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import DAL.Stock_Suppliers.IdentityMaps.ProductIdentityMap;
import StockModule.BusinessLogicLayer.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        if(p != null) {
            for (String[] s : p) {
                List<String[]> tmp = new LinkedList<>();
                tmp.add(s);
                ans.add(makeProduct(tmp));
            }
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
                    Integer.parseInt(p.get(0)[4]), Integer.parseInt(p.get(0)[5]), Double.valueOf(p.get(0)[6]), true);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public void insert(Product p){
        String[] params = {p.getID(), p.getName(), p.getManufacturer(),
                String.valueOf(p.getAmountToNotify()),
                String.valueOf(p.getCategoryID()), String.valueOf(p.getDemand()), String.valueOf(p.getWeight())};
        insert("Products", params);
        identityMap.cache(p);
    }

    public void delete(Product p){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        delete("Products", keys, keysVals);
        identityMap.remove(p);
    }

    private void setAttribute(Product p, String attribute, String value){
        String[] keys = {"product_id"};
        String[] keysVals = {p.getID()};
        update("Products", keys, keysVals, attribute, String.valueOf(value));

    }

    public void updateAmount(Product p){
        setAttribute(p, "amount", String.valueOf(p.getAmount()));
    }

    public void setID(Product p){
        setAttribute(p, "product_id", p.getID());
    }

    public void setName(Product p){
        setAttribute(p, "name", p.getName());
    }

    public void setManufacturer(Product p){
        setAttribute(p, "manufacturer", p.getManufacturer());
    }

    public void setAmountToNotify(Product p){
        setAttribute(p, "amountToNotify", String.valueOf(p.getAmountToNotify()));
    }

    public void setCategoryID(Product p){
        setAttribute(p, "categoryID", String.valueOf(p.getCategoryID()));
    }

    public void setDemand(Product p){
        setAttribute(p, "demand", String.valueOf(p.getDemand()));
    }

    public void setWeight(Product p){
        setAttribute(p, "weight", String.valueOf(p.getWeight()));
    }

    public List<Item> loadItems(String pId){
        List<Item> output = new ArrayList<>();
        String[] paramsW = {"product_id"};
        String[] paramsWV = {pId};
        List<String[]> sFromDB = load("Items", paramsW, paramsWV);

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                output.add(0, makeItem(s));
            }
        }

        return output;
    }

    private Item makeItem(String[] s){
        try {
            return new Item(s[1], s[0],
                    new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(s[2]),
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

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                Category c = new Category(Integer.valueOf(s[0]), s[1], true);
                cats.add(c);
                catsMap.put(c.getID(), c);
            }
        }

        int[][] relations = cDao.loadRelations();
        Map<Integer, List<Category>> relMap = new Hashtable<>(); //catId, SonLIST
        for(int i = 0; i < relations.length; i++){
            catsMap.get(relations[i][0]).setChildFromDB(catsMap.get(relations[i][1]));
            catsMap.get(relations[i][1]).setParentFromDB(catsMap.get(relations[i][0]));
        }


        return cats;
    }

    public List<Discount> loadDiscounts(){
        List<String[]> sFromDB = load("Discounts", null, null);
        List<Discount> output = new LinkedList<>();

        if(sFromDB != null) {
            for (String[] s : sFromDB) {
                int id = Integer.valueOf(s[0]);
                String pid = s[1];
                try {
                    Date startd = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(s[2]);
                    Date endd = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(s[3]);
                    int amount = Integer.valueOf(s[4]);
                    Type t = Type.PERCENT;
                    if (s[4].equals("1"))
                        t = Type.FIXED;

                    output.add(new Discount(pid, id, startd, endd, amount, t, true));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return output;
    }

}

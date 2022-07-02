package StockModule.BusinessLogicLayer;

import DAL.Stock_Suppliers.DAOS.StockObjects.ProductDao;
import SuppliersModule.DomainLayer.OrderController;
import java.util.*;

public class StockController {
    private static StockController sc = null;
    private int purchasesCounter;
    private HashMap<Integer,Category> categories;
    private int categoriesCounter;
    private HashMap<Integer, Discount> discounts;
    private int discountsCounter;
    private ProductDao pDao;

    private StockController(){
        pDao = new ProductDao();
        purchasesCounter = 0;
        categories = new HashMap<>();
        categoriesCounter = 0;
        discounts = new HashMap<>();
        discountsCounter = 0;

        OrderController.getInstance();

//        List<Category> categoriesList = pDao.loadCategories();
//        if (categoriesList != null) {
//            for (Category c : categoriesList) {
//                categories.put(c.getID(), c);
//                categoriesCounter++;
//            }
//        }

        List<Discount> discountList = pDao.loadDiscounts();
        if(discountList != null) {
            for (Discount d : discountList) {
                discounts.put(d.getDiscountID(), d);
                discountsCounter++;
            }
        }
    }

    public static StockController getInstance(){
        if (sc == null)
            sc = new StockController();
        return sc;
    }

    public int getQuantityForOrder(String ID, int days){
        Product p = getProduct(ID);
        int demand = p.getDemand();
        return (demand / 7) * days;
    }

    public HashMap<String, Product> getProductsInStock(){
        //Requirement 2
        HashMap<String, Product> productMap = new HashMap<>();
        for (Product p : pDao.getAllProducts()) {
            productMap.put(p.getID(), p);
        }
        return productMap;
    }


    public HashMap<Integer, Discount> getCurrentDiscounts(){
        //Requirement 4
        //return new ArrayList<>(discounts);
        return discounts;
    }

    public HashMap<Integer, Category> getCategories(){
        //Requirement 5
        return new HashMap<>(categories);
    }

    public List<Item> getStockReport(){
        //Requirement 6
        List<Item> output = new ArrayList<>();
        for (Product product : pDao.getAllProducts())
        {
            output.addAll(product.getItems());
        }
        return output;
    }

    public List<Item> getStockReportByCategory(int CategoryID){
        //Requirement 7
        List<Item> output = new ArrayList<>();
        for (Product product : pDao.getAllProducts())
        {
            if(isAncestorOf(product.getCategoryID(),CategoryID))
            {
                output.addAll(product.getItems());
            }
        }
        return output;
    }

    public List<Item> getDefectedItemsReport(){
        //Requirement 8+9
        List<Item> output = new ArrayList<>();
        for (Product product : pDao.getAllProducts())
        {
            output.addAll(product.getDefectedItems());
        }
        return output;
    }

    public List<Item> getExpiredItemsReport() {
        List<Item> output = new ArrayList<>();

        for (Product product : pDao.getAllProducts())
        {
            output.addAll(product.getExpiredItems());
        }
        return output;
    }

    public void insertNewProduct(String productName, String productManufacturer, int amountToNotify, int categoryID, int demand){
        new Product(productName, productManufacturer, amountToNotify, categoryID, demand);
    }

    public void setSubCategory(int subCategoryID,int parentID){
        Category subCategory = categories.get(subCategoryID);
        Category parent = categories.get(parentID);
        subCategory.setAsParent(parent);
    }

    public void insertNewItem(String productID, String location, Date expireDate, boolean isDefect, int amount){
        getProduct(productID).addItem(location, expireDate, isDefect, amount);
    }

    public void reduceItemAmount(String productID, int itemID, int amountToReduce) throws Exception
    {
        if (getProduct(productID).reduceItemAmount(itemID, amountToReduce)){
            OrderController.getInstance().orderShortage(productID);
        }
    }

    public void insertNewCategory(String categoryName){
        Category c = new Category(categoriesCounter,categoryName);
        categories.put(c.getID(),c);
        categoriesCounter++;
    }

    public void insertNewDiscount(String productID, Date startDate, Date endDate, int amount, Type t){
        Discount d = new Discount(productID, discountsCounter, startDate, endDate, amount, t);
        discounts.put(discountsCounter, d);
        discountsCounter++;
    }

    public void deleteProduct(String productID){
        pDao.delete(getProduct(productID));
    }

    public void deleteCategory(int categoryID){
        categories.remove(categoryID);
    }

    public void deleteDiscount(int discountID){
        discounts.remove(discountID);
    }

    public void deleteItem(String productID,int itemID) throws Exception
    {
        getProduct(productID).deleteItem(itemID);
    }

    public boolean isAncestorOf(int childCategoryID,int parentCategoryID)
    {
        Category child = categories.get(childCategoryID);
        Category parent = categories.get(parentCategoryID);

        if(child == parent)
            return true;

        if(child.getParentCategory()==null)
            return false;

        return isAncestorOf(child.getParentCategory().getID(), parentCategoryID);

    }

    private Product getProduct(String pid){
        return pDao.getProduct(pid.replaceAll(" ", "_"));
    }

    //todo : add dao
    public boolean updateProductAttribute(String productID, int Attribute, Object Value) {
        return getProduct(productID).updateAttributes(Attribute, Value);
    }

    public boolean updateCategoryName(String categoryID,String name) {
        return categories.get(categoryID).setName(name);
    }

    public boolean updateItemAttribute(String productID,int ItemID, int Attribute, Object Value) {
        return getProduct(productID).updateItemAttribute(ItemID,Attribute, Value);
    }
}

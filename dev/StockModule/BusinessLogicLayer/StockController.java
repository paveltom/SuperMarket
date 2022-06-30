package StockModule.BusinessLogicLayer;

import DAL.DAOS.StockObjects.ProductDao;
import SuppliersModule.DomainLayer.OrderController;

import java.util.*;

public class StockController {
    private HashMap<String,Product> products;
    private HashMap<Integer,Purchase> purchases;
    private int purchasesCounter;
    private HashMap<Integer,Category> categories;
    private int categoriesCounter;
    private HashMap<String, Discount> discounts;
    private int discountsCounter;
    private ProductDao pDao;

    StockController(){
        pDao = new ProductDao();
        products = new HashMap<>();
        purchases = new HashMap<>();
        purchasesCounter = 0;
        categories = new HashMap<>();
        categoriesCounter = 0;
        discounts = new HashMap<>();
        discountsCounter = 0;
        OrderController.getInstance().registerStock(this);
    }

    public int getQuantityForOrder(String ID,int days){
        Product p = products.get(ID);
        int demand = p.getDemand();
        return (demand/7)*days;
    }

    public HashMap<String,Product> getProductsInStock(){
        //Requirement 2
        return new HashMap<>(products);
    }

    public HashMap<Integer,Purchase> getPurchasesHistoryReport(){
        //Requirement 3
        return new HashMap<Integer,Purchase>(purchases);
    }

    public HashMap<String, Discount> getCurrentDiscounts(){
        //Requirement 4
        //return new ArrayList<>(discounts);
        return discounts;
    }

    public HashMap<Integer,Category> getCategories(){
        //Requirement 5
        return new HashMap<>(categories);
    }

    public List<Item> getStockReport(){
        //Requirement 6
        List<Item> output = new ArrayList<>();
        for (String s : products.keySet())
        {
            Product p = products.get(s);
            output.addAll(p.getItems());
        }
        return output;
    }

    public List<Item> getStockReportByCategory(int CategoryID){
        //Requirement 7
        List<Item> output = new ArrayList<>();
        for (String s : products.keySet())
        {
            Product p = products.get(s);
            if(isAncestorOf(p.getCategoryID(),CategoryID))
            {
                output.addAll(p.getItems());
            }
        }
        return output;
    }

    public List<Item> getDefectedItemsReport(){
        //Requirement 8+9
        List<Item> output = new ArrayList<>();
        for (String pID : products.keySet())
        {
            Product p = getProduct(pID);
            output.addAll(p.getDefectedItems());
        }
        return output;
    }

    public List<Item> getExpiredItemsReport() {
        List<Item> output = new ArrayList<>();

        for (String pID : products.keySet())
        {
            Product p = getProduct(pID);
            output.addAll(p.getExpiredItems());
        }
        return output;
    }

    public void insertNewProduct(String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        new Product(productName, productManufacturer, categoryID, supplyTime, demand);
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
        getProduct(productID).reduceItemAmount(itemID, amountToReduce);
    }

    public void insertNewCategory(String categoryName){
        Category c = new Category(categoriesCounter,categoryName);
        categories.put(c.getID(),c);
        categoriesCounter++;
    }

    public void insertNewDiscount(String productID, Date startDate, Date endDate, int amount, Type t){
        Discount d = new Discount(productID, discountsCounter, startDate, endDate, amount, t);
        discounts.put(d.getProductID(),d);
        discountsCounter++;
    }

    public void insertNewPurchase(Date purchaseDate, Map<String, Map<Integer, Integer>> products){
        Purchase p = new Purchase(purchasesCounter, purchaseDate, products);
        purchases.put(p.getID(),p);
        purchasesCounter++;
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

    public void deletePurchase(int purchaseID){
        purchases.remove(purchaseID);
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
        return pDao.getProduct(pid);
    }
}

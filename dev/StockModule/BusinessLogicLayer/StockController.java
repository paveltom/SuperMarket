package StockModule.BusinessLogicLayer;

import StockModule.ServiceLayer.Response;

import java.util.*;

public class StockController {
    private HashMap<String,Product> products;
    private HashMap<Integer,Purchase> purchases;
    private int purchasesCounter;
    private HashMap<Integer,Category> categories;
    private int categoriesCounter;
    private HashMap<Integer,Discount> discounts;
    private int discountsCounter;

    StockController(){
        products = new HashMap<>();
        purchases = new HashMap<>();
        purchasesCounter = 0;
        categories = new HashMap<>();
        categoriesCounter = 0;
        discounts = new HashMap<>();
        discountsCounter = 0;
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

    public HashMap<Integer,Discount> getCurrentDiscounts(){
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
        for (String s : products.keySet())
        {
            Product p = products.get(s);
            output.addAll(p.getDefectedItems());
        }
        return output;
    }

    public List<Item> getExpiredItemsReport() {
        List<Item> output = new ArrayList<>();

        for (String s : products.keySet())
        {
            Product p = products.get(s);
            output.addAll(p.getExpiredItems());
        }
        return output;
    }

    public void insertNewProduct(String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        Product p = new Product(productName, productManufacturer, categoryID, supplyTime, demand);
        products.put(p.getID(),p);
    }

    public void setSubCategory(int subCategoryID,int parentID){
        Category subCategory = categories.get(subCategoryID);
        Category parent = categories.get(parentID);
        subCategory.setAsParent(parent);
    }

    public void insertNewItem(int productID, String location, Date expireDate, boolean isDefect, int amount){
        products.get(productID).addItem(location, expireDate, isDefect, amount);
    }

    public void reduceItemAmount(int productID, int itemID, int amountToReduce) throws Exception
    {
        products.get(productID).reduceItemAmount(itemID, amountToReduce);
    }

    public void insertNewCategory(String categoryName){
        Category c = new Category(categoriesCounter,categoryName);
        categories.put(c.getID(),c);
        categoriesCounter++;
    }

    public void insertNewDiscount(int productID, Date startDate, Date endDate, int amount, Type t){
        Discount d = new Discount(discountsCounter, productID, startDate, endDate, amount, t);
        discounts.put(d.getProductID(),d);
        discountsCounter++;
    }

    public void insertNewPurchase(Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        Purchase p = new Purchase(purchasesCounter, purchaseDate, products);
        purchases.put(p.getID(),p);
        purchasesCounter++;
    }

    public void deleteProduct(int productID){
        products.remove(productID);
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

    public void deleteItem(int productID,int itemID) throws Exception
    {
        products.get(productID).deleteItem(itemID);
    }

    public boolean isAncestorOf(int childCategoryID,int parentCategoryID) {
        Category child = categories.get(childCategoryID);
        Category parent = categories.get(parentCategoryID);

        if (child == parent)
            return true;

        if (child.getParentCategory() == null)
            return false;

        return isAncestorOf(child.getParentCategory().getID(), parentCategoryID);
    }

    public boolean updateProductAttribute(String productID, int Attribute, Object Value) {
        return products.get(productID).updateAttributes(Attribute, Value);
    }

    public boolean updateCategoryName(String categoryID,String name) {
        return categories.get(categoryID).setName(name);
    }

    public boolean updateItemAttribute(String productID,int ItemID, int Attribute, Object Value) {
        return products.get(productID).updateItemAttribute(ItemID,Attribute, Value);
    }

}

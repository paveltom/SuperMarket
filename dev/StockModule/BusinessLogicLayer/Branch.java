package StockModule.BusinessLogicLayer;

import StockModule.ServiceLayer.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Branch {
    private int branchID;
    private String name;
    private StockController stockController;



    Branch(int _branchID,String _name){
        branchID = _branchID;
        name = _name;
        stockController = new StockController();
    }

    public String toString(){
        return "Branch ID : " + branchID + " , Branch name : " + name + "\n";
    }

    public HashMap<String,Product> getProductsInStock(){
        return stockController.getProductsInStock();
    }

    public HashMap<Integer,Purchase> getPurchasesHistoryReport(){
        //Requirement 3
        return stockController.getPurchasesHistoryReport();
    }

    public HashMap<Integer,Discount> getCurrentDiscounts(){
        //Requirement 4
        return stockController.getCurrentDiscounts();
    }

    public HashMap<Integer,Category> getCategories(){
        //Requirement 5
        return stockController.getCategories();
    }

    public List<Item> getStockReport(){
        //Requirement 6
        return stockController.getStockReport();
    }

    public List<Item> getStockReportByCategory(int CategoryID){
        //Requirement 7
        return stockController.getStockReportByCategory(CategoryID);
    }

    public List<Item> getDefectedItemsReport(){
        //Requirement 8+9
        return stockController.getDefectedItemsReport();
    }

    public List<Item> getExpiredItemsReport() {
        return stockController.getExpiredItemsReport();
    }

    public void insertNewProduct(String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        stockController.insertNewProduct(productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public void setSubCategory(int subCategoryID,int parentID){
        stockController.setSubCategory(subCategoryID,parentID);
    }

    public void insertNewItem(int productID, String location, Date expireDate, boolean isDefect, int amount)
    {
        stockController.insertNewItem(productID, location, expireDate, isDefect, amount);
    }
    public void reduceItemAmount(int productID, int itemID, int amountToReduce) throws Exception
    {
        stockController.reduceItemAmount(productID, itemID, amountToReduce);
    }

    public void insertNewCategory(String categoryName){
        stockController.insertNewCategory(categoryName);
    }

    public void insertNewDiscount(int productID, Date startDate, Date endDate, int amount, Type t){
        stockController.insertNewDiscount(productID, startDate, endDate, amount, t);
    }

    public void insertNewPurchase(Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        stockController.insertNewPurchase(purchaseDate, products);
    }

    public void deleteProduct(int productID){
        stockController.deleteProduct(productID);
    }

    public void deleteCategory(int categoryID){
        stockController.deleteCategory(categoryID);
    }

    public void deleteDiscount(int discountID){
        stockController.deleteDiscount(discountID);
    }

    public void deletePurchase(int purchaseID){
        stockController.deletePurchase(purchaseID);
    }

    public void deleteItem(int productID,int itemID) throws Exception
    {
        stockController.deleteItem(productID, itemID);
    }

    public boolean updateProductAttribute(String productID, int Attribute, Object Value) {
        return stockController.updateProductAttribute(productID, Attribute, Value);
    }

    public boolean updateCategoryName(String categoryID,String name) {
        return stockController.updateCategoryName(categoryID, name);
    }


}

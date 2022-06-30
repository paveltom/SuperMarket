package StockModule.ServiceLayer;

import StockModule.BusinessLogicLayer.*;

import java.util.*;

public class Service
{
    StockControllerService scs;

    public Service(){
        scs = new StockControllerService();
    }

    public Response addNewBranch(String name){
        return scs.addNewBranch(name);
    }
    public Response deleteBranch(int branchID){
        return scs.deleteBranch(branchID);
    }

    public Response setSubCategory(int branchID,int subCategoryID,int parentID){
        return scs.setSubCategory(branchID,subCategoryID,parentID);
    }

    public  ResponseT<List<Branch>> getBranches(){
        return scs.getBranches();
    }

    public ResponseT<List<Product>> getProductsInStock(int branchID){
        //Requirement 2
        return scs.getProductsInStock(branchID);
    }



    public ResponseT<List<Purchase>> getPurchasesHistoryReport(int branchID){
        //Requirement 3
        return scs.getPurchasesHistoryReport(branchID);
    }

    public ResponseT<List<Discount>> getCurrentDiscounts(int branchID){
        //Requirement 4
        return scs.getCurrentDiscounts(branchID);
    }

    public ResponseT<List<Category>> getCategories(int branchID)
    {
        //Requirement 5
        return scs.getCategories(branchID);
    }

    public ResponseT<List<Item>> getStockReport(int branchID){
        //Requirement 6
        return scs.getStockReport(branchID);
    }

    public ResponseT<List<Item>> getStockReportByCategory(int branchID, int categoryID){
        //Requirement 7
        return scs.getStockReportByCategory(branchID,categoryID);
    }

    public ResponseT<List<Item>> getDefectedProductsReport(int branchID){
        //Requirement 8+9
        return scs.getDefectedItemsReport(branchID);
    }

    public ResponseT<List<Item>> getExpiredProductsReport(int branchID) {
        return scs.getExpiredItemsReport(branchID);
    }

    public Response insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        return scs.insertNewProduct(branchID,productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public Response insertNewItem(int branchID, String productID, String location, Date expireDate, boolean isUsable, int amount){
        return scs.insertNewItem(branchID, productID, location, expireDate, isUsable, amount);

    }

    public Response reduceItemAmount(int branchID, String productID,int itemID,int amountToReduce)
    {
        return scs.reduceItemAmount(branchID, productID, itemID, amountToReduce);
    }

    public Response insertNewCategory(int branchID,String categoryName){
        return scs.insertNewCategory(branchID,categoryName);
    }

    public Response insertNewDiscount(int branchID, String productID, Date startDate, Date endDate, int amount, Type t){
        return scs.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
    }

    public Response insertNewPurchase(int branchID, Date purchaseDate, Map<String, Map<Integer, Integer>> products){
        return scs.insertNewPurchase(branchID, purchaseDate, products);
    }

    public Response deleteProduct(int branchID, int productID){
        return scs.deleteProduct(branchID, productID);
    }

    public Response deleteCategory(int branchID, int categoryID){
        return scs.deleteCategory(branchID, categoryID);
    }

    public Response deleteDiscount(int branchID, int discountID){
        return scs.deleteDiscount(branchID, discountID);
    }

    public Response deletePurchase(int branchID, int purchaseID){
        return scs.deletePurchase(branchID, purchaseID);
    }

    public Response deleteItem(int branchID, int productID,int itemID){
        return scs.deleteItem(branchID,productID,itemID);
    }

    public void LoadDefaultData()
    {
        addNewBranch("Shop-Eilat");
        addNewBranch("Shop-Netivot");

        insertNewCategory(0, "Electricity");
        insertNewCategory(0, "Milk");
        insertNewCategory(0, "Drinks");

        insertNewCategory(1, "Electricity");
        insertNewCategory(1, "Milk");
        insertNewCategory(1, "Drinks");

        insertNewProduct(0, "Dark Chocolate 60%", "Strauss", 1, new Date(2020,2,2), 40);
        insertNewProduct(0, "Milk Chocolate", "Strauss", 1, new Date(2001, 1, 25), 40);
        insertNewProduct(0, "Batteries AA", "GP Ultra", 0, new Date(2004, 5, 14), 65);
        insertNewProduct(0, "Orange juice", "Primor", 2, new Date(2014, 7, 30), 65);

        insertNewProduct(1, "0", "", 1, new Date(2020,2,2), 40);
        insertNewProduct(1, "Milk Chocolate", "Strauss", 1, new Date(2001, 1, 25), 40);
        insertNewProduct(1, "Batteries AA", "GP Ultra", 0, new Date(2004, 5, 14), 65);
        insertNewProduct(1, "Orange juice", "Primor", 2, new Date(2014, 7, 30), 65);

        insertNewItem(0, "0", "L5A4", new Date(2021, 4, 5), false, 30);
        insertNewItem(0, "0", "L2A17", new Date(2022, 4, 5), true, 42);
        insertNewItem(0, "1", "L6A8", new Date(2022, 4, 15), true, 50);
        insertNewItem(0, "2", "L1A2", new Date(2022, 2, 3), true, 130);
        insertNewItem(0, "3", "L9A4", new Date(2022, 1, 15), false, 280);


        insertNewItem(1, "0", "L5A4", new Date(2021, 4, 5), false, 30);
        insertNewItem(1, "0", "L2A17", new Date(2022, 4, 5), true, 42);
        insertNewItem(1, "1", "L6A8", new Date(2022, 4, 15), true, 50);
        insertNewItem(1, "2", "L1A2", new Date(2022, 2, 3), true, 130);
        insertNewItem(1, "3", "L9A4", new Date(2022, 1, 15), false, 280);

        insertNewDiscount(0, "3", new Date(2022, 4, 3), new Date(2022, 5, 3), 30, Type.PERCENT);
        insertNewDiscount(1, "3", new Date(2022, 4, 3), new Date(2022, 5, 3), 30, Type.PERCENT);

        Map<String, Map<Integer, Integer>> products = (Map<String, Map<Integer, Integer>>) new HashMap<>().put("2", new HashMap<>().put(30, 25));
        insertNewPurchase(0, new Date(2022, 4, 25), products);
        insertNewPurchase(1, new Date(2022, 4, 25), products);
    }
}

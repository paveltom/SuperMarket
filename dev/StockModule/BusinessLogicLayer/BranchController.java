package StockModule.BusinessLogicLayer;

import java.util.*;

public class BranchController{
    private List<Branch> branches;
    private int counterBranches;

    public BranchController(){
        branches = new ArrayList<>();
        counterBranches = 0;
    }

    public List<Branch> getBranches(){
        return new ArrayList<>(branches);
    }

    public void addNewBranch(String name){
        branches.add(new Branch(counterBranches,name));
        counterBranches++;
    }

    public void deleteBranch(int branchID){
        branches.remove(branchID);

    }

    public void setSubCategory(int branchID,int subCategoryID,int parentID){
        branches.get(branchID).setSubCategory(subCategoryID,parentID);
    }

    public HashMap<String,Product> getProductsInStock(int branchID){
        //Requirement 2
        return branches.get(branchID).getProductsInStock();
    }

    public HashMap<Integer,Purchase> getPurchasesHistoryReport(int branchID){
        //Requirement 3
        return branches.get(branchID).getPurchasesHistoryReport();
    }

    public HashMap<Integer,Discount> getCurrentDiscounts(int branchID){
        //Requirement 4
        return branches.get(branchID).getCurrentDiscounts();
    }

    public HashMap<Integer,Category> getCategories(int branchID){
        //Requirement 5
        return branches.get(branchID).getCategories();
    }

    public List<Item> getStockReport(int branchID){
        //Requirement 6
        return branches.get(branchID).getStockReport();
    }

    public List<Item> getStockReportByCategory(int branchID, int CategoryID){
        //Requirement 7
        return branches.get(branchID).getStockReportByCategory(CategoryID);
    }

    public List<Item> getDefectedItemsReport(int branchID){
        //Requirement 8+9
        return branches.get(branchID).getDefectedItemsReport();
    }

    public List<Item> getExpiredItemsReport(int branchID) {
        return branches.get(branchID).getExpiredItemsReport();
    }

    public void insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        branches.get(branchID).insertNewProduct(productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public void insertNewCategory(int branchID,String categoryName){
        branches.get(branchID).insertNewCategory(categoryName);
    }

    public void insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        branches.get(branchID).insertNewDiscount(productID, startDate, endDate, amount, t);
    }

    public void insertNewPurchase(int branchID, Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        branches.get(branchID).insertNewPurchase(purchaseDate, products);
    }

    public void deleteProduct(int branchID, int productID){
        branches.get(branchID).deleteProduct(productID);
    }

    public void insertNewItem(int branchID, int productID, String location, Date expireDate, boolean isDefect, int amount){
        branches.get(branchID).insertNewItem(productID, location, expireDate, isDefect, amount);
    }
    public void reduceItemAmount(int branchID, int productID, int itemID, int amountToReduce) throws Exception
    {
        branches.get(branchID).reduceItemAmount(productID, itemID, amountToReduce);
    }

    public void deleteCategory(int branchID, int categoryID){
        branches.get(branchID).deleteCategory(categoryID);
    }

    public void deleteDiscount(int branchID, int discountID){
        branches.get(branchID).deleteDiscount(discountID);
    }

    public void deletePurchase(int branchID, int purchaseID){
        branches.get(branchID).deletePurchase(purchaseID);
    }

    public void deleteItem(int branchID, int productID,int itemID) throws Exception {
        branches.get(branchID).deleteItem(productID, itemID);
    }

    public boolean updateProductAttribute(int branchID, String productID, int Attribute, Object Value) {
        return branches.get(branchID).updateProductAttribute(productID, Attribute, Value);
    }
}

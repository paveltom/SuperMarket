package StockModule.ServiceLayer;

import StockModule.BusinessLogicLayer.*;

import java.util.*;

public class Service
{
    StockControllerService scs;

    public Service(){
        scs = new StockControllerService();
    }

    /*
    public Response addNewBranch(String name){
        return scs.addNewBranch(name);
    }

    public Response deleteBranch(int ){
        return scs.deleteBranch();
    }
    */


    public Response setSubCategory(int subCategoryID,int parentID){
        return scs.setSubCategory(subCategoryID,parentID);
    }

    /*public  ResponseT<List<Branch>> getBranches(){
        return scs.getBranches();
    }
    */
    public ResponseT<List<Product>> getProductsInStock(){
        //Requirement 2
        return scs.getProductsInStock();
    }



    public ResponseT<List<Purchase>> getPurchasesHistoryReport(){
        //Requirement 3
        return scs.getPurchasesHistoryReport();
    }

    public ResponseT<List<Discount>> getCurrentDiscounts(){
        //Requirement 4
        return scs.getCurrentDiscounts();
    }

    public ResponseT<List<Category>> getCategories()
    {
        //Requirement 5
        return scs.getCategories();
    }

    public ResponseT<List<Item>> getStockReport(){
        //Requirement 6
        return scs.getStockReport();
    }

    public ResponseT<List<Item>> getStockReportByCategory( int categoryID){
        //Requirement 7
        return scs.getStockReportByCategory(categoryID);
    }

    public ResponseT<List<Item>> getDefectedProductsReport(){
        //Requirement 8+9
        return scs.getDefectedItemsReport();
    }

    public ResponseT<List<Item>> getExpiredProductsReport() {
        return scs.getExpiredItemsReport();
    }

    public Response insertNewProduct( String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        return scs.insertNewProduct(productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public Response insertNewItem( int productID, String location, Date expireDate, boolean isUsable, int amount){
        return scs.insertNewItem(productID,location,expireDate,isUsable,amount);

    }

    public Response reduceItemAmount(int productID,int itemID,int amountToReduce)
    {
        return scs.reduceItemAmount(productID, itemID, amountToReduce);
    }

    public Response insertNewCategory(String categoryName){
        return scs.insertNewCategory(categoryName);
    }

    public Response insertNewDiscount( int productID, Date startDate, Date endDate, int amount, Type t){
        return scs.insertNewDiscount(productID, startDate, endDate, amount, t);
    }

    public Response insertNewPurchase( Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        return scs.insertNewPurchase(purchaseDate, products);
    }

    public Response deleteProduct( int productID){
        return scs.deleteProduct(productID);
    }

    public Response deleteCategory( int categoryID){
        return scs.deleteCategory(categoryID);
    }

    public Response deleteDiscount( int discountID){
        return scs.deleteDiscount(discountID);
    }

    public Response deletePurchase( int purchaseID){
        return scs.deletePurchase(purchaseID);
    }

    public Response deleteItem( int productID,int itemID){
        return scs.deleteItem(productID,itemID);
    }

    public Response updateProductAttribute(String productID, int Attribute, Object Value) {
        return scs.updateProductAttribute(productID, Attribute, Value);
    }

    public Response updateCategoryName(String categoryID, String name) {
        return scs.updateCategoryName(categoryID, name);
    }

    public Response updateItemAttribute(String productID,int ItemID, int Attribute, Object Value) {
        return scs.updateItemAttribute(productID,ItemID,Attribute, Value);
    }


    public void LoadDefaultData()
    {
        /*
        addNewBranch("Shop-Eilat");
        addNewBranch("Shop-Netivot");
        */
        insertNewCategory( "Electricity");
        insertNewCategory( "Milk");
        insertNewCategory( "Drinks");

        insertNewCategory( "Electricity");
        insertNewCategory( "Milk");
        insertNewCategory( "Drinks");

        insertNewProduct( "Dark Chocolate 60%", "Strauss", 1, new Date(2020,2,2), 40);
        insertNewProduct( "Milk Chocolate", "Strauss", 1, new Date(2001, 1, 25), 40);
        insertNewProduct( "Batteries AA", "GP Ultra", 0, new Date(2004, 5, 14), 65);
        insertNewProduct( "Orange juice", "Primor", 2, new Date(2014, 7, 30), 65);

        insertNewProduct( "Dark Chocolate 60%", "Strauss", 1, new Date(2020,2,2), 40);
        insertNewProduct( "Milk Chocolate", "Strauss", 1, new Date(2001, 1, 25), 40);
        insertNewProduct( "Batteries AA", "GP Ultra", 0, new Date(2004, 5, 14), 65);
        insertNewProduct( "Orange juice", "Primor", 2, new Date(2014, 7, 30), 65);

        insertNewItem( 0, "L5A4", new Date(2021, 4, 5), false, 30);
        insertNewItem( 0, "L2A17", new Date(2022, 4, 5), true, 42);
        insertNewItem( 1, "L6A8", new Date(2022, 4, 15), true, 50);
        insertNewItem( 2, "L1A2", new Date(2022, 2, 3), true, 130);
        insertNewItem( 3, "L9A4", new Date(2022, 1, 15), false, 280);


        insertNewItem( 0, "L5A4", new Date(2021, 4, 5), false, 30);
        insertNewItem( 0, "L2A17", new Date(2022, 4, 5), true, 42);
        insertNewItem( 1, "L6A8", new Date(2022, 4, 15), true, 50);
        insertNewItem( 2, "L1A2", new Date(2022, 2, 3), true, 130);
        insertNewItem( 3, "L9A4", new Date(2022, 1, 15), false, 280);

        insertNewDiscount( 3, new Date(2022, 4, 3), new Date(2022, 5, 3), 30, Type.PERCENT);
        insertNewDiscount( 3, new Date(2022, 4, 3), new Date(2022, 5, 3), 30, Type.PERCENT);

        Map<Integer, Map<Integer, Integer>> products = (Map<Integer, Map<Integer, Integer>>) new HashMap<>().put(2, new HashMap<>().put(30, 25));
        insertNewPurchase( new Date(2022, 4, 25), products);
        insertNewPurchase( new Date(2022, 4, 25), products);
    }
}

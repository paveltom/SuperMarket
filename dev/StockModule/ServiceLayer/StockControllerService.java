package StockModule.ServiceLayer;

import StockModule.BusinessLogicLayer.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class StockControllerService
{
    StockController sc;

    StockControllerService()
    {
        sc = StockController.getInstance();
    }


    public Response setSubCategory(int subCategoryID,int parentID){
        try
        {
            sc.setSubCategory(subCategoryID,parentID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<List<Product>> getProductsInStock()
    {
        //Requirement 2
        try
        {
            return ResponseT.FromValue(sc.getProductsInStock());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Discount>> getCurrentDiscounts()
    {
        //Requirement 4
        try{
            return ResponseT.FromValue(sc.getCurrentDiscounts());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }


    }

    public ResponseT<List<Category>> getCategories()
    {
        //Requirement 5
        try
        {
            return ResponseT.FromValue(sc.getCategories());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getStockReport()
    {
        //Requirement 6
        try{
            return ResponseT.FromValue(sc.getStockReport());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getStockReportByCategory(int categoryID)
    {
        //Requirement 7
        try{
            return ResponseT.FromValue(sc.getStockReportByCategory(categoryID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }

    }

    public ResponseT<List<Item>> getDefectedItemsReport()
    {
        //Requirement 8+9
        try
        {
            return ResponseT.FromValue(sc.getDefectedItemsReport());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getExpiredItemsReport() {
        try
        {
            return ResponseT.FromValue(sc.getExpiredItemsReport());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response insertNewProduct(String productName, String productManufacturer,double weight,int amountToNotify, int categoryID, int demand)
    {
        try
        {
            sc.insertNewProduct(productName, productManufacturer,weight, amountToNotify, categoryID, demand);
            return new Response();
        } catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    // todo
    public Response insertNewItem(String productID, String location, Date expireDate, boolean isUsable, int amount)
    {
        try
        {
            sc.insertNewItem(productID,location,expireDate,isUsable,amount);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response reduceItemAmount(String productID,int itemID,int amountToReduce){
        try
        {
            sc.reduceItemAmount(productID,itemID,amountToReduce);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response insertNewCategory(String categoryName){
        try
        {
            sc.insertNewCategory(categoryName);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response insertNewDiscount(String productID, Date startDate, Date endDate, int amount, Type t){
        try
        {
            sc.insertNewDiscount(productID, startDate, endDate, amount, t);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }

    }

    /*
    public Response insertNewPurchase(Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        try
        {
            sc.insertNewPurchase(purchaseDate, products);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    */

    public Response deleteProduct(String productID){
        try
        {
            sc.deleteProduct(productID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response deleteCategory(int categoryID) {
        try
        {
            sc.deleteCategory(categoryID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }


    public Response deleteDiscount(int discountID){
        try
        {
            sc.deleteDiscount(discountID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    /*
    public Response deletePurchase(int purchaseID){
        try{
            sc.deletePurchase(purchaseID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    */

    public Response deleteItem(String productID,int itemID){
        try{
            sc.deleteItem(productID,itemID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    /*
    public ResponseT<List<Branch>> getBranches(){
        try{
            return ResponseT.FromValue(sc.getBranches());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

     */

    public Response updateProductAttribute(String productID, int Attribute, Object Value) {
        return ResponseT.FromValue(sc.updateProductAttribute(productID, Attribute, Value));
    }

    public Response updateCategoryName(String categoryID, String name) {
        return ResponseT.FromValue(sc.updateCategoryName(categoryID, name));
    }

    public Response updateItemAttribute(String productID,int ItemID, int Attribute, Object Value) {
        return ResponseT.FromValue(sc.updateItemAttribute(productID,ItemID,Attribute, Value));
    }

}

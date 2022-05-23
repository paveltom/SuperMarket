package com.company.StockModule.ServiceLayer;

import com.company.StockModule.BusinessLogicLayer.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class StockControllerService
{
    BranchController bc;

    StockControllerService()
    {
        bc = new BranchController();
    }

    public Response addNewBranch(String name)
    {
        try
        {
            bc.addNewBranch(name);
            return new Response();
        } catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response deleteBranch(int branchID)
    {
        try
        {
            bc.deleteBranch(branchID);
            return new Response();
        } catch (Exception e)
        {
            return new Response(e.getMessage());
        }

    }
    public Response setSubCategory(int branchID,int subCategoryID,int parentID){
        try
        {
            bc.setSubCategory(branchID,subCategoryID,parentID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<List<Product>> getProductsInStock(int branchID)
    {
        //Requirement 2
        try
        {
            return ResponseT.FromValue(bc.getProductsInStock(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Purchase>> getPurchasesHistoryReport(int branchID)
    {
        //Requirement 3
        try
        {
            return ResponseT.FromValue(bc.getPurchasesHistoryReport(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Discount>> getCurrentDiscounts(int branchID)
    {
        //Requirement 4
        try{
            return ResponseT.FromValue(bc.getCurrentDiscounts(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }


    }

    public ResponseT<List<Category>> getCategories(int branchID)
    {
        //Requirement 5
        try
        {
            return ResponseT.FromValue(bc.getCategories(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getStockReport(int branchID)
    {
        //Requirement 6
        try{
            return ResponseT.FromValue(bc.getStockReport(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getStockReportByCategory(int branchID, int categoryID)
    {
        //Requirement 7
        try{
            return ResponseT.FromValue(bc.getStockReportByCategory(branchID, categoryID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }

    }

    public ResponseT<List<Item>> getDefectedItemsReport(int branchID)
    {
        //Requirement 8+9
        try
        {
            return ResponseT.FromValue(bc.getDefectedItemsReport(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<Item>> getExpiredItemsReport(int branchID) {
        try
        {
            return ResponseT.FromValue(bc.getExpiredItemsReport(branchID));
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand)
    {
        try
        {
            bc.insertNewProduct(branchID, productName, productManufacturer, categoryID, supplyTime, demand);
            return new Response();
        } catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response insertNewItem(int branchID, int productID, String location, Date expireDate, boolean isUsable, int amount)
    {
        try
        {
            bc.insertNewItem(branchID,productID,location,expireDate,isUsable,amount);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response reduceItemAmount(int branchID, int productID,int itemID,int amountToReduce){
        try
        {
            bc.reduceItemAmount(branchID,productID,itemID,amountToReduce);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response insertNewCategory(int branchID,String categoryName){
        try
        {
            bc.insertNewCategory(branchID, categoryName);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        try
        {
            bc.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }

    }

    public Response insertNewPurchase(int branchID, Date purchaseDate, Map<Integer, Map<Integer, Integer>> products){
        try
        {
            bc.insertNewPurchase(branchID, purchaseDate, products);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response deleteProduct(int branchID, int productID){
        try
        {
            bc.deleteProduct(branchID, productID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response deleteCategory(int branchID, int categoryID) {
        try
        {
            bc.deleteCategory(branchID, categoryID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }


    public Response deleteDiscount(int branchID, int discountID){
        try
        {
            bc.deleteDiscount(branchID, discountID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response deletePurchase(int branchID, int purchaseID){
        try{
            bc.deletePurchase(branchID, purchaseID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    public Response deleteItem(int branchID, int productID,int itemID){
        try{
            bc.deleteItem(branchID, productID,itemID);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<List<Branch>> getBranches(){
        try{
            return ResponseT.FromValue(bc.getBranches());
        }
        catch (Exception e)
        {
            return ResponseT.FromError(e.getMessage());
        }
    }
}

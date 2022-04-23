package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.*;

import java.util.Date;
import java.util.List;

public class StockControllerService
{
    BranchController bc;

    public Response addNewBranch(String name){
        try{
            bc.addNewBranch(name);
            return new Response();
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }

    }

    public ResponseT<List<Product>> getProductsInStock(int branchID){
        //Requirement 2
        return ResponseT.FromValue(bc.getProductsInStock(branchID));
    }

    public ResponseT<List<Purchase>> getPurchasesHistoryReport(int branchID){
        //Requirement 3
        return  ResponseT.FromValue(bc.getPurchasesHistoryReport(branchID));
    }

    public ResponseT<List<Discount>> getCurrentDiscounts(int branchID){
        //Requirement 4
        return ResponseT.FromValue(bc.getCurrentDiscounts(branchID));
    }

    public ResponseT<List<Category>> getCategories(int branchID){
        //Requirement 5
        return ResponseT.FromValue(bc.getCategories(branchID));
    }

    public ResponseT<List<Item>> getStockReport(int branchID){
        //Requirement 6
        return ResponseT.FromValue(bc.getStockReport(branchID));
    }

    public ResponseT<List<Item>> getStockReportByCategory(int branchID, int categoryID){
        //Requirement 7
        return ResponseT.FromValue(bc.getStockReportByCategory(branchID,categoryID));
    }

    public ResponseT<List<Item>> getUnusableItemsReport(int branchID){
        //Requirement 8+9
        return ResponseT.FromValue(bc.getUnusableItemsReport(branchID));
    }

    public Response insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        try
        {
            bc.insertNewProduct(branchID, productName, productManufacturer, categoryID, supplyTime, demand);
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

    //public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    //}

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

}

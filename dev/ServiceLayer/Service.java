package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.*;

import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;

public class Service
{
    StockControllerService scs;

    public Response addNewBranch(String name){
        return scs.addNewBranch(name);
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

    public ResponseT<List<Product>> getUnusableProductsReport(int branchID){
        //Requirement 8+9
        return scs.getUnusableProductsReport(branchID);
    }

    public Response insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        return scs.insertNewProduct(branchID,productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public Response insertNewCategory(int branchID,String categoryName){
        return scs.insertNewCategory(branchID,categoryName);
    }

    public Response insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        return scs.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
    }

    //public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    //}

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
}

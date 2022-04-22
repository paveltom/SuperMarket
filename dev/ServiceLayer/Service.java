package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.*;

import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;

public class Service
{
    StockControllerService scs;

    public Response addNewBranch(String name){
        scs.addNewBranch(name);
    }

    public ResponseT<List<Product>> getProductsInStock(int branchID){
        //Requirement 2
        scs.getProductsInStock(branchID);
    }

    public ResponseT<List<Purchase>> getPurchasesHistoryReport(int branchID){
        //Requirement 3
        scs.getPurchasesHistoryReport(branchID);
    }

    public ResponseT<List<Discount>> getCurrentDiscounts(int branchID){
        //Requirement 4
        scs.getCurrentDiscounts(branchID);
    }

    public ResponseT<List<Category>> getCategories(int branchID)
    {
        //Requirement 5
        scs.getCategories(branchID);
    }

    public ResponseT<List<Item>> getStockReport(int branchID){
        //Requirement 6
        scs.getStockReport(branchID);
    }

    public ResponseT<List<Item>> getStockReportByCategory(int branchID, int categoryID){
        //Requirement 7
        scs.getStockReportByCategory(branchID,categoryID);
    }

    public ResponseT<List<Product>> getUnusableProductsReport(int branchID){
        //Requirement 8+9
        scs.getUnusableProductsReport(branchID);
    }

    public Response insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        scs.insertNewProduct(branchID,productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public Response insertNewCategory(int branchID,String categoryName){
        scs.insertNewCategory(branchID,categoryName);
    }

    public Response insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        scs.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
    }

    //public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    //}

    public Response deleteProduct(int branchID, int productID){
        scs.deleteProduct(branchID, productID);
    }

    public Response deleteCategory(int branchID, int categoryID){
        scs.deleteCategory(branchID, categoryID);
    }

    public Response deleteDiscount(int branchID, int discountID){
        scs.deleteDiscount(branchID, discountID);
    }

    public Response deletePurchase(int branchID, int purchaseID){
        scs.deletePurchase(branchID, purchaseID);
    }
}

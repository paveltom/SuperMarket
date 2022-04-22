package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.Type;

import java.util.Date;

public class Service
{
    StockControllerService scs;

    public void getProductsInStock(int branchID){
        //Requirement 2
        scs.getProductsInStock(branchID);

    }

    public void getPurchasesHistoryReport(int branchID){
        //Requirement 3
        scs.getPurchasesHistoryReport(branchID);
    }

    public void getCurrentDiscounts(int branchID){
        //Requirement 4
        scs.getCurrentDiscounts(branchID);
    }

    public void getCategories(int branchID){
        //Requirement 5
        scs.getCategories(branchID);
    }

    public void getStockReport(int branchID){
        //Requirement 6
        scs.getStockReport(branchID);
    }

    public void getStockReportByCategory(int branchID, int categoryID){
        //Requirement 7
        scs.getStockReportByCategory(branchID,categoryID);
    }

    public void getUnusableProductsReport(int branchID){
        //Requirement 8+9
        scs.getUnusableProductsReport(branchID);
    }

    public void insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        scs.insertNewProduct(branchID,productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public void insertNewCategory(int branchID,String categoryName){
        scs.insertNewCategory(branchID,categoryName);
    }

    public void insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        scs.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
    }

    //public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    //}

    public void deleteProduct(int branchID, int productID){
        scs.deleteProduct(branchID, productID);
    }

    public void deleteCategory(int branchID, int categoryID){
        scs.deleteCategory(branchID, categoryID);
    }

    public void deleteDiscount(int branchID, int discountID){
        scs.deleteDiscount(branchID, discountID);
    }

    public void deletePurchase(int branchID, int purchaseID){
        scs.deletePurchase(branchID, purchaseID);
    }
}

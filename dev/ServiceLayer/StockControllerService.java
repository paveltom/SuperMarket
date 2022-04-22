package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.Branch;
import com.company.BusinessLogicLayer.BranchController;
import com.company.BusinessLogicLayer.Type;

import java.util.Date;

public class StockControllerService
{
    BranchController bc;

    public void addNewBranch(String name){
        bc.addNewBranch(name);
    }

    public void getProductsInStock(int branchID){
        //Requirement 2
        bc.getProductsInStock(branchID);
    }

    public void getPurchasesHistoryReport(int branchID){
        //Requirement 3
        bc.getPurchasesHistoryReport(branchID);
    }

    public void getCurrentDiscounts(int branchID){
        //Requirement 4
        bc.getCurrentDiscounts(branchID);
    }

    public void getCategories(int branchID){
        //Requirement 5
        bc.getCategories(branchID);
    }

    public void getStockReport(int branchID){
        //Requirement 6
        bc.getStockReport(branchID);
    }

    public void getStockReportByCategory(int branchID, int categoryID){
        //Requirement 7
        bc.getStockReportByCategory(branchID,categoryID);
    }

    public void getUnusableProductsReport(int branchID){
        //Requirement 8+9
        bc.getUnusableProductsReport(branchID);
    }

    public void insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        bc.insertNewProduct(branchID,productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public void insertNewCategory(int branchID,String categoryName){
        bc.insertNewCategory(branchID,categoryName);
    }

    public void insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){
        bc.insertNewDiscount(branchID, productID, startDate, endDate, amount, t);
    }

    //public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    //}

    public void deleteProduct(int branchID, int productID){
        bc.deleteProduct(branchID, productID);
    }

    public void deleteCategory(int branchID, int categoryID){
        bc.deleteCategory(branchID, categoryID);
    }

    public void deleteDiscount(int branchID, int discountID){
        bc.deleteDiscount(branchID, discountID);
    }

    public void deletePurchase(int branchID, int purchaseID){
        bc.deletePurchase(branchID, purchaseID);
    }

}

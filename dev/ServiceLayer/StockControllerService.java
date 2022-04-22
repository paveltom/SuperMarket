package com.company.ServiceLayer;

import com.company.BusinessLogicLayer.BranchController;
import com.company.BusinessLogicLayer.Type;

import java.util.Date;

public class StockControllerService
{
    BranchController bc;


    public void getProductsInStock(int branchID){
        //Requirement 2


    }

    public void getPurchasesHistoryReport(int branchID){
        //Requirement 3

    }

    public void getCurrentDiscounts(int branchID){
        //Requirement 4
    }

    public void getCategories(int branchID){
        //Requirement 5
    }

    public void getStockReport(int branchID){
        //Requirement 6
    }

    public void getStockReportByCategory(int branchID, int CategoryID){
        //Requirement 7
    }

    public void getUnusableProductsReport(int branchID){
        //Requirement 8+9
    }

    public void insertNewProduct(int branchID, String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){

    }

    public void insertNewCategory(int branchID,String categoryName){

    }

    public void insertNewDiscount(int branchID, int productID, Date startDate, Date endDate, int amount, Type t){

    }

    public void insertNewPurchase(int branchID, Date purchaseDate, Map m){

    }

    public void deleteProduct(int branchID, int productID){

    }

    public void deleteCategory(int branchID, int categoryID){

    }

    public void deleteDiscount(int branchID, int discountID){

    }

    public void deletePurchase(int branchID, int purchaseID){

    }

}

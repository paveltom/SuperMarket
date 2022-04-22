package com.company.BusinessLogicLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BranchController{
    private List<Branch> branches;
    private int counterBranches;

    BranchController(){
        branches = new ArrayList<Branch>();
        counterBranches = 0;
    }

    public void addNewBranch(String name){
        branches.add(new Branch(counterBranches,name));
        counterBranches++;
    }

    public void getProductsInStock(int branchID){
        //Requirement 2
        branches.get(branchID).getProductsInStock();
    }

    public void getPurchasesHistoryReport(int branchID){
        //Requirement 3
        branches.get(branchID).getPurchasesHistoryReport();

    }

    public void getCurrentDiscounts(int branchID){
        //Requirement 4
        branches.get(branchID).getCurrentDiscounts();
    }

    public void getCategories(int branchID){
        //Requirement 5
        branches.get(branchID).getCategories();
    }

    public void getStockReport(int branchID){
        //Requirement 6
        branches.get(branchID).getStockReport();
    }

    public void getStockReportByCategory(int branchID, int CategoryID){
        //Requirement 7
        branches.get(branchID).getStockReportByCategory(CategoryID);
    }

    public void getUnusableProductsReport(int branchID){
        //Requirement 8+9
        branches.get(branchID).getUnusableProductsReport();
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

    /*public void insertNewPurchase(int branchID, Date purchaseDate, Map m){
    }*/

    public void deleteProduct(int branchID, int productID){
        branches.get(branchID).deleteProduct(productID);
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








}

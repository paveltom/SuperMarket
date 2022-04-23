package com.company.BusinessLogicLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BranchController{
    private List<Branch> branches;
    private int counterBranches;

    public BranchController(){
        branches = new ArrayList<Branch>();
        counterBranches = 0;
    }

    public void addNewBranch(String name){
        branches.add(new Branch(counterBranches,name));
        counterBranches++;
    }

    public List<Product> getProductsInStock(int branchID){
        //Requirement 2
        return branches.get(branchID).getProductsInStock();
    }

    public List<Purchase> getPurchasesHistoryReport(int branchID){
        //Requirement 3
        return branches.get(branchID).getPurchasesHistoryReport();
    }

    public List<Discount> getCurrentDiscounts(int branchID){
        //Requirement 4
        return branches.get(branchID).getCurrentDiscounts();
    }

    public List<Category> getCategories(int branchID){
        //Requirement 5
        return branches.get(branchID).getCategories();
    }

    public List<Item> getStockReport(int branchID){
        //Requirement 6
        return branches.get(branchID).getStockReport();
    }

    public List<Item> getStockReportByCategory(int branchID, int CategoryID){
        //Requirement 7
        return branches.get(branchID).getStockReportByCategory(CategoryID);
    }

    public List<Item> getUnusableItemsReport(int branchID){
        //Requirement 8+9
        return branches.get(branchID).getUnusableItemsReport();
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

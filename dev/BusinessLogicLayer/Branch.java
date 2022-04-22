package com.company.BusinessLogicLayer;

import java.util.Date;

public class Branch {
    private int branchID;
    private String name;
    private StockController stockController;

    Branch(int _branchID,String _name){
        branchID = _branchID;
        name = _name;
        stockController = new StockController();
    }

    public void getProductsInStock(){
        stockController.getProductsInStock();
    }

    public void getPurchasesHistoryReport(){
        //Requirement 3
        stockController.getPurchasesHistoryReport();
    }

    public void getCurrentDiscounts(){
        //Requirement 4
        stockController.getCurrentDiscounts();
    }

    public void getCategories(){
        //Requirement 5
        stockController.getCategories();
    }

    public void getStockReport(){
        //Requirement 6
        stockController.getStockReport();
    }

    public void getStockReportByCategory(int CategoryID){
        //Requirement 7
        stockController.getStockReportByCategory(CategoryID);
    }

    public void getUnusableProductsReport(){
        //Requirement 8+9
        stockController.getUnusableProductsReport();
    }

    public void insertNewProduct(String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){
        stockController.insertNewProduct(productName,productManufacturer,categoryID,supplyTime,demand);
    }

    public void insertNewCategory(String categoryName){
        stockController.insertNewCategory(categoryName);
    }

    public void insertNewDiscount(int productID, Date startDate, Date endDate, int amount, Type t){
        stockController.insertNewDiscount(productID, startDate, endDate, amount, t);
    }

    /*public void insertNewPurchase(Date purchaseDate, Map m){
    }*/

    public void deleteProduct(int productID){
        stockController.deleteProduct(productID);
    }

    public void deleteCategory(int categoryID){
        stockController.deleteCategory(categoryID);
    }

    public void deleteDiscount(int discountID){
        stockController.deleteDiscount(discountID);
    }

    public void deletePurchase(int purchaseID){
        stockController.deletePurchase(purchaseID);
    }
}

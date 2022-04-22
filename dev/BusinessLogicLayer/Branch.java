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

    }

    public void getPurchasesHistoryReport(){
        //Requirement 3

    }

    public void getCurrentDiscounts(){
        //Requirement 4
    }

    public void getCategories(){
        //Requirement 5
    }

    public void getStockReport(){
        //Requirement 6
    }

    public void getStockReportByCategory(int CategoryID){
        //Requirement 7
    }

    public void getUnusableProductsReport(){
        //Requirement 8+9
    }

    public void insertNewProduct(String productName, String productManufacturer, int categoryID, Date supplyTime, int demand){

    }

    public void insertNewCategory(String categoryName){

    }

    public void insertNewDiscount(int productID, Date startDate, Date endDate, int amount, Type t){

    }

    /*public void insertNewPurchase(Date purchaseDate, Map m){
    }*/

    public void deleteProduct(int productID){

    }

    public void deleteCategory(int categoryID){

    }

    public void deleteDiscount(int discountID){

    }

    public void deletePurchase(int purchaseID){

    }


}

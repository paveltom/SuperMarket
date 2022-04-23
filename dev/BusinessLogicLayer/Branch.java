package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.List;

public class Branch {
    private int branchID;
    private String name;
    private StockController stockController;

    Branch(int _branchID,String _name){
        branchID = _branchID;
        name = _name;
        stockController = new StockController();
    }

    public List<Product> getProductsInStock(){
        return stockController.getProductsInStock();
    }

    public List<Purchase> getPurchasesHistoryReport(){
        //Requirement 3
        return stockController.getPurchasesHistoryReport();
    }

    public List<Discount> getCurrentDiscounts(){
        //Requirement 4
        return stockController.getCurrentDiscounts();
    }

    public List<Category> getCategories(){
        //Requirement 5
        return stockController.getCategories();
    }

    public List<Item> getStockReport(){
        //Requirement 6
        return stockController.getStockReport();
    }

    public List<Item> getStockReportByCategory(int CategoryID){
        //Requirement 7
        return stockController.getStockReportByCategory(CategoryID);
    }

    public List<Item> getUnusableItemsReport(){
        //Requirement 8+9
        return stockController.getUnusableItemsReport();
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

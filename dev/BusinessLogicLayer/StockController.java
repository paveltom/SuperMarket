package com.company.BusinessLogicLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockController {
    private List<Product> products;
    private int productsCounter;
    private List<Purchase> purchases;
    private int purchasesCounter;
    private List<Category> categories;
    private int categoriesCounter;
    private List<Discount> discounts;
    private int discountsCounter;

    StockController(){
        products = new ArrayList<Product>();
        productsCounter = 0;
        purchases = new ArrayList<Purchase>();
        purchasesCounter = 0;
        categories = new ArrayList<Category>();
        categoriesCounter = 0;
        discounts = new ArrayList<Discount>();
        discountsCounter = 0;
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
        products.add(new Product(productsCounter, productName, productManufacturer, categoryID, supplyTime, demand));
        productsCounter++;
    }

    public void insertNewCategory(String categoryName){
        categories.add(new Category(categoriesCounter,categoryName));
    }

    public void insertNewDiscount(int productID, Date startDate, Date endDate, int amount, Type t){
        discounts.add(new Discount(discountsCounter, productID, startDate, endDate, amount, t));
    }

    /*public void insertNewPurchase(Date purchaseDate, Map m){
    }*/

    public void deleteProduct(int productID){
        products.remove(productID);
    }

    public void deleteCategory(int categoryID){
        categories.remove(categoryID);
    }

    public void deleteDiscount(int discountID){

    }

    public void deletePurchase(int purchaseID){
        purchases.remove(purchaseID);
    }



}

package com.company.DAL;

import com.company.StockModule.BusinessLogicLayer.Type;

import java.util.Date;
import java.util.List;

public class ProductDataMapper {

    DataBaseConnection conn;
    public ProductDataMapper(){
        conn = new DataBaseConnection();
    }

    public void addProduct(String pId, String name, String manufacturer,int amountToNotify,  int categoryID, Date supplyTime, int demand){
        String[] params = {pId, name, manufacturer, String.valueOf(amountToNotify), String.valueOf(categoryID),
                String.valueOf(supplyTime), String.valueOf(demand)};
        conn.insert("Producs", params);
    }
    public void removeProduct(String pId){
        String[] keys = {"product_id"};
        String[] keysVals = {pId};
        conn.delete("Products", keys, keysVals);
    }
    public void updateAmount(String pId, int amount){
        String[] keys = {"product_id"};
        String[] keysVals = {pId};
        conn.update("Products", keys, keysVals, "amount", String.valueOf(amount));
    }
    public void addItem(String pId, String location, Date expireDate, boolean isDefect , int amount){
        String[] params = {pId, location, String.valueOf(expireDate), String.valueOf(isDefect), String.valueOf(amount)};
        conn.insert("Items", params);
    }
    public void deleteItem(String pId, String location, Date expireDate, boolean isDefect){
        String[] keys = {"product_id", "location", "expireDate", "isDefect"};
        String[] keysVals = {pId};
        conn.delete("Items", keys, keysVals);
    }
    public void reduceItemAmount(String pId, String location, Date expireDate, boolean isDefect, int newAmount){
        String[] keys = {"product_id", "location", "expireDate", "isDefect"};
        String[] keysVals = {pId};
        conn.update("Items", keys, keysVals, "amount", String.valueOf(newAmount));
    }

    public void addCategory(int cId, String name, String parentCategory){
        String[] params = {String.valueOf(cId), name, parentCategory};
        conn.insert("Category", params);
    }

    public void updateSubCategory(int cId, List<Integer> subCategories){
        String[] keys = {"category_id"};
        String[] keysVals = {String.valueOf(cId)};
        conn.update("Category", keys, keysVals, "subCategories", String.valueOf(subCategories));
    }

    public void addDiscount(int discountID, int productID, Date discountStartDate, Date discountEndDate, int discountAmount, Type discountType){
        String[] params = {String.valueOf(discountID), String.valueOf(productID), discountStartDate.toString(), discountEndDate.toString(), String.valueOf(discountAmount), discountType.toString()};
        conn.insert("Discounts", params);
    }
    public void removeDiscount(int discountId){
        String[] keys = {"discount_id"};
        String[] keysVals = {String.valueOf(discountId)};
        conn.delete("Discounts", keys, keysVals);
    }

    public void addPurchase(int purchaseID, Date purchaseDate){
        String[] params = {String.valueOf(purchaseID), purchaseDate.toString()};
        conn.insert("Purchases", params);
    }

    public void removePurchase(int purchaseId){
        String[] keys = {"purchase_id"};
        String[] keysVals = {String.valueOf(purchaseId)};
        conn.delete("Purchases", keys, keysVals);
    }
}

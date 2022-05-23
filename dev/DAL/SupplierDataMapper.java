package com.company.DAL;

import java.util.Arrays;

public class SupplierDataMapper {
    DataBaseConnection conn;
    public SupplierDataMapper(){
        conn = new DataBaseConnection();
    }

    public void addSupplier(String sId, String BankAccount, boolean cash, boolean credit, boolean[] supDays, int maxSupDays, int supCycle, boolean hasDeliveryService){
        String[] params = {sId, BankAccount, String.valueOf(cash), String.valueOf(credit), Arrays.toString(supDays), String.valueOf(maxSupDays), String.valueOf(supCycle),
                String.valueOf(hasDeliveryService)};
        conn.insert("suppliers", params);
    }

    public void removeSupplier(String sId){
        String[] keys = {"supplier_id"};
        String[] keysVals = {sId};
        conn.delete("Suppliers", keys, keysVals);
    }

    public void addContact(String sId, String contactName, String phoneNum){
        String[] params = {sId, contactName, phoneNum};
        conn.insert("Supplier_contacts", params);
    }

    public void removeContact(String sId, String name, String phoneNum){
        String[] keys = {"supplier_id", "name", "phoneNum"};
        String[] keysVals = {sId, name, phoneNum};
        conn.delete("Supplier_Contacts", keys, keysVals);
    }

    public void addProduct(String sId, String pId, String catalogNum, float price, boolean isPeriodic){
        String[] params = {sId, pId, String.valueOf((price)), String.valueOf(isPeriodic), catalogNum};
        conn.insert("Product_Contract", params);
    }

    public void removeProduct(String sId, String pId){
        String[] keys = {"supplier_id", "product_id"};
        String[] keysVals = {sId, pId};
        conn.delete("Product_Contract", keys, keysVals);
    }
    public void updateProductCatalogNum(String sId, String pId, String newCatalogNum){
        String[] keys = {"supplier_id", "product_id"};
        String[] keysVals = {sId, pId};
        conn.update("Product_Contract", keys, keysVals, "catalogNum", newCatalogNum);
    }
    public void updateProductPrice(String sId, String pId, float price){
        String[] keys = {"supplier_id", "product_id"};
        String[] keysVals = {sId, pId};
        conn.update("Product_Contract", keys, keysVals, "price", String.valueOf(price));
    }

    public void addDiscount(String sId, String pId, int quantity, float discount){
        String[] params = {sId, pId, String.valueOf((quantity)), String.valueOf(discount)};
        conn.insert("QuantityAgreement", params);
    }

    public void updateDiscount(String sId, String pId, int quantity, float discount){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        conn.update("QuantityAgreement", keys, keysVals, "discount", String.valueOf(discount));
    }
    public void removeDiscount(String sId, String pId, int quantity){
        String[] keys = {"supplier_id", "product_id", "quantity"};
        String[] keysVals = {sId, pId, String.valueOf(quantity)};
        conn.delete("QuantityAgreement", keys, keysVals);
    }
    public void changeDaysOfDelivery(String sId, boolean[] supDays) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {sId};
        conn.update("Suppliers", keys, keysVals, "supplyDays", String.valueOf(supDays));
    }




}

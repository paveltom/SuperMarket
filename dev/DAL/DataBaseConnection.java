package com.company.DAL;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseConnection {
    private String[] suppliers = {"supplier_id", "name", "address", "payment", "supplyDays", "supplyDays",
                                    "supplyMaxDays", "supplyCycle", "deliveryService"};
    private String[] quantityAgreement = {"supplier_id", "product_id", "quantity", "discount"};
    private String[] items = {"product_id", "location", "expireDate", "isDefect", "isExpired", "amount"};
    private String[] products = {"product_id", "name", "demand", "category_id", "amountToNotify", "manufacturer"};
    private String[] product_contract = {"supplier_id", "product_id", "price", "is_periodic_order", "catalogNum"};
    private String[] discount = {"discount_id", "product_id", "discountStartDate", "discountEndDate", "discountAmount", "discountType"};
    private String[] discount_product = {"discount_id", "quantity", "discount"};
    private String[] category = {"category_id", "name", "parentCategory", "subCategories"};

    // add other strings
    private final Map<String, String[]> tablesWithParams = new HashMap<String, String[]>() {{
        put("Suppliers", suppliers);
        put("QuantityAgreement", quantityAgreement);
        put("Items", items);
        put("Products", products);
        put("Product_Contract", product_contract);
        put("Discounts", discount);
        put("Discount_Product", discount_product);
        put("Category", category);
        // add other values
    }};

    // Connect to a database
    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String dir = System.getProperty("user.dir");
            String url = "jdbc:sqlite:" + dir + "\\dev\\DAL\\SuperLiDB.db";

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public boolean insert(String tableNAME, String[] params) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            String sql = "INSERT INTO " + tableNAME + "  VALUES (" + params[0] + ",";
            for (int i = 1; i < params.length; i++)
                sql += "," + params[i];
            if(sql.endsWith(","))
                sql=sql.substring(0, sql.length()-1);
            sql += ");";
            ResultSet rs = stmt.executeQuery(sql);
            conn.commit();
            if (!rs.next()) return false;
            rs.close();
            stmt.close();
            conn.close();
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

}
package com.company.DAL;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseConnection {
    private String[] suppliers = {"supplier_id", "bankAccount", "cash", "credit", "supplyDays",
                                    "MaxSupplyDays", "supplCycle", "deliveryService"};
    private String[] supplier_contacts = {"supplier_id", "contactName", "phoneNum"};

    private String[] products = {"product_id", "name", "manufacturer", "amountToNotify", "categoryID", "supplyTime", "demand"};

    private String[] quantityAgreement = {"supplier_id", "product_id", "quantity", "discount"};
    private String[] items = {"product_id", "location", "expireDate", "isDefect", "isExpired", "amount"};

    private String[] product_contract = {"supplier_id", "product_id", "price", "is_periodic_order", "catalogNum"};
    private String[] discount = {"discount_id", "product_id", "discountStartDate", "discountEndDate", "discountAmount", "discountType"};
    private String[] discount_product = {"discount_id", "quantity", "discount"};
    private String[] category = {"category_id", "name", "parentCategory", "subCategories"};
    private String[] purchase = {"purchase_id", "purchaseDate"};

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
        put("Supplier_Contacts", category);
        put("Purchases", purchase);
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

    public boolean update(String tableNAME, String[] key, String[]keysNAMES, String paramNAME, String paramVALUE){
        //System.out.println("\nupdate");
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder("UPDATE " + tableNAME + " set " + paramNAME + " = '" + paramVALUE + "' where " + keysNAMES[0] + "=" + key[0]);
            for (int i = 1; i < keysNAMES.length; i++)
                sql.append(" AND ").append(keysNAMES[i]).append("=").append(key[i]);
            sql.append(";");

            //System.out.println("sql: " + sql);

            boolean res = stmt.execute(sql.toString());
            conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Update method. On table: " + tableNAME);
            return false;
        }
    }

    public boolean delete(String tableNAME, String[] key, String[] keysNAMES) {
        //System.out.println("\ndelete");
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder("Delete from " + tableNAME + " where " + keysNAMES[0] + "=" + key[0]);
            for (int i = 1; i < keysNAMES.length; i++)
                sql.append(" AND ").append(keysNAMES[i]).append("=").append(key[i]);
            sql.append(";");

            //System.out.println("delete sql: " + sql);

            boolean res = stmt.execute(sql.toString());
            conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Delete method. On table: " + tableNAME);
            return false;
        }
    }
}
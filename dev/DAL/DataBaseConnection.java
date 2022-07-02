package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseConnection {
    private String[] suppliers = {"supplier_id", "name", "address", "bank", "cash", "credit", "deliveryService"};
    private String[] supplyTimes = {"supplier_id", "daysOfDelivery", "maxDeliveryDuration", "orderCycle", "daysAcc"};
    private String[] contacts = {"supplier_id", "contactName", "phoneNum"};
    private String[] product_order = {"supplier_id", "product_id", "order_id", "quantity", "discount", "finalPrice", "catalogPrice"};
    private String[] products = {"product_id", "name", "manufacturer", "amountToNotify", "categoryID", "demand"};
    private String[] product_contract = {"supplier_id", "product_id", "price", "is_periodic_order", "catalogNum"};
    private String[] quantityAgreements = {"supplier_id", "product_id", "quantity", "discount"};
    private String[] discounts = {"discount_id", "product_id", "discountStartDate", "discountEndDate", "discountAmount", "discountType"};
    private String[] orders = {"supplier_id", "id", "date", "contactPhone", "supName", "supAddress"};
    private String[] items = {"product_id", "location", "expireDate", "isDefect", "isExpired", "amount"};
    private String[] discount_product = {"discount_id", "quantity", "discount"};
    private String[] category = {"category_id", "name"};
    private String[] categoryRelation = {"ParentId", "ChildId"};
    private String[] product_category = {"product_id", "category_id"};


    // add other strings
    private final Map<String, String[]> tablesWithParams = new HashMap<String, String[]>() {{
        put("Suppliers", suppliers);
        put("SupplyTimes", supplyTimes);
        put("Contacts", contacts);
        put("Product_Order", product_order);
        put("Products", products);
        put("Orders", orders);

        put("Product_Contract", product_contract);

        put("QuantityAgreements", quantityAgreements);

        put("Discounts", discounts);



        put("Items", items);

        put("Discount_Product", discount_product);

        put("Category", category);
        put("CategoryRelations", categoryRelation);

        put("Product_Category", product_category);
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

            //System.out.println("Connection to SQLite has been established.");
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
            String sql = "INSERT INTO " + tableNAME + "  VALUES ('" + params[0] + "',";
            for (int i = 1; i < params.length; i++)
                sql += "'" + params[i]+ "',";
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

    public List<String[]> select(String tableNAME, String[] paramsWHERE, String[] paramsWHEREValues) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder("SELECT * from  " + tableNAME);
            if(paramsWHERE != null){
                sql.append(" where ").append(paramsWHERE[0]).append("='").append(paramsWHEREValues[0]+"'");
                for (int i = 1; i < paramsWHERE.length; i++)
                    sql.append(" AND ").append(paramsWHERE[i]).append("='").append(paramsWHEREValues[i]+"'");
            }
            sql.append(";");

            ResultSet resultSet = stmt.executeQuery(sql.toString());
            String[] resRow = tablesWithParams.get(tableNAME);


            List<String[]> output = new ArrayList<>();
            while(resultSet.next()){
                String[] resValues = new String[resRow.length];
                for(int i = 0; i < resRow.length; i++)
                    resValues[i] = resultSet.getString(resRow[i]);
                output.add(resValues);
            }

            resultSet.close();
            stmt.close();
            conn.close();
            return output;
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Select method. On table: " + tableNAME);
            return null;
        }
    }

}
package DAL.Delivery_Personnel;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;

public class DataBaseConnection {

    private boolean exists = false;
    private String[] truckValues = {"VehicleLicenseNumber", "MaxLoadWeight", "NetWeight", "Model", "ShippingZone", "Diary", "AuthorizedLicense"};
    private String[] driverValues = {"Id", "Name", "Cellphone", "VehicleLicenseCategory", "ShippingZone", "Diary", "FutureShifts"};
    private String[] deliveryValues = {"OrderId", "SupplierZone", "SupplierAddress", "SupplierName", "SupplierCellphone",
            "ClientZone", "ClientAddress", "ClientName", "ClientCellphone", "DeliverdProducts", "DueDate",
            "DriverId", "TruckLicenseNumber", "Status"};
    private String[] shiftValues = {"Date", "Type", "Manager", "Workers"};
    private String[] workerValues = {"Id","Name","Job","SMQual","BankDetails","Pay","StartDate","SocialConditions","Availability"};
    // add other strings
    private final Map<String, String[]> tablesWithParams = new HashMap<String, String[]>() {{
        put("Trucks", truckValues);
        put("Shifts", shiftValues);
        put("Drivers", driverValues);
        put("Deliveries", deliveryValues);
        put("Workers",workerValues);
        // add other values
    }};

    // Connect to a database
    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String dir = System.getProperty("user.dir");
            //String url = "jdbc:sqlite:" + dir + "\\dev\\DataBase\\PerDel.db";
            String url = "jdbc:sqlite:" + dir + "\\PerDel.db";
//            String url = "jdbc:sqlite:" + dir + "/PerDel.db"; FOR LINUX
            //System.out.println("url: " + url);
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            exists = createCrucialTables(conn); // create db with tables if not exists

            //System.out.println("Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(String tableNAME, String[] params){ // exmpl: TRUCK, {"01113", "1000"...}
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("\ninsert");
        //System.out.println("table: " + tableNAME);
        //System.out.println("params: ");
        //for(String curr: params) System.out.print(curr + " ");
        //System.out.println();
        //System.out.println("paramNames: ");
        //for(String curr: tablesWithParams.get(tableNAME)) System.out.print(curr + " ");
        //System.out.println();
        try {
            conn = connect();
            stmt = conn.createStatement();
            String sql = "INSERT INTO " + tableNAME + " VALUES ('" + params[0] + "'";
            for (int i = 1; i < params.length; i++)
                sql += ",'" + params[i] + "'";
            sql += ");";
            //System.out.println("sql: " + sql);
            boolean res = stmt.execute(sql);
            //conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch (Exception e){
//            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Insert method. On table: " + tableNAME);
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
            }
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
//            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Update method. On table: " + tableNAME);
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
            }
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
//            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Delete method. On table: " + tableNAME);
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
            }
            return false;
        }

    }

    /**
     *
     * @param tableNAME NAME of the table to select from.
     * @param paramsWHERE put 'null' here if you want to select all values from the table.
     * @param paramsWHEREValues put 'null' here if you want to select all values from the table.
     */
    public List<String[]> select(String tableNAME, String[] paramsWHERE, String[] paramsWHEREValues) {
        //System.out.println("\nselect");
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = connect();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder("SELECT * from  " + tableNAME);
            if(paramsWHERE != null){
                sql.append(" where ").append(paramsWHERE[0]).append("=").append(paramsWHEREValues[0]);
                for (int i = 1; i < paramsWHERE.length; i++)
                    sql.append(" AND ").append(paramsWHERE[i]).append("=").append(paramsWHEREValues[i]);
            }
            sql.append(";");

            ResultSet resultSet = stmt.executeQuery(sql.toString());
            String[] resRow = tablesWithParams.get(tableNAME);
            String[] resValues = new String[resRow.length];
            List<String[]> output = new ArrayList<>();
            while(resultSet.next()){
                for(int i = 0; i < resRow.length; i++)
                    resValues[i] = resultSet.getString(resRow[i]);
                output.add(Arrays.copyOf(resValues, resValues.length));
            }

            resultSet.close();
            stmt.close();
            conn.close();
            return output;
        } catch ( Exception e ) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage() + ". Select method. On table: " + tableNAME);
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }
    }

    private boolean createCrucialTables(Connection conn) {
        Statement stmt = null;
        String existence = "SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;";

        String driversTableCreation = "CREATE TABLE Drivers " +
                "(Id TEXT PRIMARY KEY NOT NULL," +
                " Name TEXT NOT NULL," +
                " Cellphone TEXT NOT NULL," +
                " VehicleLicenseCategory TEXT NOT NULL," +
                " ShippingZone TEXT NOT NULL," +
                " Diary TEXT NOT NULL," +
                " FutureShifts TEXT NOT NULL)";

        String trucksTableCreation = "CREATE TABLE Trucks " +
                "(VehicleLicenseNumber TEXT NOT NULL," +
                " MaxLoadWeight TEXT NOT NULL," +
                " NetWeight TEXT NOT NULL," +
                " Model TEXT NOT NULL," +
                " ShippingZone TEXT NOT NULL," +
                " Diary TEXT NOT NULL," +
                " AuthorizedLicense TEXT NOT NULL," +
                " PRIMARY KEY(VehicleLicenseNumber))";

        String deliveriesTableCreation = "CREATE TABLE Deliveries (" +
                "OrderId TEXT," +
                " SupplierZone TEXT," +
                " SupplierAddress TEXT," +
                " SupplierName TEXT," +
                " SupplierCellphone TEXT," +
                " ClientZone TEXT," +
                " ClientAddress TEXT," +
                " ClientName TEXT," +
                " ClientCellphone TEXT," +
                " DeliverdProducts TEXT," +
                " DueDate TEXT," +
                " DriverID TEXT," +
                " TruckLicenseNumber INTEGER," +
                " Status INTEGER," +
                " PRIMARY KEY(OrderId)" + ")";

        String shiftsTableCreation = "CREATE TABLE Shifts (" +
                "Date TEXT NOT NULL," +
                " Type TEXT NOT NULL," +
                " Manager TEXT NOT NULL," +
                " Workers TEXT NOT NULL," +
                " PRIMARY KEY(Date,Type)" + ");";

        String workersTableCreataion = "CREATE TABLE Workers (" +
                "Id TEXT NOT NULL," +
                " Name TEXT NOT NULL," +
                "Job TEXT NOT NULL," +
                " SMQual TEXT NOT NULL," +
                " BankDetails TEXT," +
                " Pay TEXT NOT NULL," +
                " StartDate TEXT NOT NULL," +
                " SocialConditions TEXT," +
                " Availability TEXT," +
                " PRIMARY KEY(Id)" + ");";









        String stockSupCategory = "CREATE TABLE Category(" +
                "category_id TEXT," +
                "name TEXT," +
                "PRIMARY KEY(category_id));";

        String stocksupCategoryRelations = "CREATE TABLE CategoryRelations(" +
                "ParentId TEXT," +
                "ChildId TEXT," +
                "PRIMARY KEY(ParentId,ChildId));";

        String stockSupContacts = "CREATE TABLE Contacts (" +
                "supplier_id TEXT," +
                "contactName TEXT," +
                "phoneNum TEXT," +
                "PRIMARY KEY(supplier_id,contactName,phoneNum));";

        String stockSupDiscount_Product = "CREATE TABLE Discount_Product (" +
                "discount_id TEXT," +
                "quantity BLOB," +
                "discount TEXT," +
                "PRIMARY KEY(discount_id, quantity));";

        String stockSupDiscounts = "CREATE TABLE Discounts (" +
                "discount_id TEXT, " +
                "product_id TEXT, " +
                "discountStartDate TEXT, " +
                "discountEndDate TEXT, " +
                "discountAmount TEXT, " +
                "discountType TEXT, " +
                "PRIMARY KEY(discount_id));";

        String stockSupItems = "CREATE TABLE Items (" +
                "product_id TEXT, " +
                "location TEXT, " +
                "expireDate TEXT, " +
                "isDefect TEXT, " +
                "isExpired TEXT, " +
                "amount TEXT, " +
                "PRIMARY KEY(product_id, location, expireDate, isDefect));";

        String stockSupOrders = "CREATE TABLE Orders(" +
                "supplier_id TEXT, " +
                "id TEXT," +
                "date TEXT, " +
                "contactPhone TEXT, " +
                "supName TEXT, " +
                "supAddress TEXT, " +
                "PRIMARY KEY(supplier_id, id));";

        String stockSupProduct_Contract = "CREATE TABLE Product_Contract (" +
                "supplier_id TEXT, " +
                "product_id TEXT, " +
                "price TEXT, " +
                "is_periodic_order TEXT, " +
                "catalogNum TEXT, " +
                "PRIMARY KEY(supplier_id, product_id));";

        String stockSupProduct_Order = "CREATE TABLE Product_Order (" +
                "supplier_id TEXT, " +
                "product_id TEXT, " +
                "order_id TEXT, " +
                "quantity TEXT, " +
                "discount TEXT, " +
                "finalPrice TEXT, " +
                "catalogPrice TEXT, " +
                "PRIMARY KEY(supplier_id, order_id, product_id));";

        String stockSupProducts = "CREATE TABLE Products (" +
                "product_id TEXT, " +
                "name TEXT, " +
                "manufacturer TEXT, " +
                "amountToNotify TEXT, " +
                "categoryID TEXT, " +
                "demand TEXT, " +
                "PRIMARY KEY(product_id));";

        String stockSupQuantityAgreements = "CREATE TABLE QuantityAgreements (" +
                "supplier_id TEXT, " +
                "product_id TEXT, " +
                "quantity TEXT, " +
                "discount TEXT, " +
                "PRIMARY KEY(supplier_id, product_id, quantity));";


        String stockSupSuppliers = "CREATE TABLE Suppliers (" +
                "supplier_id TEXT, " +
                "name TEXT, " +
                "address TEXT, " +
                "bank TEXT, " +
                "cash TEXT, " +
                "credit TEXT, " +
                "deliveryService TEXT, " +
                "PRIMARY KEY(supplier_id));";

        String stockSupSupplyTimes = "CREATE TABLE SupplyTimes (" +
                "supplier_id TEXT, " +
                "daysOfDelivery TEXT, " +
                "maxDeliveryDuration TEXT, " +
                "orderCycle TEXT, " +
                "daysAcc TEXT," +
                "PRIMARY KEY(supplier_id));";




        try{
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "Drivers", new String[] {"TABLE"});
            if(resultSet.next()){
                resultSet.close();
                return true;
            }

            stmt = conn.createStatement();
            stmt.execute(driversTableCreation);
            stmt.execute(trucksTableCreation);
            stmt.execute(deliveriesTableCreation);
            stmt.execute(shiftsTableCreation);
            stmt.execute(workersTableCreataion);

            stmt.execute(stockSupCategory);
            stmt.execute(stocksupCategoryRelations);
            stmt.execute(stockSupContacts);
            stmt.execute(stockSupDiscount_Product);
            stmt.execute(stockSupDiscounts);
            stmt.execute(stockSupItems);
            stmt.execute(stockSupOrders);
            stmt.execute(stockSupProduct_Contract);
            stmt.execute(stockSupProduct_Order);
            stmt.execute(stockSupProducts);
            stmt.execute(stockSupQuantityAgreements);
            stmt.execute(stockSupSuppliers);
            stmt.execute(stockSupSupplyTimes);

            System.out.println("DB and Tables created successfully...");
            stmt.close();
            resultSet.close();
            return true;
        } catch (SQLException e) {
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            return false;
        }
    }

    public void deleteDB(){
        try {
            Connection conn = connect();
            conn.close();
        }catch (Exception e){

        }
        String dir = System.getProperty("user.dir");
        //File f = new File(dir + "\\dev\\DataBase\\PerDel.db");
        File f = new File(dir + "\\PerDel.db");
        if(f.exists()){

            f.delete();
        }
    }



    // for super user only
    public void initDB() {
        if(!exists) {
            String[] params = {"123456789", "rami", "PersonnelManager", "yes", "bank", "31.0", "22/06/2022", "Social", ""};
            insert("Workers", params);
        }
    }


}

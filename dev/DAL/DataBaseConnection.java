package DAL;

import java.sql.*;
import java.util.*;

public class DataBaseConnection {
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
            String url = "jdbc:sqlite:" + dir + "/PerDel.db";
            //System.out.println("url: " + url);
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            boolean exists = createCrucialTables(conn); // create db with tables if not exists

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
            return null;
        }
    }

    private boolean createCrucialTables(Connection conn) {
        Statement statement;
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

        try{
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "Drivers", new String[] {"TABLE"});
            if(resultSet.next()){
                resultSet.close();
                return true;
            }

            Statement stmt = conn.createStatement();
            stmt.execute(driversTableCreation);
            stmt.execute(trucksTableCreation);
            stmt.execute(deliveriesTableCreation);
            stmt.execute(shiftsTableCreation);
            stmt.execute(workersTableCreataion);
            System.out.println("DB and Tables created successfully...");
            stmt.close();
            resultSet.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // for super user only
    public void addTable() {

    }


}

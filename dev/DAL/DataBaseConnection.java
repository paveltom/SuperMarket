package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseConnection {
    private String[] truckValues = {"VehicleLicenseNumber", "MaxLoadWeight", "NetWeight", "Zone", "Model", "Diary"};
    private String[] driverValues = {"Id", "Name", "Cellphone", "VehicleLicenseCategory", "ShippingZone", "Diary"};
    private String[] deliveryValues = {"OrderId", "DeliveryId", "SupplierZone", "SupplierAddress", "SupplierName", "SupplierCellphone",
            "ClientZone", "ClientAddress", "ClientName", "ClientCellphone", "DeliverdProducts", "DueDate",
            "DriverId", "DriverName", "DriverCellphone", "TruckLicenseNumber"};
    private String[] shiftValues = {"date", "type", "shiftManager", "workers"};
    private String[] workerValues = {"Id","Name","Job","SMQual","BankDetails","Pay","StartDate","SocialConditions","Availability"};
    // add other strings
    private final Map<String, String[]> tablesWithParams = new HashMap<String, String[]>() {{
        put("Trucks", truckValues);
        put("shifts", shiftValues);
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
            String url = "jdbc:sqlite:" + dir + "\\dev\\DataBase\\example.db";
            System.out.println("url: " + url);
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public boolean insert(String tableNAME, String[] params){ // exmpl: TRUCK, {"01113", "1000"...}
        System.out.println("\ninsert");
        Connection conn = null;
        Statement stmt = null;
        System.out.println("table: " + tableNAME);
        System.out.println("params: ");
        for(String curr: params) System.out.print(curr + " ");
        System.out.println();
        System.out.println("paramNames: ");
        for(String curr: tablesWithParams.get(tableNAME)) System.out.print(curr + " ");
        System.out.println();
        try {
            conn = connect();
            stmt = conn.createStatement();
            //String sql = "INSERT INTO " + tableNAME + " " + String.join(",", tablesWithParams.get(tableNAME)) + " VALUES (" + params[0];
            String sql = "INSERT INTO " + tableNAME + " VALUES ('" + params[0] + "'";
            for (int i = 1; i < params.length; i++)
                sql += ",'" + params[i] + "'";
            sql += ");";
            System.out.println("sql: " + sql);
            boolean res = stmt.execute(sql);
            //conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public boolean update(String tableNAME, String[] key, String[]keysNAMES, String paramNAME, String paramVALUE){
        System.out.println("\nupdate");
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

            System.out.println("sql: " + sql);

            boolean res = stmt.execute(sql.toString());
            conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public boolean delete(String tableNAME, String[] key, String[] keysNAMES) {
        System.out.println("\ndelete");
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

            System.out.println("delete sql: " + sql);

            boolean res = stmt.execute(sql.toString());
            conn.commit();
            stmt.close();
            conn.close();
            return res;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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
        System.out.println("\nselect");
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
                output.add(resValues);
            }

            resultSet.close();
            stmt.close();
            conn.close();
            return output;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }


//    private boolean isTableExists(SQLiteDatabase db, String tableName){
//        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'";
//        Cursor mCursor = db.rawQuery(sql, null);
//        if (mCursor.getCount() > 0) {
//            return true;
//        }
//        mCursor.close();
//        return false;
//    }
//
//    // if database has no tables
//    private void createCrucialTables() {
//
//    }



    // for super user only
    public void addTable() {

    }


}

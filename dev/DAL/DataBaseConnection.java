package DAL;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseConnection {
    private final Map<String, String> tablesWithParams = new HashMap<String, String>() {{
        put("TRUCKS", "(LICENSEPLATE,MAXLOAD,NETWEIGHT,ZONE)");
        // add other values
    }};

    // Connect to a database
    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String dir = System.getProperty("user.dir");
            String url = "jdbc:sqlite:" + dir + "/example.db";

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public void insert(String tableNAME, String[] params){ // exmpl: TRUCK, {"01113", "1000"...}
        Connection conn = connect();
        String statement = "INSERT INTO" + tableNAME + tablesWithParams.get(tableNAME) + "VALUES (" + params[0] + ",...";
    }

    public void update(String tableNAME, String[] keys, String paramNAME, String paramTYPE, Object value)




    // if database has no tables
    private void createCrucialTables() {

    }

    // for super user only
    public void addTable() {

    }


}

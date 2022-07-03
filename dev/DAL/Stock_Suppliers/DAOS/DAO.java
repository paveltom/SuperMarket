package DAL.Stock_Suppliers.DAOS;

import DAL.Stock_Suppliers.DataBaseConn;
import java.util.List;

public class DAO<T> {
    private final DataBaseConn conn = new DataBaseConn();

    protected void insert(String tableName,String[] params){
        conn.insert(tableName, params);
    }

    protected void update(String tableName,String[] keys, String[] keysVals, String attribute, String value){
        conn.update(tableName, keys, keysVals, attribute, String.valueOf(value));
    }

    protected void delete(String tableName, String[] keys, String[] keysVals){
        conn.delete(tableName, keysVals, keys);
    }

    protected List<String[]> load(String tableName, String[] params, String[] ParamsVals){
        return conn.select(tableName, params, ParamsVals);
    }

}

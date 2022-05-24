package DAL.DAO;

import DAL.DataBaseConnection;
import StockModule.BusinessLogicLayer.Item;
import SuppliersModule.DomainLayer.CatalogProduct;

public class ItemDAO {
    DataBaseConnection conn;
    public ItemDAO(){conn = new DataBaseConnection();}

    public void insert(Item i){
        String[] params = {i.getProductID(), i.getLocation(), String.valueOf(i.getExpireDate()),
                        String.valueOf(i.isDefect()), String.valueOf(i.isExpired()), String.valueOf(i.getAmount())};
        conn.insert("Items", params);
    }
    public void delete(Item i){
        String[] keys = {"product_id", "location", "expireDate", "isDefect"};
        String[] keysVals = {i.getProductID(), i.getLocation(), String.valueOf(i.getExpireDate()),
                String.valueOf(i.isDefect())};
        conn.delete("Items", keys, keysVals);
    }
    public void setAmount(Item i){
        setAttribute(i, "amount", String.valueOf(i.getAmount()));
    }

    public void setExpired(Item i){
        setAttribute(i, "isExpired", String.valueOf(i.isExpired()));
    }
    public void setDefect(Item i){
        setAttribute(i, "isDefect", String.valueOf(i.isDefect()));
    }

    private void setAttribute(Item i, String attribute, String value){
        String[] keys = {"product_id", "location", "expireDate", "isDefect"};
        String[] keysVals = {i.getProductID(), i.getLocation(), String.valueOf(i.getExpireDate()),
                String.valueOf(i.isDefect())};
        conn.update("Items", keys, keysVals, attribute, String.valueOf(value));
    }
}

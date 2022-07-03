package DAL.Stock_Suppliers.DAOS.StockObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import StockModule.BusinessLogicLayer.Item;

public class ItemDao extends DAO {
    public void insert(Item i){
        String[] params = {i.getProductID(), i.getLocation(), String.valueOf(i.getExpireDate()),
                String.valueOf(i.isDefect()), String.valueOf(i.isExpired()), String.valueOf(i.getAmount())};
        insert("Items", params);
    }
    public void delete(Item i){
        String[] keys = {"product_id", "location", "expireDate", "isDefect"};
        String[] keysVals = {i.getProductID(), i.getLocation(), String.valueOf(i.getExpireDate()),
                String.valueOf(i.isDefect())};
        delete("Items", keys, keysVals);
    }
    public void setAmount(Item i){
        setAttribute(i, "amount", String.valueOf(i.getAmount()));
    }

    public void setProductId(Item i){
        setAttribute(i, "product_id", i.getProductID());
    }

    public void setLocation(Item i){
        setAttribute(i, "location", i.getLocation());
    }

    public void setExpireDate(Item i){
        setAttribute(i, "expireDate", String.valueOf(i.getExpireDate()));
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
        update("Items", keys, keysVals, attribute, String.valueOf(value));
    }
}

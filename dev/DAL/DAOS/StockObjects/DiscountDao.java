package DAL.DAOS.StockObjects;

import DAL.DAOS.DAO;
import StockModule.BusinessLogicLayer.Discount;
import StockModule.BusinessLogicLayer.Item;
import StockModule.BusinessLogicLayer.Type;

public class DiscountDao extends DAO<Discount> {
    public void insert(Discount d, int amount, Type type){
        String t = "";
        if(type.equals(Type.FIXED))
             t= "1";
        else  {
            t="2";
        }
        String[] params = {String.valueOf(d.getDiscountID()), d.getProductID(), String.valueOf(d.getDiscountStartDate()),
                            String.valueOf(d.getDiscountEndDate()), String.valueOf(amount), t};
        insert("Discounts", params);
    }
    public void delete(Discount d){
        String[] keys = {"discount_id"};
        String[] keysVals = {String.valueOf(d.getDiscountID())};
        delete("Discounts", keys, keysVals);
    }
}

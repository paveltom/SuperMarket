package StockModule.BusinessLogicLayer;

import DAL.Stock_Suppliers.DAOS.StockObjects.DiscountDao;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Discount
{
    private int discountID;
    private String productID;
    private Date discountStartDate;
    private Date discountEndDate;
    private Map<Integer, Type> discount;
    private DiscountDao dao;

    Discount(String _productID, int _discountID, Date _discountStartDate, Date _discountEndDate,int _amount,Type _t)
    {
        dao = new DiscountDao();
        productID = _productID;
        discountID = _discountID;
        discountStartDate = _discountStartDate;
        discountEndDate = _discountEndDate;
        discount = new HashMap<>();
        discount.put(_amount, _t);

        dao.insert(this, _amount, _t);
    }

    //db
    public Discount(String _productID, int _discountID, Date _discountStartDate, Date _discountEndDate,int _amount,Type _t, boolean db)
    {
        productID = _productID;
        discountID = _discountID;
        discountStartDate = _discountStartDate;
        discountEndDate = _discountEndDate;
        discount = new HashMap<>();
        discount.put(_amount, _t);
    }



    public String toString(){
        Integer amount = (Integer) discount.keySet().toArray()[0];
        Type t = discount.get(amount);
        return "Discount ID : " + discountID + " , Product ID : " + productID + " , Start Date : " + discountStartDate + " , End Date : " + discountEndDate + " , Amount : " + amount + " " + t+ "\n";
    }

    public int getDiscountID() {
        return discountID;
    }

    public String getProductID() {
        return productID;
    }

    public Map<Integer, Type> getType() {
        return this.discount;
    }

    public Date getDiscountStartDate() {
        return discountStartDate;
    }

    public Date getDiscountEndDate() {
        return discountEndDate;
    }

    public Map<Integer, Type> getDiscount() {
        return discount;
    }
}

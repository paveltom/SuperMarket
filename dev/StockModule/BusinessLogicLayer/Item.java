package StockModule.BusinessLogicLayer;

import DAL.Stock_Suppliers.DAOS.StockObjects.ItemDao;
import java.util.Date;

public class Item {
    private String productID;
    private String location;
    private Date expireDate;
    private boolean isDefect;
    private boolean isExpired;
    private int amount;
    private ItemDao dao;

    public Item(String _location, String _productID, Date _expireDate, boolean _isDefect, int _amount)
    {
        dao = new ItemDao();
        location = _location;
        productID = _productID;
        expireDate = _expireDate;
        isDefect = _isDefect;
        checkIfExpired();
        amount = _amount;

        dao.insert(this);

    }

    //db
    public Item(String _location, String _productID, Date _expireDate, boolean _isDefect, boolean isExpired, int _amount, boolean isFromDB)
    {
        dao = new ItemDao();
        location = _location;
        productID = _productID;
        expireDate = _expireDate;
        isDefect = _isDefect;
        checkIfExpired();
        amount = _amount;
        this.isExpired = isExpired;
    }

    public String toString(){
        return "Product ID : " + productID + " , Location : " + location + " , Expire Date : " + expireDate + " , Amount : " + amount + ", " + (!isDefect ? "This item is not defected." : "") + ", " + (!isExpired ? "This item is not expired." : "") + "\n";
    }

    public void reduce(int amountToReduce) throws Exception
    {
        int newAmount = amount - amountToReduce;
        if(newAmount < 0)
        {
            throw new Exception("Can't reduce more than amount in stock. Reduce declined.");
        }
        amount = newAmount;
    }

    public void addToStock(int amountToAdd){
        this.amount += amountToAdd;
    }

    public void setOk(){
        this.isDefect = false;
    }

    public void setDefect(){
        this.isDefect = true;
    }

    public boolean isDefect(){
        return isDefect;
    }

    public void checkIfExpired(){
        isExpired = ((new Date()).compareTo(expireDate) > 0);
    }

    public boolean isExpired() {
        return isExpired;
    }

    public int getAmount(){
        return amount;
    }

    public String getProductID() {
        return productID;
    }

    public String getLocation() {
        return location;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    // todo: add dao
    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setDefect(boolean defect) {
        isDefect = defect;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean updateAttribute(int Attribute, Object Value){
        switch (Attribute) {
            case 0: {
                try {
                    setProductID((String) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 1: {
                try {
                    setLocation((String) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 2: {
                try {
                    setExpireDate((Date) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 3: {
                try {
                    setDefect((boolean) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 4: {
                try {
                    setExpired((boolean) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 5: {
                try {
                    setAmount((int) Value);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

}

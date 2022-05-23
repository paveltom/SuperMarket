package StockModule.BusinessLogicLayer;

import java.util.Date;

public class Item {
    private String productID;
    private String location;
    private Date expireDate;
    private boolean isDefect;
    private boolean isExpired;
    private int amount;

    public Item(String _location, String _productID, Date _expireDate, boolean _isDefect, int _amount)
    {
        location = _location;
        productID = _productID;
        expireDate = _expireDate;
        isDefect = _isDefect;
        checkIfExpired();
        amount = _amount;
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
}

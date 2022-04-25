package com.company.BusinessLogicLayer;

import java.util.Date;

public class Item {
    private int productID;
    private String location;
    private Date expireDate;
    private boolean isUsable;
    private int amount;

    public Item(String _location,int _productID, Date _expireDate,boolean _isUsable,int _amount)
    {
        location = _location;
        productID = _productID;
        expireDate = _expireDate;
        isUsable = _isUsable;
        amount = _amount;
    }

    public String toString(){
        return "Product ID : " + productID + " , Location : " + location + " , Expire Date : " + expireDate + " , Amount : " + amount + " " + (!isUsable ? "This item is not usable." : "")+ "\n";
    }

    public void reduce(int amountToReduce) throws Exception
    {
        int newAmount = amount - amountToReduce;
        if(newAmount<0)
        {
            throw new Exception("Can't reduce more than amount in stock. Reduce declined.");
        }
        amount = newAmount;
    }
    public void addToStock(int amountToAdd){
        this.amount += amountToAdd;
    }
    public void setAsUnusable(){
        this.isUsable = false;
    }
    public void setAsUsable(){
        this.isUsable = true;
    }
    public boolean isUsable(){
        return isUsable;
    }
    public void checkIfExpired(){
        if((new Date()).compareTo(expireDate) > 0)
        {
            setAsUnusable();
        }
    }
    public int getAmount(){
        return amount;
    }



}

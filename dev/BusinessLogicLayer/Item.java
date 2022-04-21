package com.company.BusinessLogicLayer;

import java.util.Date;

public class Item {
    private int productID;
    private String location;
    private Date expireDate;
    private boolean isUsable;
    private int amount;

    public Item(String _location,int productID, Date expireDate)
    {
        this.productID = productID;
        this.expireDate = expireDate;
        this.isUsable = true;
        this.amount = 0;
    }

    public void reduce(int amountToReduce){
        this.amount -= amountToReduce;
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

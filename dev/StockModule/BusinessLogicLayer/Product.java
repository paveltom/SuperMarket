package com.company.BusinessLogicLayer;

import java.util.*;

public class Product {
    private int ID;
    private String name;
    private String manufacturer;
    private int amount;
    private int amountToNotify;
    private int categoryID;
    private Date supplyTime;
    private int demand;
    private List<Item> items;

    public Product(int ID, String name, String manufacturer, int categoryID, Date supplyTime, int demand)
    {
        this.ID = ID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = 0;
        this.amountToNotify = 0;
        this.categoryID = categoryID;
        this.supplyTime = supplyTime;
        this.demand = demand;
        this.items = new LinkedList<>();

    }

    public String toString(){
        return "Product Name : " + name + " , Manufacturer : " + manufacturer + " , Amount : " + amount + " , Category ID : " + categoryID + " , Supply Time : " + supplyTime + " , Demand : " + demand+ "\n";
    }

    public void updateAmount() throws Exception
    {
        amount = 0;
        for(Item i : items){
            amount += i.getAmount();
        }
        if(amount<demand)
        {
            throw new Exception("PLEASE NOTICE : Current amount is lower than product's demand. Please refill stock.");
        }

    }

    public List<Item> getItems(){
        return items;
    }

    public int getCategoryID(){
        return categoryID;
    }

    public List<Item> getDefectedItems(){
        List<Item> output = new ArrayList<>();
        for(Item i : items){
            if(i.isDefect()){
                output.add(i);
            }
        }
        return output;
    }

    public List<Item> getExpiredItems() {
        List<Item> output = new ArrayList<>();
        for(Item i : items){
            if(i.isExpired()){
                output.add(i);
            }
        }
        return output;
    }

    public void addItem(String location, Date expireDate, boolean isDefect , int amount){
        items.add(new Item(location, ID, expireDate, isDefect, amount));
    }

    public void deleteItem(int itemID) throws Exception
    {
        items.remove(itemID);
        updateAmount();
    }
    public void reduceItemAmount(int itemID,int amountToReduce) throws Exception
    {
        items.get(itemID).reduce(amountToReduce);
        updateAmount();
    }
}


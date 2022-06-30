package StockModule.BusinessLogicLayer;

//import DAL.DAO.ProductDAO;

import DAL.DAOS.StockObjects.ProductDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Product {
    private String ID;
    private String name;
    private String manufacturer;
    private int amount;
    private int amountToNotify;
    private int categoryID;
    private Date supplyTime;
    private int demand; // Demand means amount of units sold per week.
    private List<Item> items;
    private ProductDao dao;

    public Product(String name, String manufacturer, int categoryID, Date supplyTime, int demand)
    {
        dao = new ProductDao();
        this.ID = name+manufacturer;
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = 0;
        this.amountToNotify = 0;
        this.categoryID = categoryID;
        this.supplyTime = supplyTime;
        this.demand = demand;
        this.items = new LinkedList<>();

        dao.insert(this);
    }

    //db
    public Product(String name, String manufacturer,int amountToNotify,  int categoryID, int demand, boolean isFromDB)
    {
        this.dao = new ProductDao();
        this.ID = name+manufacturer;
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = 0;
        this.amountToNotify = amountToNotify;
        this.categoryID = categoryID;
        this.demand = demand;
        this.items = new LinkedList<>();
        
        this.items = dao.loadItems(getID());
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
        dao.updateAmount(this);
        if(amount < demand)
        {
            throw new Exception("PLEASE NOTICE : Current amount is lower than product's demand. Please refill stock.");
        }

    }

    public Date getSupplyTime(){return supplyTime;}
    public int getAmount(){return amount;}
    public int getAmountToNotify(){return amountToNotify;}
    public String getManufacturer(){return manufacturer;}
    public String getName(){return name;}
    public List<Item> getItems(){
        return items;
    }

    public int getDemand(){
        return demand;
    }
    public int getCategoryID(){
        return categoryID;
    }

    public String getID(){
        return ID;
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


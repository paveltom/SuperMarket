package StockModule.BusinessLogicLayer;

import DAL.Stock_Suppliers.DAOS.StockObjects.ProductDao;
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
    private int demand; // Demand means amount of units sold per week.
    private List<Item> items;
    private ProductDao dao;

    public Product(String name, String manufacturer, int amountToNotify, int categoryID, int demand)
    {
        dao = new ProductDao();
        this.ID = (name+manufacturer);
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = 0;
        this.amountToNotify = amountToNotify;
        this.categoryID = categoryID;
        this.demand = demand;
        this.items = new LinkedList<>();

        dao.insert(this);
    }

    //db
    public Product(String name, String manufacturer, int amountToNotify,  int categoryID, int demand, boolean isFromDB)
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
        return "Product Name : " + name + " , Manufacturer : " + manufacturer + " , Amount : " + amount + " , Category ID : " + categoryID + " , Demand : " + demand+ "\n";
    }

    /**
     *
     * @return true if need to order shortage, false otherwise
     */
    public boolean updateAmount()
    {
        amount = 0;
        for(Item i : items){
            amount += i.getAmount();
        }
        dao.updateAmount(this);
        return amount < amountToNotify;
    }

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

    public boolean reduceItemAmount(int itemID,int amountToReduce) throws Exception
    {
        items.get(itemID).reduce(amountToReduce);
        return updateAmount();
    }

    public void setID(String ID) {
        this.ID = ID;
        dao.setID(this);
    }

    public void setName(String name) {
        this.name = name;
        dao.setName(this);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        dao.setManufacturer(this);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        dao.updateAmount(this);
    }

    public void setAmountToNotify(int amountToNotify) {
        this.amountToNotify = amountToNotify;
        dao.setAmountToNotify(this);
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        dao.setCategoryID(this);
    }


    public void setDemand(int demand) {
        this.demand = demand;
        dao.setDemand(this);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setDao(ProductDao dao) {
        this.dao = dao;
    }

    public String getAttributesString(){
        return ".0. ID "+"\n"+".1. Name"+"\n"+".2. Manufacturer"+"\n"+ ".3. Amount"+"\n"+".4. AmountToNotify"+"\n"+ ".5. CategoryID"+"\n"+ ".6. Demand";
    }

    public boolean updateAttributes(int Attribute, Object Value){
        switch (Attribute) {
            case 0: {
                try {
                    setID((String) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 1: {
                try {
                    setName((String) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 2: {
                try {
                    setManufacturer((String) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 3: {
                try {
                    setAmount((int) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 4: {
                try {
                    setAmountToNotify((int) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 5: {
                try {
                    setCategoryID((int) Value);
                } catch (Exception e) {
                    return false;
                }
            }
            case 6: {
                try {
                    setDemand((int) Value);
                } catch (Exception e) {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean updateItemAttribute(int ItemID, int Attribute, Object Value) {
        return items.get(ItemID).updateAttribute(Attribute, Value);
    }


}


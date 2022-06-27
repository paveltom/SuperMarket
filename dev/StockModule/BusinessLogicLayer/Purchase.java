package StockModule.BusinessLogicLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Purchase {
    private static int purchaseID;
    private Date purchaseDate;
    private Map<Integer, Map<Integer, Integer>> products; //<ProductID, <Fixed price, Actual Price (discount?)>>

    public Purchase(int _purchaseID, Date _purchaseDate, Map<Integer, Map<Integer, Integer>> _products)
    {
        purchaseID = _purchaseID;
        purchaseID++;
        purchaseDate = _purchaseDate;
        products = new HashMap<>();
        products.putAll(_products);
    }

    public int getID(){
        return purchaseID;
    }

    public String toString(){
        return "Purchase ID : " + purchaseID + " , Purchase Date : " + purchaseDate + "\n";
    }

}

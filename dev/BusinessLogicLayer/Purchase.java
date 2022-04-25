package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Purchase {
    private int purchaseID;
    private Date purchaseDate;
    private Map<Integer, Map<Integer, Integer>> products; //<ProductID, <Fixed price, Actual Price (discount?)>>

    public Purchase(int _purchaseID, Date _purchaseDate, Map<Integer, Map<Integer, Integer>> _products)
    {
        purchaseID = _purchaseID;
        purchaseDate = _purchaseDate;
        products = new HashMap<>();
        products.putAll(_products);
    }

    public String toString(){
        return "Purchase ID : " + purchaseID + " , Purchase Date : " + purchaseDate + "\n";
    }

}

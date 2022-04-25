package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Purchase {
    private int purchaseID;
    private Date purchaseDate;
    private Map<Integer, Map<Integer, Integer>> products; //<ProductID, <Fixed price, Actual Price (discount?)>>

    public Purchase(Date _purchaseDate,int _productID,int _fixedPrice,int _actualPrice)
    {
        //purchaseID =;
        purchaseDate = _purchaseDate;
        products = new HashMap<Integer,Map<Integer,Integer>>();


    }

    public String toString(){
        return "Purchase ID : " + purchaseID + " , Purchase Date : " + purchaseDate+ "\n";
    }

}

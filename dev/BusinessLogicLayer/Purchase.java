package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.Map;

public class Purchase {
    private int purchaseID;
    private Date purchaseDate;
    private Map<Integer, Map<Integer, Integer>> products; //<ProductID, <Fixed price, Actual Price (discount?)>>

    public String toString(){
        return "Purchase ID : " + purchaseID + " , Purchase Date : " + purchaseDate;
    }

}

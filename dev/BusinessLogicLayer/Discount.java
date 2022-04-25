package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Discount
{
    private int discountID;
    private int productID;
    private Date discountStartDate;
    private Date discountEndDate;
    private Map<Integer, Type> discount;

    Discount(int _productID, int _discountID, Date _discountStartDate, Date _discountEndDate,int _amount,Type _t)
    {
        productID = _productID;
        discountID = _discountID;
        discountStartDate = _discountStartDate;
        discountEndDate = _discountEndDate;
        discount = new HashMap<>();
        discount.put(_amount, _t);

    }



    public String toString(){
        Integer amount = (Integer) discount.keySet().toArray()[0];
        Type t = discount.get(amount);
        return "Discount ID : " + discountID + " , Product ID : " + productID + " , Start Date : " + discountStartDate + " , End Date : " + discountEndDate + " , Amount : " + amount + " " + t+ "\n";
    }
}

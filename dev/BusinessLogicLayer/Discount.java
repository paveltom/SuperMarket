package com.company.BusinessLogicLayer;

import java.util.Date;
import java.util.Map;

public class Discount
{
    private int productID;
    private int discountID;
    private Date discountStartDate;
    private Date discountEndDate;
    private Map<Integer, Type> discount;

    Discount(int _productID, int _discountID, Date _discountStartDate, Date _discountEndDate, Map<Integer, Type> _discount)
    {
        productID = _productID;
        discountID = _discountID;
        discountStartDate = _discountStartDate;
        discountEndDate = _discountEndDate;
        discount = _discount;

    }

    public String toString(){
        return "Discount ID : " + discountID + " , Product ID : " + productID + " , Start Date : " + discountStartDate + " , End Date : " + discountEndDate + " , Amount : NEED TO FIX";
    }
}

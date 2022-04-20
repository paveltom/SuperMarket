package com.company;

import java.util.Date;
import java.util.Map;

public class Discount {
    private int productID;
    private int discountID;
    private Date discountStartDate;
    private Date discountEndDate;
    private Map<Integer, Type> discount;
}

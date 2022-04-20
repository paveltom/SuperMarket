package com.company.BusinessLayer.LogicLayer;

import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

enum Job{PersonnelManager,Cashier,StoreKeeper,Usher,LogisticsManager,Driver}
public class Worker {

    public String Id;
    public String Name;
    public Job Job;
    public Boolean SMQualification;
    public String BankDetails;
    public Double Pay;
    public Date StartDate;

    public Worker(String _Id)
    {
        if(_Id.length() != 9 )
            throw new IllegalArgumentException("Id has to be 9 numbers");
    }
}

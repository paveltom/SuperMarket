package com.company.BusinessLayer.LogicLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Shift {
    public Date date;
    public Integer shiftType;
    public Worker manager;
    public HashMap<JobEnum, LinkedList<Worker>> workers;

    public Shift(Date date, Integer shiftType, Worker manager, HashMap<JobEnum, LinkedList<Worker>> workersList) {
        this.date = date;
        this.shiftType = shiftType;
        if(!manager.SMQualification) throw new IllegalArgumentException("The selected worker for the shift manager is not qualified");
        this.manager = manager;
        if(!workersList.containsKey(JobEnum.Cashier) || !workersList.containsKey(JobEnum.Usher) || !workersList.containsKey(JobEnum.StoreKeeper) || !workersList.containsKey(JobEnum.Driver))
            throw new IllegalArgumentException("Shift must contain cashier, usher, store keeper and driver");
        if(shiftType == 1) {
            if (workersList.containsKey(JobEnum.LogisticsManager) || workersList.containsKey(JobEnum.PersonnelManager))
                throw new IllegalArgumentException("Evening shift cant have Personnel Manager or Logistics Manager");
        }
        this.workers = workersList;
    }

    public Date getDate() {
        return date;
    }

    public Integer getShiftType() {
        return shiftType;
    }

    public Worker getManager() {
        return manager;
    }

    public HashMap<JobEnum, LinkedList<Worker>> getWorkers() {
        return workers;
    }
}

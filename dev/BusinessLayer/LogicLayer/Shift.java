package com.company.BusinessLayer.LogicLayer;

import java.text.SimpleDateFormat;
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

    public String toString() {
        String s="[\n" +
                "Date: "+new SimpleDateFormat("dd/MM/yyyy").format(date)+
                "\nShift type: ";
        if(this.shiftType == 0) s=s+"Morning";
        else s=s+"Evening";
        s+="\nManager: "+this.manager.Name+" ("+this.manager.Id+")+" +
                "\nWorkers:\n";
        for (JobEnum j:
                workers.keySet()) {
            s+="\t"+j+": ";
            for (Worker w:
                    workers.get(j)) {
                s+=w.Name+" ("+w.Id+"), ";
            }
            s=s.substring(0,s.length()-2);
            s+="\n";
        }
        s+="]";
        return s;
    }
}

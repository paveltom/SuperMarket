package com.company.BusinessLayer.ServiceLayer;

import com.company.BusinessLayer.LogicLayer.ShiftController;
import com.company.BusinessLayer.LogicLayer.Worker;

import java.util.HashMap;
import java.util.LinkedList;

public class ShiftService {
    private ShiftController sController;

    public ShiftService() {
        this.sController = new ShiftController();
    }

    public void addWorker(String w){
        sController.addWorker(w);
    }

    public void removeWorker(String w){
        sController.removeWorker(w);
    }

    public void changeAvailability(String w, String a){
        sController.changeAvailability(w,a);
    }

    public void addShift(String date, Integer shiftType, Worker manager, HashMap<String, LinkedList<Worker>> workersList){
        sController.addShift(date, shiftType, manager, workersList);
    }

    public String shiftHistory(){
        return sController.shiftHistory();
    }

    public void changeId(String o, String n){
        sController.changeId(o,n);
    }
    public String showAvailability(){
        return sController.showAvailability();
    }
}

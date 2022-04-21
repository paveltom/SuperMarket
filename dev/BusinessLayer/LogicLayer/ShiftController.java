package com.company.BusinessLayer.LogicLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ShiftController {
    public LinkedList<Shift> shifts;
    public HashMap<String,String> availability;

    public ShiftController() {
        this.shifts = new LinkedList<Shift>();
        this.availability = new HashMap<String,String>();
    }

    public void addShift(String date, Integer shiftType, Worker manager, HashMap<JobEnum, LinkedList<Worker>> workersList){
        Date d;
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            d = format1.parse(date);

        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Date isn't valid");
        }
        Shift s = new Shift(d,shiftType,manager,workersList);
        shifts.add(s);
    }

    public void changeAvailability(String w, String a){
        if(availability.containsKey(w))
            availability.replace(w,a);
        else
            throw new IllegalArgumentException("Worker doesn't exists");
    }

    public void addWorker(String w){
        if(!availability.containsKey(w))
            availability.put(w,"");
        else
            throw new IllegalArgumentException("Cant add worker to availability list");
    }

    public void removeWorker(String w){
        if(availability.containsKey(w))
            availability.remove(w);
        else
            throw new IllegalArgumentException("Cant remove worker because it doesn't exists");
    }

    public String shiftHistory(){
        String s="";
        for (Shift i:
             shifts) {
            s=s+i+"\n";
        }
        return s.substring(0,s.length()-1);
    }

    private void setShifts(LinkedList<Shift> shifts) {
        this.shifts = shifts;
    }

    private void setAvailability(HashMap<String, String> availability) {
        this.availability = availability;
    }

    public LinkedList<Shift> getShifts() {
        return shifts;
    }

    public HashMap<String, String> getAvailability() {
        return availability;
    }
}

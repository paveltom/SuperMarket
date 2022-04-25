package BusinessLayer.LogicLayer;

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

    public void addShift(String date, Integer shiftType, Worker manager, HashMap<String, LinkedList<Worker>> workersList){
        Date d;
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            d = format1.parse(date);

        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Date isn't valid");
        }
        HashMap<JobEnum, LinkedList<Worker>> wLs=new HashMap<>();
        for (String s:
                workersList.keySet()) {
            if(s.equals("PersonnelManager")) wLs.put(JobEnum.PersonnelManager, workersList.get(s));
            if(s.equals("Cashier")) wLs.put(JobEnum.Cashier, workersList.get(s));
            if(s.equals("StoreKeeper")) wLs.put(JobEnum.StoreKeeper, workersList.get(s));
            if(s.equals("Usher")) wLs.put(JobEnum.Usher, workersList.get(s));
            if(s.equals("LogisticsManager")) wLs.put(JobEnum.LogisticsManager, workersList.get(s));
            if(s.equals("Driver")) wLs.put(JobEnum.Driver, workersList.get(s));
        }
        for (Shift s:
                shifts) {
            if(s.getDate().equals(d) && s.getShiftType() == shiftType) throw new IllegalArgumentException("This shift already exists");
        }
        Shift s = new Shift(d,shiftType,manager,wLs);
        shifts.add(s);
    }

    public void changeId(String o, String n){
        if(availability.containsKey(o)) {
            String s = availability.get(o);
            availability.remove(o);
            availability.put(n,s);
        }
        else
            throw new IllegalArgumentException("The Id to change doesn't exists");
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
        if(shifts.size()==0) return "No shifts found";
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
    public String showAvailability(){
        String s = "Availability:\n";
        for (String id:
                availability.keySet()) {
            s+="\t"+id+": "+availability.get(id)+"\n";
        }
        return s.substring(0,s.length()-1);
    }
}

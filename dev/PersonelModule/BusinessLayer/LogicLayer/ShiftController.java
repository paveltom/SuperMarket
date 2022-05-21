package PersonelModule.BusinessLayer.LogicLayer;

import DAL.DALController;
import DAL.DTO.ShiftDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    public LinkedList<Shift> shifts;
    public HashMap<String,String> availability;
    private DALController dal;

    public ShiftController() {
        this.shifts = new LinkedList<Shift>();
        this.availability = new HashMap<String,String>();
        this.dal=DALController.getInstance();

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
        if(!manager.SMQualification) throw new IllegalArgumentException("The selected worker for the shift manager is not qualified");
        HashMap<String, LinkedList<String>> sLs=new HashMap<>();
        HashMap<JobEnum, LinkedList<String>> hm = new HashMap<>();
        for (JobEnum j:
                wLs.keySet()) {
            LinkedList<String> lnk = new LinkedList<>();
            for (Worker w:
                    wLs.get(j)) {
                if(!w.getJob().equals(j.toString()))
                    throw new IllegalArgumentException("Some workers can't work in their assigned role");
                lnk.add(w.getId());
            }
            hm.put(j,lnk);
            sLs.put(j.toString(),lnk);
        }
        List<ShiftDTO> l = dal.getShiftHistory();
        for (ShiftDTO s:
                l) {
            HashMap<JobEnum, LinkedList<String>> h = new HashMap<>();
            for (String st:
                    s.workers.keySet()) {
                if(st.equals("PersonnelManager")) h.put(JobEnum.PersonnelManager, s.workers.get(st));
                if(st.equals("Cashier")) h.put(JobEnum.Cashier, s.workers.get(st));
                if(st.equals("StoreKeeper")) h.put(JobEnum.StoreKeeper, s.workers.get(st));
                if(st.equals("Usher")) h.put(JobEnum.Usher, s.workers.get(st));
                if(st.equals("LogisticsManager")) h.put(JobEnum.LogisticsManager, s.workers.get(st));
                if(st.equals("Driver")) h.put(JobEnum.Driver, s.workers.get(st));
            }
            Shift shift = new Shift(s.date,s.type,s.shiftManager,h);
            for (Shift sh:
                    shifts) {
                if(!(sh.getDate().equals(shift.date) && sh.getShiftType().equals(shift.getShiftType()))) shifts.add(shift);
            }
        }
        for (Shift s:
                shifts) {
            if(s.getDate().equals(d) && s.getShiftType().equals(shiftType)) throw new IllegalArgumentException("This shift already exists");
        }
        Shift s = new Shift(d,shiftType,manager.getId(),hm);
        dal.addShift(new ShiftDTO(s.getDate(),s.getShiftType(),s.getManager(),sLs));
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
        List<ShiftDTO> l = dal.getShiftHistory();
        for (ShiftDTO s:
                l) {
            HashMap<JobEnum, LinkedList<String>> h = new HashMap<>();
            for (String st:
                    s.workers.keySet()) {
                if(st.equals("PersonnelManager")) h.put(JobEnum.PersonnelManager, s.workers.get(st));
                if(st.equals("Cashier")) h.put(JobEnum.Cashier, s.workers.get(st));
                if(st.equals("StoreKeeper")) h.put(JobEnum.StoreKeeper, s.workers.get(st));
                if(st.equals("Usher")) h.put(JobEnum.Usher, s.workers.get(st));
                if(st.equals("LogisticsManager")) h.put(JobEnum.LogisticsManager, s.workers.get(st));
                if(st.equals("Driver")) h.put(JobEnum.Driver, s.workers.get(st));
            }
            Shift shift = new Shift(s.date,s.type,s.shiftManager,h);
            for (Shift sh:
                    shifts) {
                if(!(sh.getDate().equals(shift.date) && sh.getShiftType().equals(shift.getShiftType()))) shifts.add(shift);
            }
        }
        if(shifts.size()==0) return "No shifts found";
        String s="";
        Date now=new Date();
        for (Shift i:
                shifts) {
            if(i.getDate().before(now)) {
                s = s + i + "\n";
            }
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

    public boolean isOccupide(String id){
        List<ShiftDTO> l = dal.getShiftHistory();
        for (ShiftDTO s:
                l) {
            HashMap<JobEnum, LinkedList<String>> h = new HashMap<>();
            for (String st:
                    s.workers.keySet()) {
                if(st.equals("PersonnelManager")) h.put(JobEnum.PersonnelManager, s.workers.get(st));
                if(st.equals("Cashier")) h.put(JobEnum.Cashier, s.workers.get(st));
                if(st.equals("StoreKeeper")) h.put(JobEnum.StoreKeeper, s.workers.get(st));
                if(st.equals("Usher")) h.put(JobEnum.Usher, s.workers.get(st));
                if(st.equals("LogisticsManager")) h.put(JobEnum.LogisticsManager, s.workers.get(st));
                if(st.equals("Driver")) h.put(JobEnum.Driver, s.workers.get(st));
            }
            Shift shift = new Shift(s.date,s.type,s.shiftManager,h);
            for (Shift sh:
                    shifts) {
                if(!(sh.getDate().equals(shift.date) && sh.getShiftType().equals(shift.getShiftType()))) shifts.add(shift);
            }
        }
        Date d = new Date();
        for (Shift s:
                shifts) {
            if(s.date.after(d)) {
                for (JobEnum j :
                        s.workers.keySet()) {
                    for (String w :
                            s.workers.get(j)) {
                        if (id.equals(w)) return true;
                    }
                }
            }
        }
        return false;
    }
}

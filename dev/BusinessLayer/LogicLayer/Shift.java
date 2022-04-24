package BusinessLayer.LogicLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class Shift {
    public Date date;
    public Integer shiftType;
    public String manager;
    public HashMap<JobEnum, LinkedList<String>> workers;

    public Shift(Date date, Integer shiftType, Worker manager, HashMap<JobEnum, LinkedList<Worker>> workersList) {
        this.date = date;
        this.shiftType = shiftType;
        if(!manager.SMQualification) throw new IllegalArgumentException("The selected worker for the shift manager is not qualified");
        this.manager = manager.getId();
        if(!workersList.containsKey(JobEnum.Cashier) || !workersList.containsKey(JobEnum.Usher) || !workersList.containsKey(JobEnum.StoreKeeper) || !workersList.containsKey(JobEnum.Driver))
            throw new IllegalArgumentException("Shift must contain cashier, usher, store keeper and driver");
        if(shiftType == 1) {
            if (workersList.containsKey(JobEnum.LogisticsManager) || workersList.containsKey(JobEnum.PersonnelManager))
                throw new IllegalArgumentException("Evening shift cant have Personnel Manager or Logistics Manager");
        }
        HashMap<JobEnum, LinkedList<String>> hm = new HashMap<>();
        for (JobEnum j:
             workersList.keySet()) {
            LinkedList<String> lnk = new LinkedList<>();
            for (Worker w:
                    workersList.get(j)) {
                if(!w.getJob().equals(j.toString()))
                    throw new IllegalArgumentException("Some workers can't work in their assigned role");
                lnk.add(w.getId());
            }
            hm.put(j,lnk);
        }
        this.workers = hm;
    }

    public Date getDate() {
        return date;
    }

    public Integer getShiftType() {
        return shiftType;
    }

    public String getManager() {
        return manager;
    }

    public HashMap<JobEnum, LinkedList<String>> getWorkers() {
        return workers;
    }

    public String toString() {
        String s="[\n" +
                "Date: "+new SimpleDateFormat("dd/MM/yyyy").format(date)+
                "\nShift type: ";
        if(this.shiftType == 0) s=s+"Morning";
        else s=s+"Evening";
        s+="\nManager: "+this.manager+
                "\nWorkers:\n";
        for (JobEnum j:
                workers.keySet()) {
            s+="\t"+j+": ";
            for (String w:
                    workers.get(j)) {
                s+=w+", ";
            }
            s=s.substring(0,s.length()-2);
            s+="\n";
        }
        s+="]";
        return s;
    }
}

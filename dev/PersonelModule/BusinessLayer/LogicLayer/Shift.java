package PersonelModule.BusinessLayer.LogicLayer;

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

    public Shift(Date date, Integer shiftType, String manager, HashMap<JobEnum, LinkedList<String>> workersList) {
        this.date = date;
        this.shiftType = shiftType;
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

    public String getManager() {
        return manager;
    }

    public HashMap<JobEnum, LinkedList<String>> getWorkers() {
        return workers;
    }

    public String toString() {
        String s="[\n" +
                "\tDate: "+new SimpleDateFormat("dd/MM/yyyy").format(date)+
                "\n\tShift type: ";
        if(this.shiftType == 0) s=s+"Morning";
        else s=s+"Evening";
        s+="\n\tManager: "+this.manager+
                "\n\tWorkers:\n";
        for (JobEnum j:
                workers.keySet()) {
            s+="\t\t"+j+": ";
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

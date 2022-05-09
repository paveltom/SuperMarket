package PersonelModule.BusinessLayer.ServiceLayer;

import PersonelModule.BusinessLayer.LogicLayer.Worker;

import java.util.HashMap;
import java.util.LinkedList;

public class Service {

    private ShiftService sService;
    public WorkerService wService;

    public Service()
    {
        wService = new WorkerService();
        sService = new ShiftService();
    }

    public String showAvailability(){
        return sService.showAvailability();
    }
    public String changeAvailability(String w, String a){
        try{sService.changeAvailability(w, a);}catch (Exception e){return e.getMessage(); }
        return "Changed availability successfully";
    }


    public String addShift(String date, Integer shiftType, String manager, String workersList){
        Worker m;
        try {
            m=wService.GetWorker(manager);
        }catch (Exception e){
            return e.getMessage();
        }
        String[] ls = workersList.split("\\|");
        HashMap<String,LinkedList<Worker>> wList = new HashMap<>();
        for (String s:
                ls) {
            String[] jLs=s.split(" ");
            if(!jLs[0].equals("PersonnelManager") && !jLs[0].equals("Cashier") && !jLs[0].equals("StoreKeeper")
                    && !jLs[0].equals("Usher") && !jLs[0].equals("LogisticsManager") && !jLs[0].equals("Driver"))
                return "Incorrect jobs";
            LinkedList<Worker> wls=new LinkedList<>();
            for(int i=1; i< jLs.length; i++) {
                try{
                    if(wls.contains(wService.GetWorker(jLs[i]))) return "Can't add the same worker more then once to the same shift";
                    wls.add(wService.GetWorker(jLs[i]));
                }catch (Exception e){return e.getMessage();}
            }
            if(wls.size()==0)
                return "Job must have at least 1 worker";
            if(wList.containsKey(jLs[0])) return "Shift cant contain the same job type more then once";
            wList.put(jLs[0], wls);
        }
        try {
            sService.addShift(date, shiftType, m, wList);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Added shift successfully";
    }

    public String shiftHistory(){
        return sService.shiftHistory();
    }

    /**
     * Public Function to make a new Worker and Add him
     * @param _Id - Id of the worker
     * @param _Name - The name of the worker
     * @param _Job - String that represents the job
     * @param _Qual - Boolean that shows if a workers is qualified to be a SM
     * @param _Bank - Bank details
     * @param _Pay - The salary per hour
     * @param _StartDate - The start date of the worker
     * @param _Social - Social conditions
     */
    public String AddWorker(String _Id,String _Name,String _Job,String _Qual,String _Bank,Double _Pay, String _StartDate,String _Social)
    {
        try{
            wService.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
            sService.addWorker(_Id);
            return "Added worker successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public String AddDriver(String _Id,String _Name,String _Job,String _Qual,String _Bank,Double _Pay, String _StartDate,String _Social, String zone, String license)
    {
        try{
            wService.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
            sService.addWorker(_Id);
            //TODO: send the data to Delivery Module
            return "Added worker successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }


    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     */
    public String DeleteWorker(String _Id)
    {
        try{
            wService.DeleteWorker(_Id);
            sService.removeWorker(_Id);
            return "Deleted worker successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
    public String ChangeId(String _oldId,String _newId)
    {
        try {
            wService.ChangeId(_oldId, _newId);
            sService.changeId(_oldId, _newId);
            return "Changed Id successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

    }
    /**
     * Function to change a workers name
     * @param _Id - The id of the worker
     * @param _newName - The name we want to change to
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeName(String _Id,String _newName)
    {
        return wService.ChangeName(_Id,_newName);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newJob - The Job we want to change to
     */
    public String ChangeJob(String _Id,String _newJob)
    {
        return wService.ChangeJob(_Id,_newJob);
    }

    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     */
    public String ChangeQual(String _Id,String _newQual)
    {
        if(_newQual.equals("yes"))
            return wService.ChangeQual(_Id,true);
        else if(_newQual.equals("no"))
            return wService.ChangeQual(_Id,false);
        else return "SMQualification bad format(Only 'yes' or 'no')";
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newBank - The new Bank details
     */
    public String ChangeBank(String _Id,String _newBank)
    {
        return wService.ChangeBank(_Id,_newBank);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newPay - The new salary per hour
     */
    public String ChangePay(String _Id,double _newPay)
    {
        return wService.ChangePay(_Id,_newPay);
    }

    /**
     * Function to change a workers start date
     * @param _Id - The id of the worker
     * @param _newStart - The new start date
     */
    public String ChangeStart(String _Id,String _newStart)
    {
        return wService.ChangeStart(_Id,_newStart);
    }

    /**
     * Function to change a workers social conditions
     * @param _Id - The id of the worker
     * @param _newSocial - The new social conditions
     */
    public String ChangeSocial(String _Id,String _newSocial)
    {
        return wService.ChangeSocial(_Id,_newSocial);
    }

    /**
     * Function to get details on a specific worker and print them
     * @param _Id - The id of the worker
     */
    public String GetWorkerString(String _Id)
    {
        return wService.getWorkerString(_Id);
    }

    /**
     * Function to print all workers details
     */
    public String GetAllWorkersString()
    {
        return wService.GetAllWorkersString();
    }

    /**
     * Function to get a string of workers in a certain job
     * @param _Job - the job we want
     * @return - a string of the workers id and name
     */
    public String GetWorkersByJob(String _Job)
    {
        return wService.GetWorkerByJob(_Job);
    }
}

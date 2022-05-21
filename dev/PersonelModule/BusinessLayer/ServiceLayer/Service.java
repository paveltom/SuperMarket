package PersonelModule.BusinessLayer.ServiceLayer;

import DAL.DALController;
import DeliveryModule.Facade.FacadeObjects.FacadeDate;
import DeliveryModule.Facade.FacadeObjects.FacadeDriver;
import PersonelModule.BusinessLayer.LogicLayer.Worker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Service {

    private ShiftService sService;
    public WorkerService wService;
    private DeliveryModule.Facade.Service DMService;

    public Service()
    {
        wService = new WorkerService();
        sService = new ShiftService();
    }

    public void AddDmService(DeliveryModule.Facade.Service _Service)
    {
        this.DMService = _Service;
    }
    public String showAvailability(){
        try {
            wService.loadAllWorkers();
        }
        catch (Exception e){
            return e.getMessage();
        }
        String DriverShifts = "";
        List<String> wIds = wService.getWorkersIdByJob("Driver");
        for (String wid:
                wIds) {
            boolean first = true;
            DriverShifts +="Driver "+wService.GetWorkerNameById(wid) +" id:"+wid+" future delivers are:";
            String futureShifts = DALController.getInstance().getDriverFutureShifts(wid);
            String[] SingleShift = futureShifts.split("#");
            List<String> UpdatedFutureShifts = new LinkedList<String>();
            for (String oneShift:
                    SingleShift) {
                String[] dateAndType = oneShift.split(",");
                try {
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                    Date futureDate = format1.parse(dateAndType[0]);
                    if(!futureDate.before(new Date()))
                    {
                        //if we get here it means that the futureDate is after today
                        if(first)
                        {
                            first = false;
                            if(dateAndType[1].equals("0"))
                                DriverShifts += dateAndType[0] + ",Morning";
                            else {
                                DriverShifts += dateAndType[0] + ",Night";
                            }
                        }
                        else {
                            if(dateAndType[1].equals("0"))
                                DriverShifts += "|"+dateAndType[0] + ",Morning";
                            else {
                                DriverShifts += "|"+dateAndType[0] + ",Night";
                            }
                        }
                        UpdatedFutureShifts.add(oneShift);
                    }
                }
                catch (Exception e)
                {};
            }
            String[] UpdateFutureShiftsArr = new String[UpdatedFutureShifts.size()];
            UpdatedFutureShifts.toArray(UpdateFutureShiftsArr);
            //We update the FutureShiftsInDB
            DALController.getInstance().rewriteDriverFutureShifts(wid,UpdateFutureShiftsArr);

        }
        String Availability =  sService.showAvailability();
        String AllAvail = DriverShifts +"\n"+Availability;
        return AllAvail;
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
        List<String> DriverIds = wService.getWorkersIdByJob("Driver");
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
                    DriverIds.remove(jLs[i]);
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
        //Adding Constraints to drivers
        for (String DriverId:
                DriverIds) {
            try {
                int day = Integer.valueOf(date.split("/")[0]);
                int month = Integer.valueOf(date.split("/")[1]);
                int year = Integer.valueOf(date.split("/")[2]);
                DMService.addConstraints(DriverId,new FacadeDate(day,month,year),shiftType);
            }
            catch (Exception e)
            {
                return "Date is invalid in addShift";
            }
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
            if(_Job.equals("Driver"))
                return "Called Wrong Method";
            wService.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
            sService.addWorker(_Id);
            return "Added worker successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to add a driver
     * @param _Id - Id of the worker
     * @param _Name - The name of the worker
     * @param _Job - String that represents the job
     * @param _Qual - Boolean that shows if a workers is qualified to be a SM
     * @param _Bank - Bank details
     * @param _Pay - The salary per hour
     * @param _StartDate - The start date of the worker
     * @param _Social - Social conditions
     * @param vehicleCategory - Vehicle category as stated in DM
     * @param livingArea - the living area of the driver
     * @param cellphone - the cellphone of the driver
     * @return a string of a successful msg or a string of the reason it failed
     */
    public String AddDriver(String _Id,String _Name,String _Job,String _Qual,String _Bank,Double _Pay, String _StartDate,String _Social, String vehicleCategory, String livingArea, String cellphone)
    {
        try{
            wService.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
            sService.addWorker(_Id);
            FacadeDriver fDriver = new FacadeDriver(_Id,_Name,vehicleCategory,livingArea,cellphone);
            DMService.addDriver(fDriver);
            return "Added Driver successfully";
        }
        catch (Exception e)
        {
            try {
                wService.DeleteWorker(_Id);
                sService.removeWorker(_Id);
                DMService.removeDriver(_Id);
            }
            catch (Exception e1)
            {

            }
            return e.getMessage();
        }
    }


    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     */
    public String DeleteWorker(String _Id)
    {
        Calendar cal = Calendar.getInstance();
        try{


            if (DMService.isOccupied(_Id, cal.get(Calendar.MONTH) + 1,LocalDate.now().getDayOfMonth()))
                return "Cannot delete worker because he has Jobs in the future";
            if (sService.isOccupied(_Id))
                return "Cannot delete worker because he has Jobs in the future";
            wService.DeleteWorker(_Id);
            sService.removeWorker(_Id);
            DMService.removeDriver(_Id);
            return "Deleted worker successfully";
        }
        catch (Exception e)
        {
            return "Wasnt able to delete worker check input";
        }
    }

    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
   /* public String ChangeId(String _oldId,String _newId)
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

    }*/
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
        try{
            Calendar cal = Calendar.getInstance();
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            if (DMService.isOccupied(_Id, cal.get(Calendar.MONTH) + 1,LocalDate.now().getDayOfMonth()))
                return "Cannot change worker because he has Jobs in the future";
            if (sService.isOccupied(_Id))
                return "Cannot change worker because he has Jobs in the future";
            if(wService.getWorkerJobById(_Id).equals("Driver"))
            {
                wService.ChangeJob(_Id,_newJob);
                DMService.removeDriver(_Id);
            }
            else wService.ChangeJob(_Id,_newJob);
            return "Changed job successfully";}
        catch (Exception e ){
            return e.getMessage();
        }
    }

    /**
     * Function to change job to driver
     * @param _Id - id of the worker
     * @param vehicleCategory - his license
     * @param livingArea - his living area
     * @param cellphone - his cellphone
     * @return  A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeToDriver(String _Id,String vehicleCategory, String livingArea, String cellphone)
    {
        try {
            Calendar cal = Calendar.getInstance();
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            if (DMService.isOccupied(_Id, cal.get(Calendar.MONTH) + 1,LocalDate.now().getDayOfMonth()))
                return "Cannot change worker because he has Jobs in the future";
            if (sService.isOccupied(_Id))
                return "Cannot change worker because he has Jobs in the future";
            wService.ChangeJob(_Id, "Driver");
            FacadeDriver fDriver = new FacadeDriver(_Id,wService.GetWorkerNameById(_Id),vehicleCategory,livingArea,cellphone);
            DMService.addDriver(fDriver);
            return "Changed job successfully";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }
    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     */
    public String ChangeQual(String _Id,String _newQual)
    {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        if (DMService.isOccupied(_Id, cal.get(Calendar.MONTH) + 1,LocalDate.now().getDayOfMonth()))
            return "Cannot change worker because he has Jobs in the future";
        if (sService.isOccupied(_Id))
            return "Cannot change worker because he has Jobs in the future";
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

    /**
     * Function to add Driver Future Deliverys
     * @param _Id - id of the driver
     * @param date - the date of the delivery
     * @param shiftType - the shift type / time of the delivery(day = 0 / night = 1)
     */
    public void AddDriverFuture(String _Id, FacadeDate date,Integer shiftType)
    {
        try {
            DALController.getInstance().addDriverFutureShifts(_Id,"#"+date.getDay()+"/"+date.getMonth()+"/"+date.getYear()+","+shiftType);
        }
        catch (Exception e)
        {
            //To check if I need to add something to here
        }
    }
}

package PersonelModule.BusinessLayer.ServiceLayer;

import PersonelModule.BusinessLayer.LogicLayer.Worker;
import PersonelModule.BusinessLayer.LogicLayer.WorkerController;

public class WorkerService {

    public WorkerController wController;

    /**
     * Constructor for Worker Service
     */
    public WorkerService(){
        wController = new WorkerController();
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
     * @return A message if the addition was successful or an explanation why it wasn't
     */
    public void AddWorker(String _Id,String _Name,String _Job,String _Qual,String _Bank,Double _Pay, String _StartDate,String _Social)
    {
        if(_Qual.equals("yes"))
        {
            wController.AddWorker(_Id,_Name,_Job,true,_Bank,_Pay,_StartDate,_Social);
        }
        else {
            wController.AddWorker(_Id,_Name,_Job,false,_Bank,_Pay,_StartDate,_Social);
        }
    }

    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     * @return A message if the deletion was successful or an explanation why it wasn't
     */
    public void DeleteWorker(String _Id)
    {
        wController.DeleteWorker(_Id);
    }

    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     * @return A message if the change was successful or an explanation why it wasn't
     */
   /* public void ChangeId(String _oldId,String _newId)
    {
        wController.ChangeId(_oldId,_newId);
    }*/

    /**
     * Function to change a workers name
     * @param _Id - The id of the worker
     * @param _newName - The name we want to change to
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeName(String _Id,String _newName)
    {
        try{
            wController.ChangeName(_Id,_newName);
            return  "Changed name successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newJob - The Job we want to change to
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String  ChangeJob(String _Id,String _newJob)
    {
        try{
            wController.ChangeJob(_Id,_newJob);
            return  "Changed job successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeQual(String _Id,boolean _newQual)
    {
        try{
            wController.ChangeQual(_Id,_newQual);
            return  "Changed SMQualification successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newBank - The new Bank details
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeBank(String _Id,String _newBank)
    {
        try{
            wController.ChangeBank(_Id,_newBank);
            return  "Changed bank details successfully";

        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newPay - The new salary per hour
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangePay(String _Id,double _newPay)
    {
        try{
            wController.ChangePay(_Id,_newPay);
            return  "Changed pay successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers start date
     * @param _Id - The id of the worker
     * @param _newStart - The new start date
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeStart(String _Id,String _newStart)
    {
        try{
            wController.ChangeStart(_Id,_newStart);
            return  "Changed start date successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to change a workers social conditions
     * @param _Id - The id of the worker
     * @param _newSocial - The new social conditions
     * @return A message if the change was successful or an explanation why it wasn't
     */
    public String ChangeSocial(String _Id,String _newSocial)
    {
        try{
            wController.ChangeSocial(_Id,_newSocial);
            return  "Changed social conditions successfully";
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
    }

    /**
     * Function to get a workers details by his Id
     * @param _Id - The id of the worker
     * @return A string of the worker's details or an error msg
     */
    public String getWorkerString(String _Id)
    {
        try{
            return wController.GetWorkerToString(_Id);
        }
        catch (Exception e)
        {
           return e.getMessage();
        }
    }

    /**
     * Function to get all the workers details
     * @return A string of all the workers details
     */
    public String GetAllWorkersString()
    {
        return wController.GetAllWorkersToString();
    }

    /**
     * Function to get a worker by his id
     * @param _Id - The id of the worker
     * @return A worker object or an error if he doesnt exist
     */
    public Worker GetWorker(String _Id)
    {
        return wController.getWorker(_Id);
    }

    /**
     * Function to get a string of workers in a certain job
     * @param _Job - the job we want
     * @return - a string of the workers id and name
     */
    public String GetWorkerByJob(String _Job)
    {
        return wController.getWorkerByJov(_Job);
    }
}

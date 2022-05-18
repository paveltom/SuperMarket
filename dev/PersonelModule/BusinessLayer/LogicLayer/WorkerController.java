package PersonelModule.BusinessLayer.LogicLayer;

import DAL.DALController;
import DAL.DTO.WorkerDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkerController {

    public Map<String,Worker> AllWorkers;

    public WorkerController()
    {
        AllWorkers = new HashMap<>() ;
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
    public void AddWorker(String _Id,String _Name,String _Job,Boolean _Qual,String _Bank,Double _Pay, String _StartDate,String _Social)
    {
        if(AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("A worker with this Id Already Exists");
        WorkerDTO wDto = DALController.getInstance().getWorker(_Id);
        if(wDto != null)
        {
            AllWorkers.put(_Id,new Worker(wDto));
            throw new IllegalArgumentException("A worker with this Id Already Exists");
        }
        Worker newWorker = new Worker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
        AllWorkers.put(_Id,newWorker);
    }

    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     */
    public void DeleteWorker(String _Id)
    {
        if(!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        WorkerDTO wDto = DALController.getInstance().getWorker(_Id);
        if(wDto == null)
        {
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        }
        if(AllWorkers.containsKey(_Id))
            AllWorkers.remove(_Id);

    }
    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
    public void ChangeId(String _oldId,String _newId)
    {
       /* if(!AllWorkers.containsKey(_oldId))
            throw new IllegalArgumentException("There is no worker with id "+_oldId);
        if(AllWorkers.containsKey(_newId))
            throw new IllegalArgumentException("A worker with id :"+_newId+" Already Exist, Change failed");
        Worker changeWorker = AllWorkers.get(_oldId);
        changeWorker.setId(_newId);
        AllWorkers.remove(_oldId);
        AllWorkers.put(_newId,changeWorker);*/
    }

    /**
     * Function to change a workers name
     * @param _Id - The id of the worker
     * @param _newName - The name we want to change to
     */
    public void ChangeName(String _Id,String _newName)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setName(_newName);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newJob - The Job we want to change to
     */
    public void ChangeJob(String _Id,String _newJob)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setJob(_newJob);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     */
    public void ChangeQual(String _Id,boolean _newQual)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setSMQualification(_newQual);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newBank - The new Bank details
     */
    public void ChangeBank(String _Id,String _newBank)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setBankDetails(_newBank);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newPay - The new salary per hour
     */
    public void ChangePay(String _Id,double _newPay)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setPay(_newPay);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers start date
     * @param _Id - The id of the worker
     * @param _newStart - The new start date
     */
    public void ChangeStart(String _Id,String _newStart)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setStartDate(_newStart);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to change a workers social conditions
     * @param _Id - The id of the worker
     * @param _newSocial - The new social conditions
     */
    public void ChangeSocial(String _Id,String _newSocial)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        Worker changeWorker = AllWorkers.get(_Id);
        changeWorker.setSocialConditions(_newSocial);
        AllWorkers.replace(_Id,changeWorker);
    }

    /**
     * Function to get toString of a worker via his Id
     * @param _Id - Id of the worker we want to get
     * @return A string of the worker or an Exception if no such worker exists
     */
    public String GetWorkerToString(String _Id)
    {
        if (!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id);
        return AllWorkers.get(_Id).toString();
    }

    /**
     * Function to get all workers toString
     * @return A string of all the workers details
     */
    public String GetAllWorkersToString()
    {
        Boolean first = true;
        String allWorkersString = "";
        for (String key:
             AllWorkers.keySet()) {
            if(first)
            {
                allWorkersString += AllWorkers.get(key).toString();
                first = false;
            }
            else {
                allWorkersString += "\n"+AllWorkers.get(key).toString();

            }
        }
        return allWorkersString;
    }

    /**
     * Function to get a worker
     * @param _Id - the id of the worker
     * @return The worker object or an error
     */
    public Worker getWorker(String _Id)
    {
        if(!AllWorkers.containsKey(_Id))
            throw new IllegalArgumentException("There is no worker with id "+_Id );
        return AllWorkers.get(_Id);
    }

    /**
     * Function to get all workers with a certain job
     * @param _Job - the job
     * @return a string of all the workers that work in the required job
     */
    public String getWorkerByJov(String _Job)
    {
        Boolean first = true;
        String AllWorkersInJob = "";
        for (String key:
             AllWorkers.keySet()) {
            if(first)
            {
                if(AllWorkers.get(key).getJob().equals(_Job)) {
                    AllWorkersInJob += AllWorkers.get(key).getId() +" "+ AllWorkers.get(key).getName();
                    first = false;
                }
            }
            else {
                if(AllWorkers.get(key).getJob().equals(_Job)) {
                    AllWorkersInJob +="\n"+ AllWorkers.get(key).getId() + " " + AllWorkers.get(key).getName();
                }

            }
        }
        return AllWorkersInJob;
    }

    /**
     * Function to get a list of ids of worker by job
     * @param _Job - the job
     * @return List of id's
     */
    public List<String> getWorkerIdByJob(String _Job)
    {
        List<String> driverIds = new LinkedList<String>();
        for (Worker w:
                AllWorkers.values()) {
            if(w.getJob() == "Driver")
                driverIds.add(w.getId());
        }
        return driverIds;
    }

}

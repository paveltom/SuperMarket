package com.company.BusinessLayer.LogicLayer;

import java.util.HashMap;
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
        AllWorkers.remove(_Id);
    }
    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
    public void ChangeId(String _oldId,String _newId)
    {
        if(!AllWorkers.containsKey(_oldId))
            throw new IllegalArgumentException("There is no worker with id "+_oldId);
        if(AllWorkers.containsKey(_newId))
            throw new IllegalArgumentException("A worker with id :"+_newId+"Already Exist, Change failed");
        Worker changeWorker = AllWorkers.get(_oldId);
        changeWorker.setId(_newId);
        AllWorkers.remove(_oldId);
        AllWorkers.put(_newId,changeWorker);
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


}

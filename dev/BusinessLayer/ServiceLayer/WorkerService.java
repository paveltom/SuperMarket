package com.company.BusinessLayer.ServiceLayer;

import com.company.BusinessLayer.LogicLayer.WorkerController;

public class WorkerService {

    private WorkerController wController;

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
     */
    public void AddWorker(String _Id,String _Name,String _Job,Boolean _Qual,String _Bank,Double _Pay, String _StartDate,String _Social)
    {
        try{
            wController.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     */
    public void DeleteWorker(String _Id)
    {
        try{
            wController.DeleteWorker(_Id);
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
    public void ChangeId(String _oldId,String _newId)
    {
        try{
            wController.ChangeId(_oldId,_newId);
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Function to change a workers name
     * @param _Id - The id of the worker
     * @param _newName - The name we want to change to
     */
    public void ChangeName(String _Id,String _newName)
    {
        try{
            wController.ChangeName(_Id,_newName);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newJob - The Job we want to change to
     */
    public void ChangeJob(String _Id,String _newJob)
    {
        try{
            wController.ChangeJob(_Id,_newJob);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     */
    public void ChangeQual(String _Id,boolean _newQual)
    {
        try{
            wController.ChangeQual(_Id,_newQual);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newBank - The new Bank details
     */
    public void ChangeBank(String _Id,String _newBank)
    {
        try{
            wController.ChangeBank(_Id,_newBank);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newPay - The new salary per hour
     */
    public void ChangePay(String _Id,double _newPay)
    {
        try{
            wController.ChangePay(_Id,_newPay);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers start date
     * @param _Id - The id of the worker
     * @param _newStart - The new start date
     */
    public void ChangeStart(String _Id,String _newStart)
    {
        try{
            wController.ChangeStart(_Id,_newStart);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Function to change a workers social conditions
     * @param _Id - The id of the worker
     * @param _newSocial - The new social conditions
     */
    public void ChangeSocial(String _Id,String _newSocial)
    {
        try{
            wController.ChangeSocial(_Id,_newSocial);
        }
        catch(Exception e)
        {

        }
    }
}

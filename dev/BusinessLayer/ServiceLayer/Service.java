package com.company.BusinessLayer.ServiceLayer;

public class Service {

    private WorkerService wService;

    public Service()
    {
        wService = new WorkerService();
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
        wService.AddWorker(_Id,_Name,_Job,_Qual,_Bank,_Pay,_StartDate,_Social);
    }


    /**
     * Function to delete a worker
     * @param _Id - The id of the worker we want to delete
     */
    public void DeleteWorker(String _Id)
    {
        wService.DeleteWorker(_Id);
    }

    /**
     * Function to change Id incase there was a mistake
     * @param _oldId - Old and incorrect Id
     * @param _newId - New and correct Id
     */
    public void ChangeId(String _oldId,String _newId)
    {
        wService.ChangeId(_oldId,_newId);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newJob - The Job we want to change to
     */
    public void ChangeJob(String _Id,String _newJob)
    {
        wService.ChangeJob(_Id,_newJob);
    }

    /**
     * Function to change a workers SMQualification
     * @param _Id - The id of the worker
     * @param _newQual - The new qualification
     */
    public void ChangeQual(String _Id,boolean _newQual)
    {
        wService.ChangeQual(_Id,_newQual);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newBank - The new Bank details
     */
    public void ChangeBank(String _Id,String _newBank)
    {
        wService.ChangeBank(_Id,_newBank);
    }

    /**
     * Function to change a workers Job
     * @param _Id - The id of the worker
     * @param _newPay - The new salary per hour
     */
    public void ChangePay(String _Id,double _newPay)
    {
        wService.ChangePay(_Id,_newPay);
    }

    /**
     * Function to change a workers start date
     * @param _Id - The id of the worker
     * @param _newStart - The new start date
     */
    public void ChangeStart(String _Id,String _newStart)
    {
        wService.ChangeStart(_Id,_newStart);
    }

    /**
     * Function to change a workers social conditions
     * @param _Id - The id of the worker
     * @param _newSocial - The new social conditions
     */
    public void ChangeSocial(String _Id,String _newSocial)
    {
        wService.ChangeSocial(_Id,_newSocial);
    }

    /**
     * Function to get details on a specific worker and print them
     * @param _Id - The id of the worker
     */
    public void GetWorkerString(String _Id)
    {
        System.out.println(wService.getWorkerString(_Id));
    }

    /**
     * Function to print all workers details
     */
    public void GetAllWorkersString()
    {
        System.out.println(wService.GetAllWorkersString());
    }
}

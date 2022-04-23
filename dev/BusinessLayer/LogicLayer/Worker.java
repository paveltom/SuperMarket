package com.company.BusinessLayer.LogicLayer;

import java.text.SimpleDateFormat;
import java.util.Date;

enum JobEnum{PersonnelManager,Cashier,StoreKeeper,Usher,LogisticsManager,Driver}
public class Worker {

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public JobEnum getJob() {
        return Job;
    }

    public String getSMQualification() {
        if(SMQualification)
            return "yes";
        else return "no";
    }

    public String getBankDetails() {
        return BankDetails;
    }

    public Double getPay() {
        return Pay;
    }

    public String getStartDate() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        return format1.format(this.StartDate);
    }

    public String getSocialConditions() {
        return SocialConditions;
    }

    public void setId(String id) {
        if(id.length() != 9 )
            throw new IllegalArgumentException("Id has to be 9 numbers");
        else Id = id;
    }

    public void setName(String name) {
        if(name == null)
            throw new IllegalArgumentException("Name cannot be null");
        else Name = name;
    }

    public void setJob(String job) {
        try{
            this.Job = JobEnum.valueOf(job);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Job isn't valid");
        }
    }

    public void setSMQualification(Boolean SMQualification) {
        if(SMQualification == null)
            throw new IllegalArgumentException("SMQualification cannot be null");
        else this.SMQualification = SMQualification;
    }

    public void setBankDetails(String bankDetails) {
        if(bankDetails == null)
            throw new IllegalArgumentException("Bank Details cannot be null");
        else BankDetails = bankDetails;
    }

    public void setPay(Double pay) {
        if(pay <= 29.12)
            throw new IllegalArgumentException("Pay cannot be below 29.12");
        else Pay = pay;
    }

    public void setStartDate(String startDate) {
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            this.StartDate = format1.parse(startDate);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Date isn't valid");
        }
    }

    public void setSocialConditions(String socialConditions) {
        if(socialConditions == null)
            throw new IllegalArgumentException("Social Conditions cannot be null");
        else SocialConditions = socialConditions;
    }

    public String Id;
    public String Name;
    public JobEnum Job;
    public Boolean SMQualification;
    public String BankDetails;
    public Double Pay;
    public Date StartDate;
    public String SocialConditions;

    /**
     * Public Constractor to make a new Worker
     * @param _Id - Id of the worker
     * @param _Name - The name of the worker
     * @param _Job - String that represents the job
     * @param _Qual - Boolean that shows if a workers is qualified to be a SM
     * @param _Bank - Bank details
     * @param _Pay - The salary per hour
     * @param _StartDate - The start date of the worker
     * @param _Social - Social conditions
     */
    public Worker(String _Id,String _Name,String _Job,Boolean _Qual,String _Bank,Double _Pay, String _StartDate,String _Social)
    {
        if(_Id.length() != 9 )
            throw new IllegalArgumentException("Id has to be 9 numbers");
        if(_Name == null)
            throw new IllegalArgumentException("Name cannot be null");
        try{
            this.Job = JobEnum.valueOf(_Job);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Job isn't valid");
        }
        if(_Qual == null)
            throw new IllegalArgumentException("SMQualification cannot be null");
        if(_Bank == null)
            throw new IllegalArgumentException("Bank Details cannot be null");
        if(_Pay <= 29.12)
            throw new IllegalArgumentException("Pay cannot be below 29.12");
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            this.StartDate = format1.parse(_StartDate);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Date isn't valid");
        }
        if(_Social == null)
            throw new IllegalArgumentException("Social Conditions cannot be null");

        this.Id = _Id;
        this.Name = _Name;
        this.SMQualification = _Qual;
        this.BankDetails = _Bank;
        this.Pay = _Pay;
        this.SocialConditions = _Social;


    }

    public String toString()
    {
        return "Id:"+this.Id+"\nName:"+this.Name+"\nJob:"+this.Job+"\nSMQualification:"+this.getSMQualification()+"\nBank Details:"+this.BankDetails+"\nPay:"+this.Pay+"\nStartDate"+this.getStartDate()+"\nSocial Conditions:"+this.SocialConditions;
    }

}

package DAL.Delivery_Personnel.DTO;

public class WorkerDTO implements DTO {
    public String wId;

    public void setName(String name) {
        Name = name;
    }

    public void setBankDetails(String bankDetails) {
        BankDetails = bankDetails;
    }

    public void setSocialConditions(String socialConditions) {
        SocialConditions = socialConditions;
    }

    public void setJob(String job) {
        Job = job;
    }

    public void setSMQual(String SMQual) {
        this.SMQual = SMQual;
    }

    public void setPay(String pay) {
        Pay = pay;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    private String Name,BankDetails,SocialConditions,Job,SMQual,Pay,StartDate,Availability;

    public WorkerDTO(String _id,String _name,String _Job,String _SMQual,String _BankDetails,String _Pay,String _startDate,String _SocialCondition)
    {
        wId = _id;
        Name = _name;
        Job = _Job;
        SMQual = _SMQual;
        BankDetails = _BankDetails;
        Pay = _Pay;
        StartDate = _startDate;
        SocialConditions = _SocialCondition;
        Availability = "";
    }

    public WorkerDTO(String _id,String _name,String _Job,String _SMQual,String _BankDetails,String _Pay,String _startDate,String _SocialCondition,String _Avail)
    {
        wId = _id;
        Name = _name;
        Job = _Job;
        SMQual = _SMQual;
        BankDetails = _BankDetails;
        Pay = _Pay;
        StartDate = _startDate;
        SocialConditions = _SocialCondition;
        Availability = _Avail;
    }
    public String[] getParams()
    {
        return new String[]{wId, Name, Job, SMQual, BankDetails, Pay, StartDate, SocialConditions,Availability};
    }

    public String getParamVal(String paramName)
    {
        switch (paramName)
        {
            case "Id":
                return wId;
            case "Name":
                return Name;
            case "Job":
                return Job;
            case "SMQual":
                return SMQual;
            case "BankDetails":
                return BankDetails;
            case "Pay":
                return Pay;
            case "StartDate":
                return StartDate;
            case "SocialConditions":
                return SocialConditions;
            case "Availability":
                return Availability;
            default:
                return wId;
        }
    }
    @Override
    public String getKey() {
        return wId;
    }

    @Override
    public String toString() {
        return wId+":\nName:"+Name+":\nJob:"+Job+":\nSMQual:"+SMQual+":\nBankDetails:"+BankDetails+":\nPay:"+Pay+":\nStartDate:"+StartDate+":\nSocialCondiditons:"+SocialConditions;
    }

    public void changeAvailability(String a) {
        this.Availability=a;
    }
}

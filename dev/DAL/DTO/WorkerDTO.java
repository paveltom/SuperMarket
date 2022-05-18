package DAL.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkerDTO implements DTO {
    public String wId;
    private String Name,BankDetails,SocialConditions,Job,SMQual,Pay,StartDate;

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
    }

    public String[] getParams()
    {
        return new String[]{wId, Name, Job, SMQual, BankDetails, Pay, StartDate, SocialConditions};
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
}

package DAL.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkerDTO implements DTO {
    public String wId;
    private String name,BankDetails,SocialConditions,Job;
    private boolean SMQual;
    private double Pay;
    private Date startDate;

    public WorkerDTO(String _id,String _name,String _Job,String _SMQual,String _BankDetails,String _Pay,String _startDate,String _SocialCondition)
    {
        wId = _id;
        name = _name;
        Job = _Job;
        if(_SMQual.equals("yes"))
            SMQual = true;
        else  SMQual = false;
        BankDetails = _BankDetails;
        Pay = Double.parseDouble(_Pay);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            startDate = format1.parse(_startDate);
        }
        catch (Exception e)//The try will always succeed so we cant get an exception
        {}
        SocialConditions = _SocialCondition;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}

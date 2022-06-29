package DAL.Delivery_Personnel.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ShiftDTO implements DTO {

    public Date date;
    public Integer type;
    public String shiftManager;
    public HashMap<String, LinkedList<String>> workers;

    public ShiftDTO(Date _date, Integer _type, String _shiftManager, HashMap<String, LinkedList<String>> _workers){
        this.date=_date;
        this.type=_type;
        this.shiftManager=_shiftManager;
        this.workers=_workers;
    }




    @Override
    public String getKey() {
        return ""+new SimpleDateFormat("dd/MM/yyyy").format(date)+"#"+type.toString();
    }

    @Override
    public String toString() {
        return "ShiftDTO";
    }
}

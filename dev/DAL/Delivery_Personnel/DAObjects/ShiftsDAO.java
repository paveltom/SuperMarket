package DAL.Delivery_Personnel.DAObjects;

import DAL.Delivery_Personnel.DTO.DTO;
import DAL.Delivery_Personnel.DTO.ShiftDTO;
import DAL.Delivery_Personnel.DataBaseConnection;
import DAL.Delivery_Personnel.IdentityMap;

import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftsDAO implements IDAO{
    private IdentityMap im;
    private DataBaseConnection con;

    public ShiftsDAO(){
        im=new IdentityMap();
        con = new DataBaseConnection();
    }

    @Override
    public DTO getObj(String[] keys) {
        DTO obj = im.getCachedObj(keys[0]+"#"+keys[1]);
        if(obj == null) return loadObjectFromDB(keys);
        return obj;
    }

    @Override
    public DTO loadObjectFromDB(String[] key) {
        List<String[]> shifts = con.select("Shifts", new String[]{"Date", "Type"},new String[]{key[0],key[1]});
        if(shifts==null || shifts.size()==0) return null;
        String[] sDetails = shifts.get(0);
        Date d;
        try{
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            d = format1.parse(sDetails[0]);

        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Date isn't valid");
        }
        String w = sDetails[4];
        HashMap<String,LinkedList<String>> wList = new HashMap<>();
        String[] ls=w.split("\\|");
        for (String s:
                ls) {
            String[] jLs=s.split(" ");
            LinkedList<String> wls=new LinkedList<>();
            for(int i=1; i< jLs.length; i++) {
                wls.add(jLs[i]);
            }
            wList.put(jLs[0],wls);
        }
        ShiftDTO shift = new ShiftDTO(d,Integer.parseInt(sDetails[1]),sDetails[2],wList);
        im.cacheObject(shift);
        return shift;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        ShiftDTO s = (ShiftDTO) obj;
        String[] params = new String[4];
        params[0] = ""+new SimpleDateFormat("dd/MM/yyyy").format(s.date);
        params[1]=Integer.toString(s.type);
        params[2]=s.shiftManager;
        String ws = "";
        for (String st:
                s.workers.keySet()) {
            List<String> wos = s.workers.get(st);
            ws+=st;
            for (String s1:
                    wos) {
                ws+=" "+s1;
            }
            ws+="|";
        }
        ws=ws.substring(0,ws.length()-1);
        params[3]=ws;
        return con.insert("Shifts", params);
    }

    @Override
    public boolean updateObj(DTO obj) {
        //TODO
        return false;
    }

    @Override
    public boolean deleteObj(String[] keys) {
        im.unCacheObject(keys[0]+"#"+keys[1]);
        return con.delete("Shifts",keys,new String[]{"Date","Type"});
    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        loadAllObjsFromDB();
        return im.getObjsList();
    }

    @Override
    public void loadAllObjsFromDB() {
        List<String[]> ls = con.select("Shifts",null,null);
        for (String[] key:
                ls) {
            loadObjectFromDB(new String[]{key[0],key[1]});
        }
    }

    @Override
    public void freeCache() {
        im=new IdentityMap();
    }

    @Override
    public void deleteDB() {

    }
}


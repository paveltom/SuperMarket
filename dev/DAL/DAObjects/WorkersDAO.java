package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.DTO.WorkerDTO;
import DAL.DataBaseConnection;
import DAL.IdentityMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkersDAO implements IDAO{
    public IdentityMap workerIM = new IdentityMap();
    private String TableName = "Workers";
    private String[] params = {"Id","Name","Job","SMQual","BankDetails","Pay","StartDate","SocialConditions"};

    public List<DTO> getAllObjByJob(String _Job)
    {
        DataBaseConnection dbc = new DataBaseConnection();
        String[] key = {"Job"};
        String job = "'"+_Job+"'";
        String[] key1 = {job};
        List<String[]> allDTO = dbc.select(TableName, key, key1);
        List<DTO> dtos = new ArrayList<DTO>();
        for (String[] s:
                allDTO) {
            if(workerIM.getCachedObj(s[0]) == null)
            {
                WorkerDTO wDTO = new WorkerDTO(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7]);
                workerIM.cacheObject(wDTO);
                dtos.add(wDTO);
            }
        }
        return dtos;
    }
    public Map<String,String> getAllAvail()
    {
        Map<String,String> avails = new HashMap<String ,String >();
     loadAllObjsFromDB();
        for (DTO dto:
             workerIM.getObjsList()) {
            avails.put(((WorkerDTO)dto).getKey(),((WorkerDTO)dto).getParamVal("Availability"));
        }
        return avails;
    }
    @Override
    public DTO getObj(String[] keys) {
        WorkerDTO wDTO = (WorkerDTO) workerIM.getCachedObj(keys[0]);
        if(workerIM.getCachedObj(keys[0]) != null)
            return wDTO;
        else {
           return loadObjectFromDB(keys);
        }

    }

    @Override
    public DTO loadObjectFromDB(String[] key) {
        DataBaseConnection dbc = new DataBaseConnection();
        List<String[]> WorkerFromDb = dbc.select(TableName, new String[]{params[0]},key);
        if(WorkerFromDb.isEmpty())
            return null;
        else
        {
            WorkerDTO wDTO = new WorkerDTO(WorkerFromDb.get(0)[0],WorkerFromDb.get(0)[1],WorkerFromDb.get(0)[2],WorkerFromDb.get(0)[3],WorkerFromDb.get(0)[4],WorkerFromDb.get(0)[5],WorkerFromDb.get(0)[6],WorkerFromDb.get(0)[7],WorkerFromDb.get(0)[8]);
            workerIM.cacheObject(wDTO);
            return wDTO;
        }
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        DataBaseConnection dbc = new DataBaseConnection();
        dbc.insert(TableName,((WorkerDTO)obj).getParams());
        workerIM.cacheObject(obj);
        return true;
    }

    @Override
    public boolean updateObj(DTO obj) {
        DataBaseConnection dbc = new DataBaseConnection();
        for (String s:
                params) {
            dbc.update(TableName,new String[]{obj.getKey()}, new String[]{params[0]},s,((WorkerDTO)obj).getParamVal(s));
        }
        return true;
    }

    @Override
    public boolean deleteObj(String[] keys) {
        DataBaseConnection dbc = new DataBaseConnection();
        dbc.delete(TableName,keys, new String[]{params[0]});
        return true;
    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        loadAllObjsFromDB();
        List<DTO> list = workerIM.getObjsList();
        return list;
    }

    @Override
    public void loadAllObjsFromDB() {
        DataBaseConnection dbc = new DataBaseConnection();
        List<String[]> allDTO = dbc.select(TableName, new String[]{"null"}, new String[]{"null"});
        for (String[] s:
                allDTO) {
            if(workerIM.getCachedObj(s[0]) == null)
            {
                WorkerDTO wDTO = new WorkerDTO(s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8]);
                workerIM.cacheObject(wDTO);
            }
        }
    }

    @Override
    public void freeCache() {

    }

    @Override
    public void deleteDB() {

    }
}

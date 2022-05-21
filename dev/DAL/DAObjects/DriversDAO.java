package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.DTO.DriverDTO;
import DAL.DataBaseConnection;
import DAL.IdentityMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;

public class DriversDAO implements IDAO{

    private IdentityMap driversIM;
    private DataBaseConnection dbconn;

    public DriversDAO(){
        dbconn = new DataBaseConnection();
        driversIM = new IdentityMap();
    }


    public boolean updateDriverDiary(String key, String shifts){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        boolean res = dbconn.update("Drivers", keys, keyNames, "Diary", shifts);
        driversIM.cacheObject(loadObjectFromDB(keys));
        return res;
    }

    public boolean addDriverFutureShifts(String key, String toAdd){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String[] out = dbconn.select("Drivers", keyNames,keys).get(0);
        String updatedShifts = out[6] + toAdd;
        boolean res = dbconn.update("Drivers", keys, keyNames, "FutureShifts", updatedShifts);
        driversIM.cacheObject(loadObjectFromDB(keys));
        return res;
    }
    public boolean rewriteDriverFutureShifts(String key, String[] shiftsToAdd){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String newShifts = String.join("#", shiftsToAdd);
        boolean res = dbconn.update("Drivers", keys, keyNames, "FutureShifts", newShifts);
        driversIM.cacheObject(loadObjectFromDB(keys));
        return res;
    }
    public String getDriverFutureShifts(String key){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String[] driver = dbconn.select("Drivers", keyNames, keys).get(0);
        return driver[6];
    }

    @Override
    public DTO getObj(String[] keys) {
        DTO output = driversIM.getCachedObj(keys[0]);
        if(output == null)
            output = loadObjectFromDB(keys);
        return output;
    }

    @Override
    public DTO loadObjectFromDB(String[] keys) {
        String[] keyNames = {"Id"};
        String[] driver = dbconn.select("Drivers", keyNames, keys).get(0);
        DTO output = new DriverDTO(driver[0], driver[3], driver[4], driver[1], driver[2], driver[5], driver[6]);
        return output;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        if(obj instanceof DriverDTO){
            String[] params = {((DriverDTO) obj).Id, ((DriverDTO) obj).Name, ((DriverDTO) obj).Cellphone, ((DriverDTO) obj).License, ((DriverDTO) obj).Zone, ((DriverDTO) obj).Diary, ((DriverDTO) obj).FutureShifts};
            try {
                boolean res = dbconn.insert("Drivers", params);
                if(!res) return res;
                String[] keys = {((DriverDTO) obj).Id};
                driversIM.cacheObject(loadObjectFromDB(keys));
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateObj(DTO obj) {
        boolean res = false;
        if(obj instanceof DriverDTO) {
            try {
                String[] keys = {obj.getKey()};
                String[] keyNames = {"Id"};
                String[] params = {((DriverDTO) obj).Id, ((DriverDTO) obj).Name, ((DriverDTO) obj).Cellphone, ((DriverDTO) obj).License, ((DriverDTO) obj).Zone, ((DriverDTO) obj).Diary, ((DriverDTO) obj).FutureShifts};
                String[] paramNames = {"Id", "Name", "Cellphone", "VehicleLicenseCategory", "ShippingZone", "Diary", "FutureShifts"};
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != "")
                        res = dbconn.update("Drivers", keys, keyNames, paramNames[i], params[i]);
                }
                driversIM.cacheObject(loadObjectFromDB(keys));
                return res;
            }catch (Exception e){
                return false;
            }
        }
        return res;
    }

    @Override
    public boolean deleteObj(String[] keys) {
        try {
            String[] keyNames = {"Id"};
            dbconn.delete("Drivers", keys, keyNames);
            return true;
        }catch (Exception e){return false;}
    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        loadAllObjsFromDB();
        return driversIM.getObjsList();
    }

    @Override
    public void loadAllObjsFromDB() {
        List<String[]> res = dbconn.select("Drivers", null, null);
        for(String[] driver : res){
            DriverDTO toCache = new DriverDTO(driver[0], driver[3], driver[4], driver[1], driver[2], driver[5], driver[6]);
            driversIM.cacheObject(toCache);
        }
    }

    @Override
    public void freeCache() {
        driversIM.clearMap();
    }

    @Override
    public void deleteDB() {
        throw new NotImplementedException();
    }
}

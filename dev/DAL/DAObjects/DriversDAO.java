package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.DTO.DriverDTO;
import DAL.DataBaseConnection;
import DAL.IdentityMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;

public class DriversDAO implements IDAO{

    private IdentityMap driversIM;
    DataBaseConnection dbconn;

    public DriversDAO(){
        dbconn = new DataBaseConnection();
        driversIM = new IdentityMap();
    }


    public void updateDriverDiary(String key, String shifts){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        dbconn.update("Drivers", keys, keyNames, "Diary", shifts);
    }

    public void addDriverFutureShifts(String key, String toAdd){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String[] out = dbconn.select("Drivers", keyNames,keys).get(0);
        String updatedShifts = out[6] + toAdd;
        dbconn.update("Drivers", keys, keyNames, "FutureShifts", updatedShifts);
    }
    public void rewriteDriverFutureShifts(String key, String[] shiftsToAdd){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String newShifts = String.join(",", shiftsToAdd);
        dbconn.update("Drivers", keys, keyNames, "FutureShifts", newShifts);
    }
    public String getDriverFutureShifts(String key){
        String[] keys = {key};
        String[] keyNames = {"Id"};
        String[] driver = dbconn.select("Drivers", keyNames, keys).get(0);
        return driver[6];
    }

    //String id, String license, String zone,String name, String cellphone, String diary

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
        DTO output = new DriverDTO(driver[0], driver[3], driver[4], driver[1], driver[2], driver[6]);
        return output;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        if(obj instanceof DriverDTO){
            String[] params = {((DriverDTO) obj).Id, ((DriverDTO) obj).Name, ((DriverDTO) obj).Cellphone, ((DriverDTO) obj).License, ((DriverDTO) obj).Zone, ((DriverDTO) obj).Diary};
            try {
                dbconn.insert("Drivers", params);
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateObj(DTO obj, int[] valsToUpdate) {
        if(obj instanceof DriverDTO) {
            try {
                String[] keys = {obj.getKey()};
                String[] keyNames = {"Id"};
                String[] params = {((DriverDTO) obj).Id, ((DriverDTO) obj).Name, ((DriverDTO) obj).Cellphone, ((DriverDTO) obj).License, ((DriverDTO) obj).Zone, ((DriverDTO) obj).Diary};
                String[] paramNames = {"Id", "Name", "Cellphone", "VehicleLicenseCategory", "ShippingZone", "Diary"};
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != "")
                        dbconn.update("Drivers", keys, keyNames, paramNames[i], params[i]);
                }
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
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
            DriverDTO toCache = new DriverDTO(driver[0], driver[3], driver[4], driver[1], driver[2], driver[6]);
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

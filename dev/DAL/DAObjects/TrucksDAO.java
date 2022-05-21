package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.DTO.TruckDTO;
import DAL.DataBaseConnection;
import DAL.IdentityMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class TrucksDAO implements IDAO{

    private IdentityMap trucksIM;

    private DataBaseConnection dbconn;

    public TrucksDAO(){

        trucksIM = new IdentityMap();
        dbconn = new DataBaseConnection();
    }


    public boolean updateTruckDiary(String key, String shifts){
        String[] keys = {key};
        String[] keyNames = {"VehicleLicenseNumber"};
        boolean res = dbconn.update("Trucks", keys, keyNames, "Diary", shifts);
        trucksIM.cacheObject(loadObjectFromDB(keys));
        return res;
    }

    @Override
    public DTO getObj(String[] keys) {
        DTO output = trucksIM.getCachedObj(keys[0]);
        if(output == null)
            output = loadObjectFromDB(keys);
        return output;
    }

    @Override
    public DTO loadObjectFromDB(String[] keys) {
        String[] keyNames = {"VehicleLicenseNumber"};
        String[] truck = dbconn.select("Trucks", keyNames, keys).get(0);
        DTO output = new TruckDTO(Double.parseDouble(truck[1]), Double.parseDouble(truck[2]), Long.parseLong(truck[0]), truck[3], truck[4], truck[5], "");
        return output;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        if(obj instanceof TruckDTO){
            String[] params = {String.valueOf(((TruckDTO) obj).VehicleLicenseNumber), String.valueOf(((TruckDTO) obj).MaxLoadWeight), String.valueOf(((TruckDTO) obj).NetWeight), ((TruckDTO) obj).Model, ((TruckDTO) obj).Zone, ((TruckDTO) obj).Diary};
            try {
                boolean res = dbconn.insert("Trucks", params);
                if(!res) return res;
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
        if(obj instanceof TruckDTO) {
            try {
                String[] keys = {obj.getKey()};
                String[] keyNames = {"Id"};
                String[] params = {String.valueOf(((TruckDTO) obj).VehicleLicenseNumber), String.valueOf(((TruckDTO) obj).MaxLoadWeight), String.valueOf(((TruckDTO) obj).NetWeight), ((TruckDTO) obj).Model, ((TruckDTO) obj).Zone, ((TruckDTO) obj).Diary};
                String[] paramNames = {"VehicleLicenseNumber", "MaxLoadWeight", "NetWeight", "Model", "ShippingZone", "Diary"};
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != "")
                        res = dbconn.update("Trucks", keys, keyNames, paramNames[i], params[i]);
                }
                trucksIM.cacheObject(loadObjectFromDB(keys));
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
            String[] keyNames = {"VehicleLicenseNumber"};
            dbconn.delete("Trucks", keys, keyNames);
            trucksIM.unCacheObject(keys[0]);
            return true;
        }catch (Exception e){return false;}
    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        loadAllObjsFromDB();
        return trucksIM.getObjsList();
    }

    @Override
    public void loadAllObjsFromDB() {
        List<String[]> res = dbconn.select("Trucks", null, null);
        for(String[] truck : res){
            TruckDTO toCache = new TruckDTO(Double.parseDouble(truck[1]), Double.parseDouble(truck[2]), Long.parseLong(truck[0]), truck[3], truck[4], truck[5], "");
            trucksIM.cacheObject(toCache);
        }
    }

    @Override
    public void freeCache() {
        trucksIM.clearMap();
    }

    @Override
    public void deleteDB() {
        throw new NotImplementedException();
    }
}

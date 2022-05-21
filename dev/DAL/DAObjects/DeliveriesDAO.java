package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.DTO.DeliveryRecipeDTO;
import DAL.DataBaseConnection;
import DAL.IdentityMap;

import java.util.List;

public class DeliveriesDAO implements IDAO{

    private IdentityMap deliveriesIM;
    private DataBaseConnection dbconn;

    public DeliveriesDAO(){
        deliveriesIM = new IdentityMap();
        dbconn = new DataBaseConnection();
    }
    @Override
    public DTO getObj(String[] keys) {
        DTO output = deliveriesIM.getCachedObj(keys[0]);
        if(output == null)
            output = loadObjectFromDB(keys);
        return output;
    }

    @Override
    public DTO loadObjectFromDB(String[] keys) {
        String[] keyNames = {"DeliveryId"};
        String[] delivery = dbconn.select("Deliveries", keyNames, keys).get(0);
        int i = 0;
        DTO output = new DeliveryRecipeDTO(delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                                            delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                                            delivery[i++], Long.parseLong(delivery[i++]), delivery[i++]);
        return output;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        if(obj instanceof DeliveryRecipeDTO){
            String[] params = {((DeliveryRecipeDTO) obj).OrderId, ((DeliveryRecipeDTO) obj).DeliveryId, ((DeliveryRecipeDTO) obj).SupplierZone,
                               ((DeliveryRecipeDTO) obj).SupplierAddress, ((DeliveryRecipeDTO) obj).SupplierName, ((DeliveryRecipeDTO) obj).SupplierCellphone,
                                ((DeliveryRecipeDTO) obj).ClientZone, ((DeliveryRecipeDTO) obj).ClientAddress, ((DeliveryRecipeDTO) obj).ClientName,
                                 ((DeliveryRecipeDTO) obj).ClientCellphone, ((DeliveryRecipeDTO) obj).DeliveredProducts,
                                ((DeliveryRecipeDTO) obj).DueDate, ((DeliveryRecipeDTO) obj).DriverId, ((DeliveryRecipeDTO) obj).DriverName,
                                ((DeliveryRecipeDTO) obj).DriverCellphone, String.valueOf(((DeliveryRecipeDTO) obj).TruckLicenseNumber)};
            try {
                boolean res = dbconn.insert("Deliveries", params);
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
        if(obj instanceof DeliveryRecipeDTO) {
            try {
                String[] keys = {obj.getKey()};
                String[] keyNames = {"DeliveryId"};
                String[] params = {((DeliveryRecipeDTO) obj).OrderId, ((DeliveryRecipeDTO) obj).DeliveryId, ((DeliveryRecipeDTO) obj).SupplierZone,
                        ((DeliveryRecipeDTO) obj).SupplierAddress, ((DeliveryRecipeDTO) obj).SupplierName, ((DeliveryRecipeDTO) obj).SupplierCellphone,
                        ((DeliveryRecipeDTO) obj).ClientZone, ((DeliveryRecipeDTO) obj).ClientAddress, ((DeliveryRecipeDTO) obj).ClientName,
                        ((DeliveryRecipeDTO) obj).ClientName, ((DeliveryRecipeDTO) obj).ClientCellphone, ((DeliveryRecipeDTO) obj).DeliveredProducts,
                        ((DeliveryRecipeDTO) obj).DueDate, ((DeliveryRecipeDTO) obj).DriverId, ((DeliveryRecipeDTO) obj).DriverName,
                        ((DeliveryRecipeDTO) obj).DriverCellphone, String.valueOf(((DeliveryRecipeDTO) obj).TruckLicenseNumber)};
                String[] paramNames = {"OrderId", "DeliveryId", "SupplierZone", "SupplierAddress", "SupplierName", "SupplierCellphone",
                        "ClientZone", "ClientAddress", "ClientName", "ClientCellphone", "DeliverdProducts", "DueDate",
                        "DriverId", "DriverName", "DriverCellphone", "TruckLicenseNumber"};
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != "")
                        res = dbconn.update("Deliveries", keys, keyNames, paramNames[i], params[i]);
                }
                deliveriesIM.cacheObject(loadObjectFromDB(keys));
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
            String[] keyNames = {"DeliveryId"};
            dbconn.delete("Deliveries", keys, keyNames);
            deliveriesIM.unCacheObject(keys[0]);
            return true;
        }catch (Exception e){return false;}    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        loadAllObjsFromDB();
        return deliveriesIM.getObjsList();
    }

    @Override
    public void loadAllObjsFromDB() {
        List<String[]> res = dbconn.select("Deliveries", null, null);
        for(String[] delivery : res){
            int i = 0;
            DeliveryRecipeDTO toCache = new DeliveryRecipeDTO(delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                    delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                    delivery[i++], Long.parseLong(delivery[i++]), delivery[i++]);
            deliveriesIM.cacheObject(toCache);
        }
    }

    @Override
    public void freeCache() {
        deliveriesIM.clearMap();
    }

    @Override
    public void deleteDB() {

    }
}

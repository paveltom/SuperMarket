package DAL.Delivery_Personnel.DAObjects;

import DAL.Delivery_Personnel.DTO.DTO;
import DAL.Delivery_Personnel.DTO.RecipeDTO;
import DAL.Delivery_Personnel.DataBaseConnection;
import DAL.Delivery_Personnel.IdentityMap;

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
        return new RecipeDTO(delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                delivery[i++], delivery[i++], delivery[i]);
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        if(obj instanceof RecipeDTO){
            String[] params = {((RecipeDTO) obj).OrderId, ((RecipeDTO) obj).SupplierZone,
                               ((RecipeDTO) obj).SupplierAddress, ((RecipeDTO) obj).SupplierName, ((RecipeDTO) obj).SupplierCellphone,
                                ((RecipeDTO) obj).ClientZone, ((RecipeDTO) obj).ClientAddress, ((RecipeDTO) obj).ClientName,
                                 ((RecipeDTO) obj).ClientCellphone, ((RecipeDTO) obj).DeliveredProducts,
                                ((RecipeDTO) obj).DueDate, ((RecipeDTO) obj).DriverId,
                    ((RecipeDTO) obj).TruckLicenseNumber, ((RecipeDTO) obj).Status};
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
        if(obj instanceof RecipeDTO) {
            try {
                String[] keys = {obj.getKey()};
                String[] keyNames = {"DeliveryId"};
                String[] params = {((RecipeDTO) obj).OrderId, ((RecipeDTO) obj).SupplierZone,
                        ((RecipeDTO) obj).SupplierAddress, ((RecipeDTO) obj).SupplierName, ((RecipeDTO) obj).SupplierCellphone,
                        ((RecipeDTO) obj).ClientZone, ((RecipeDTO) obj).ClientAddress, ((RecipeDTO) obj).ClientName,
                        ((RecipeDTO) obj).ClientName, ((RecipeDTO) obj).ClientCellphone, ((RecipeDTO) obj).DeliveredProducts,
                        ((RecipeDTO) obj).DueDate, ((RecipeDTO) obj).DriverId,
                        ((RecipeDTO) obj).TruckLicenseNumber, ((RecipeDTO) obj).Status};
                String[] paramNames = {"OrderId", "SupplierZone", "SupplierAddress", "SupplierName", "SupplierCellphone",
                        "ClientZone", "ClientAddress", "ClientName", "ClientCellphone", "DeliverdProducts", "DueDate",
                        "DriverId", "TruckLicenseNumber", "Status"};
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
            String[] keyNames = {"OrderId"};
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
            RecipeDTO toCache = new RecipeDTO(delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                    delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++], delivery[i++],
                    delivery[i++], delivery[i++], delivery[i]);
            deliveriesIM.cacheObject(toCache);
        }
    }

    @Override
    public void freeCache() {
        deliveriesIM.clearMap();
    }

    @Override
    public void deleteDB() {
        dbconn.deleteDB();
    }
}

package DAL.Delivery_Personnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DAL.Delivery_Personnel.DTO.DTO;

public class IdentityMap {
    private Map<String, DTO> cachedObjects;

    public IdentityMap(){
        this.cachedObjects = new HashMap<>();
    }

    public void cacheObject(DTO obj){
        cachedObjects.put(obj.getKey(), obj);
    }

    public DTO getCachedObj(String key){
        return cachedObjects.get(key);
    }

    public void clearMap(){
        this.cachedObjects = new HashMap<>();
    }

    public void unCacheObject(String key){this.cachedObjects.remove(key);}

    public List<DTO> getObjsList(){return new ArrayList<DTO>(cachedObjects.values());}
}

package DAL;

import java.util.HashMap;
import java.util.Map;
import DAL.DTObjects.IDTO;

public class IdentityMap {
    private Map<String, IDTO> cachedObjects;

    public IdentityMap(){
        this.cachedObjects = new HashMap<>();
    }

    public void cacheObject(IDTO obj){
        cachedObjects.put(obj.getKey(), obj);
    }

    public IDTO getCachedObj(String key){
        return cachedObjects.get(key);
    }

    public void clearMap(){
        this.cachedObjects = new HashMap<>();
    }
}

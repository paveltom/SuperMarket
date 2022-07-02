package DAL.Stock_Suppliers.IdentityMaps;


import SuppliersModule.DomainLayer.Supplier;
import java.util.ArrayList;

public class SuppliersIdentityMap extends IdentityMap<Supplier> {

    private static SuppliersIdentityMap instance = null;

    public static SuppliersIdentityMap getInstance(){
        if (instance == null)
            instance = new SuppliersIdentityMap();
        return instance;
    }
    private SuppliersIdentityMap(){
        cacheList = new ArrayList<>();
    }

    public Supplier isCached(String id) {
        for(Supplier s: cacheList){
            if (s.getsId().equals(id))
                return s;
        }
        return null;
    }

}

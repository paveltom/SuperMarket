package DAL.IdentityMaps;

import StockModule.BusinessLogicLayer.Product;
import SuppliersModule.DomainLayer.Supplier;

import java.util.ArrayList;
import java.util.List;

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

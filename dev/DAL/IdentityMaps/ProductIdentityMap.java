package DAL.IdentityMaps;

import StockModule.BusinessLogicLayer.Product;

import java.util.ArrayList;

public class ProductIdentityMap extends IdentityMap<Product> {
    private static ProductIdentityMap instance = null;

    public static ProductIdentityMap getInstance(){
        if (instance == null)
            instance = new ProductIdentityMap();
        return instance;
    }
    private ProductIdentityMap(){
        cacheList = new ArrayList<>();
    }

    public Product isCached(String idd){
        for(Product p: cacheList){
            if (p.getID().equals(idd))
                return p;
        }
        return null;
    }
}

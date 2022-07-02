package DAL.Stock_Suppliers.IdentityMaps;

import StockModule.BusinessLogicLayer.Category;
import java.util.ArrayList;

public class CategoryIdentityMap extends IdentityMap<Category>{

    static CategoryIdentityMap instance = null;

    public static CategoryIdentityMap getInstance(){
        if (instance == null)
            instance = new CategoryIdentityMap();
        return instance;
    }
    private CategoryIdentityMap(){
        cacheList = new ArrayList<>();
    }

    public Category isCached(String id) {
        int _id = Integer.valueOf(id);

        for(Category c: cacheList){
            if (c.getID() == _id)
                return c;
        }
        return null;
    }
}
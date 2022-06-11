package DAL.IdentityMaps;

import StockModule.BusinessLogicLayer.Product;

import java.util.List;

public abstract class IdentityMap<T> {
    protected List<T> cacheList;

    public void cache(T obj){
        cacheList.add(obj);
    }

    public void remove(T obj){
        cacheList.remove(obj);
    }

    public abstract T isCached(String id);

    public List<T> getAll(){
        return cacheList;
    }
}

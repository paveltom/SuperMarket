package DAL;

import StockModule.BusinessLogicLayer.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductIdentityMap {
    private List<Product> cachedProducts;
    private DataBaseConnection conn;
    private static ProductIdentityMap instance = null;

    public static ProductIdentityMap getInstance(){
        if (instance == null)
            instance = new ProductIdentityMap();
        return instance;
    }
    private ProductIdentityMap(){
        cachedProducts = new ArrayList<>();
        conn = new DataBaseConnection();
    }

    public void cach(Product p){
        cachedProducts.add(p);
    }

    public void remove(Product p){
        cachedProducts.remove(p);
    }

    public Product isCached(String pId){
        for(Product p: cachedProducts){
            if (p.getID().equals(pId))
                return p;
        }
        return null;
    }


}

package DAL.DAOS.SupplierObjects;
import DAL.DAOS.DAO;
import SuppliersModule.DomainLayer.CatalogProduct;

public class CatalogProductDao extends DAO {

    public void insert(CatalogProduct cp){
        String[] params = {cp.getsId(), cp.getId(), String.valueOf(cp.getPrice()), String.valueOf(cp.isInPeriodicOrder())
                , cp.getCatalogNum()};
        insert("Product_Contract", params);
    }

    public void delete(CatalogProduct cp){
        String[] keys = {"supplier_id", "product_id"};
        String[] keysVals = {cp.getsId(), cp.getId()};
        delete("Product_Contract", keys, keysVals);
    }

    public void setCatalogNum(CatalogProduct cp){
        setAttribute(cp, "catalogNum", cp.getCatalogNum());
    }

    public void setPrice(CatalogProduct cp){
        setAttribute(cp, "price", String.valueOf(cp.getPrice()));
    }

    public void setInPeriodicOrder(CatalogProduct cp){
        setAttribute(cp, "is_periodic_order", String.valueOf(cp.isInPeriodicOrder()));
    }

    private void setAttribute(CatalogProduct cp, String attribute, String value){
        String[] keys = {"supplier_id", "product_id"};
        String[] keysVals = {cp.getsId(), cp.getId()};
        update("Product_Contract", keys, keysVals, attribute, String.valueOf(value));
    }
}

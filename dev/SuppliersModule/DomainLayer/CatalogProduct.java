package SuppliersModule.DomainLayer;

import DAL.DAOS.SupplierObjects.CatalogProductDao;

public class CatalogProduct {

    private final String id;
    private String catalogNum;
    private float price;
    private boolean inPeriodicOrder = false;
    private CatalogProductDao dao;
    private String sId;
    // getters and setters
    public String getsId(){return sId;}
    public String getId(){return id;}
    public String getCatalogNum() {
        return catalogNum;
    }
    public float getPrice() {
        return price;
    }
    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
        dao.setCatalogNum(this);
    }
    public void setPrice(float price) {
        if (price < 0 )
            throw new IllegalArgumentException("product price cannot be negative");
        this.price = price;
        dao.setPrice(this);
    }

    public boolean isInPeriodicOrder() {
        return inPeriodicOrder;
    }

    public void setInPeriodicOrder(boolean inPeriodicOrder) {
        this.inPeriodicOrder = inPeriodicOrder;
        dao.setInPeriodicOrder(this);
    }

    // constructor
    public CatalogProduct(String sId, String id, String catalogNum, float price){
        dao = new CatalogProductDao();
        this.sId = sId;
        this.id = id;
        setCatalogNum(catalogNum);
        setPrice(price);

        dao.insert(this);
    }
    // from db
    public CatalogProduct(String sId, String id, String catalogNum, float price, boolean isPeriodic){
        dao = new CatalogProductDao();
        this.id = id;
        this.sId = sId;
        setCatalogNum(catalogNum);
        setPrice(price);
        inPeriodicOrder = isPeriodic;
    }

    // methods


    @Override
    public String toString() {
        return  "id='" + id + '\'' +
                ", catalogNum='" + catalogNum + '\'' +
                ", price=" + price +
                ", inPeriodicOrder=" + inPeriodicOrder;
    }
}

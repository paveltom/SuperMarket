package SuppliersModule.DomainLayer;

public class CatalogProduct {

    private final String id;
    private String catalogNum;
    private float price;
    private boolean inPeriodicOrder = false;
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
    public void setCatalogNum(String catalogNum) { this.catalogNum = catalogNum; }
    public void setPrice(float price) {
        if (price < 0 )
            throw new IllegalArgumentException("product price cannot be negative");
        this.price = price;
    }

    public boolean isInPeriodicOrder() {
        return inPeriodicOrder;
    }

    public void setInPeriodicOrder(boolean inPeriodicOrder) {
        this.inPeriodicOrder = inPeriodicOrder;
    }

    // constructor
    public CatalogProduct(String sId, String id, String catalogNum, float price){
        this.id = id;
        setCatalogNum(catalogNum);
        setPrice(price);
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

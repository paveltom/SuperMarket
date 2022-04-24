package DomainLayer;

public class SupProduct {
    private String id;
    private String name;
    private float price;

    public SupProduct(String id, String name, float price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() { return price; }

    public String getId() {
        return id;
    }

    public void setPrice(float price) {
        if(price <= 0)
            throw new IllegalArgumentException("price must be > 0");
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

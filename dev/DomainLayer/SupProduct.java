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

    public float getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}

package DeliveryModule.Facade.FacadeObjects;

public class FacadeProduct {
    private int id;
    private double weight;
    private int amount;

    public FacadeProduct(){}

    public FacadeProduct(int id, int amount, double weight){
        this.id = id;
        this.weight = weight;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public int getAmount() {
        return amount;
    }
}

package DeliveryModule.Facade.FacadeObjects;

import DeliveryModule.BusinessLayer.Element.Product;

public class FacadeProduct {
    private String id;
    private double weight;
    private int amount;

    public FacadeProduct(){}

    public FacadeProduct(Product productBL){
        this.id = productBL.Id;
        this.amount = productBL.Amount;
        this.weight = productBL.WeightPerUnit;
    }

    public FacadeProduct(String id, int amount, double weight){
        this.id = id;
        this.weight = weight;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public int getAmount() {
        return amount;
    }
}

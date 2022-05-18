package DeliveryModule.Facade.FacadeObjects;

import DeliveryModule.BusinessLayer.Element.Product;

public class FacadeProduct {
    private int id;
    private double weight;
    private int amount;

    public FacadeProduct(){}

    public FacadeProduct(Product productBL){
        this.id = productBL.Id;
        this.amount = productBL.Amount;
        this.weight = productBL.WeightPerUnit;
    }

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

package DeliveryModule.BusinessLayer.Element;

import DeliveryModule.BusinessLayer.Type.ShippingZone;

/*
* Internal (to Delivery-Business Layer) representation of a product
*/
public class Product
{
    public final int Id, Amount;
    public final double WeightPerUnit;
    private final String Delimiter = "\\$";

    public Product(int id, double weightPerUnit, int amount)
    {
        Id = id;
        WeightPerUnit = weightPerUnit;
        Amount = amount;
    }

    public Product(String encoded)
    {
        final int ID_INDEX = 0, AMOUNT_INDEX = 1, WEIGHT_PER_UNIT_INDEX = 2;
        String tokens[] = encoded.split(Delimiter);
        Id = Integer.parseInt(tokens[ID_INDEX]);
        Amount = Integer.parseInt(tokens[AMOUNT_INDEX]);
        WeightPerUnit = Double.parseDouble(tokens[WEIGHT_PER_UNIT_INDEX]);
    }

    public String Encode()
    {
        return String.format("%d$%d$%f", Id, Amount, WeightPerUnit);
    }

    @Override
    public String toString()
    {
        return String.format("Product: %d\nWeight Per Unit: %f\nAmount: %d\n", Id, WeightPerUnit, Amount);
    }

    @Override
    public int hashCode()
    {
        final int hash = 31, multiplier = 19;
        return (int)(multiplier * (hash + Id + WeightPerUnit + Amount));
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Product))
            return false;
        else
        {
            Product other = (Product) obj;
            return other.Id == Id;
        }
    }
}

package BusinessLayer.DataObject;

/*
* Internal (to Delivery-Business Layer) representation of a product
*/
public class Product
{
    public final int Id, Amount;
    public final double WeightPerUnit;

    public Product(int id, double weightPerUnit, int amount)
    {
        Id = id;
        WeightPerUnit = weightPerUnit;
        Amount = amount;
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

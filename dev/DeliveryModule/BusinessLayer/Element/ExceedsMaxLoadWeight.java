package DeliveryModule.BusinessLayer.Element;

public class ExceedsMaxLoadWeight implements Recipe
{
    public final int OrderId;

    public ExceedsMaxLoadWeight(int orderId)
    {
        OrderId = orderId;
    }

    @Override
    public String toString()
    {
        return String.format("Order: %d can not be delivered since it exceeds max load weight\n", OrderId);
    }
}

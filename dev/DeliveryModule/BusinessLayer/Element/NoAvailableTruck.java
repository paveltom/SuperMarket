package DeliveryModule.BusinessLayer.Element;

public class NoAvailableTruck implements Recipe
{
    public final int OrderId;

    public NoAvailableTruck(int orderId)
    {
        OrderId = orderId;
    }

    @Override
    public String toString()
    {
        return String.format("Order: %d can not be delivered since there is no available truck\n", OrderId);
    }
}

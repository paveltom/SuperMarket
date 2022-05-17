package DeliveryModule.BusinessLayer.Element;

public class NoAvailableDriver implements Recipe
{
    public final int OrderId;

    public NoAvailableDriver(int orderId)
    {
        OrderId = orderId;
    }

    @Override
    public String toString()
    {
        return String.format("Order: %d can not be delivered since there is no available driver\n", OrderId);
    }
}

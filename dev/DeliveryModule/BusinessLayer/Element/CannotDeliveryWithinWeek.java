package DeliveryModule.BusinessLayer.Element;

public class CannotDeliveryWithinWeek implements Recipe
{
    public final int OrderId;
    public final DeliveryDate DueDate;

    public CannotDeliveryWithinWeek(int orderId, DeliveryDate deliveryDate)
    {
        OrderId = orderId;
        DueDate = deliveryDate;
    }

    @Override
    public String toString()
    {
        return String.format("Order: %d can not be delivered within a week.\n The closest due date is: %s", OrderId, DueDate);
    }
}

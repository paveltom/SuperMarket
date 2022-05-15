package BusinessLayer.Element;

public class ErrorRecipe implements Recipe
{
    public final int ErrorCause, OrderId;

    public ErrorRecipe(int errorCause, int orderId)
    {
        ErrorCause = errorCause;
        OrderId = orderId;
    }

    @Override
    public String toString()
    {
        return String.format("Cannot deliver %d since there is no available %s\n", OrderId, ErrorCause == 0? "Driver" : "Truck");
    }
}

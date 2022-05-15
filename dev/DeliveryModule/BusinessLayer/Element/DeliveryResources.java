package BusinessLayer.Element;

public class DeliveryResources
{
    public final Driver DeliveryDriver;
    public final Truck DeliveryTruck;
    public final DeliveryDate DueDate;

    public DeliveryResources(Driver driver, Truck truck, DeliveryDate deliveryDate)
    {
        DeliveryDriver = driver;
        DeliveryTruck = truck;
        DueDate = deliveryDate;
    }

}

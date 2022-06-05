package DeliveryModule.BusinessLayer.Element;

public class CancelDeliveryRecipients
{
    public final String DriverId;
    public final long TruckId;
    public final DeliveryDate DueDate;

    public CancelDeliveryRecipients(String driverId, long truckId, DeliveryDate dueDate)
    {
        DriverId = driverId;
        TruckId = truckId;
        DueDate = dueDate;
    }
}

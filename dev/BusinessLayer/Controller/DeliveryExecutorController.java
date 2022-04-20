package BusinessLayer.Controller;

import BusinessLayer.DataObject.*;

import BusinessLayer.Types.ShippingZone;
import BusinessLayer.Types.VehicleLicenseCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryExecutorController
{
    private AtomicInteger DeliveryIds;
    private final int INCREMENT = 2, DAYS = 365, SHIFT = 4, YEAR = 2022;
    private Map<ShippingZone, byte[][]> Scheduler;
    private Map<Integer, DeliveryOrder> Deliveries;
    private int day, shift;


    public DeliveryExecutorController()
    {
        DeliveryIds = new AtomicInteger(-1);
        Scheduler = new HashMap<>();
        for(ShippingZone zone : ShippingZone.values())
            Scheduler.put(zone, new byte[DAYS][SHIFT]);
        Deliveries = new HashMap<>();
        day = 0;
        shift = 0;
    }

    public DeliveryRecipe Deliver(DeliveryOrder deliveryOrder)
    {
        int deliverId = DeliveryIds.addAndGet(INCREMENT);
        int orderId = deliveryOrder.OrderId;
        var dueDate = GetDeliveryDate();
        day++;
        shift++;
        double weight = 12000000.00;
        var unDelivered = new HashMap<Integer, Integer>(); // delivered[productId] = amount shall be supplied in another delivery
        boolean isPartitioned = false;
        for(var product : deliveryOrder.RequestedProducts)
        {
            double totalProductWeight = product.Amount * product.WeightPerUnit;
            if(!isPartitioned && weight > totalProductWeight)
                weight -= totalProductWeight;
            else
            {
                isPartitioned = true;
                int suppliedAmount = (int)(weight / product.WeightPerUnit);
                weight -= (suppliedAmount * product.WeightPerUnit);
                unDelivered.put(product.Id, suppliedAmount);
            }
        }
        Driver deliveryDriver = new Driver(316534072,VehicleLicenseCategory.C, "DummyDriverFName", "DummyDriverLName", "0548826400");
        Truck deliveryTruck = new Truck(12,13,15791321,"Suzuki harata");
        var deliveryRecipe = new DeliveryRecipe(orderId, deliverId, isPartitioned, dueDate, deliveryDriver, deliveryTruck, unDelivered);
        Deliveries.put(deliverId, deliveryOrder);
        return deliveryRecipe;
    }

    public String GetDeliveriesHistory()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Deliveries History ----------\n");
        for(var delivery: Deliveries.values())
            sb.append(delivery);
        return sb.toString();
    }

    private DeliveryDate GetDeliveryDate()
    {
        int x = day + 1;
        return new DeliveryDate(x, x, YEAR, shift);
    }

}

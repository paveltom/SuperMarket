package DeliveryModule.BusinessLayer.Controller;

import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Element.Truck;
import DeliveryModule.BusinessLayer.Type.Tuple;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryExecutorController
{
    private AtomicInteger DeliveryIds;
    private final int INCREMENT = 1;
    private Map<Integer, DeliveryOrder> Deliveries;

    public DeliveryExecutorController()
    {
        DeliveryIds = new AtomicInteger(-1);
        Deliveries = new HashMap<>();
    }

    public DeliveryRecipe Deliver(DeliveryOrder deliveryOrder)
    {
        int deliverId = DeliveryIds.addAndGet(INCREMENT);
        double CargoWeight = 0;
        final double MaxCargoWeight = VehicleLicenseCategory.GetMaxLoadWeight();
        var unDelivered = new HashMap<Integer, Integer>(); // delivered[productId] = amount shall be supplied in another delivery
        boolean isPartitioned = false;
        for(var product : deliveryOrder.RequestedProducts)
        {
            double totalProductWeight = product.Amount * product.WeightPerUnit;
            if(!isPartitioned && (CargoWeight + totalProductWeight < MaxCargoWeight))
                CargoWeight += totalProductWeight;
            else // cargo's weight exceeds the maximal load weights. Hence, partial this delivery order.
            {
                isPartitioned = true;
                CargoWeight = MaxCargoWeight - CargoWeight;
                int suppliedAmount = (int) (CargoWeight / product.WeightPerUnit);
                CargoWeight += (suppliedAmount * product.WeightPerUnit);
                unDelivered.put(product.Id, suppliedAmount);
            }
        }
        var date = deliveryOrder.SubmissionDate;
        Tuple<Driver, Truck, DeliveryDate> resourceOrder =DeliveryController.GetInstance().GetDeliveryDate(deliveryOrder.SubmissionDate, deliveryOrder.Supplier.Zone, CargoWeight);
        var output = new DeliveryRecipe(deliveryOrder.OrderId, deliverId, isPartitioned, resourceOrder.Third, resourceOrder.First, resourceOrder.Second, unDelivered);
        Deliveries.put(deliverId, deliveryOrder);
        return output;
    }

    public String GetDeliveriesHistory()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Deliveries History ------------------------------\n");
        for(var delivery: Deliveries.values())
            sb.append(delivery);
        return sb.toString();
    }

    public void Clear()
    {
        Deliveries.clear();
    }

}

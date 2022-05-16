package DeliveryModule.BusinessLayer.Controller;

import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryExecutorController
{
    private AtomicInteger DeliveryIds;
    private final int INCREMENT = 1;
    private Map<Integer, DeliveryOrder> Deliveries;

    private final int NO_AVAILABLE_DRIVER = 0;
    private final int NO_AVAILABLE_TRUCK = 1;


    public DeliveryExecutorController()
    {
        DeliveryIds = new AtomicInteger(-1);
        Deliveries = new HashMap<>();
    }

    public Recipe Deliver(DeliveryOrder deliveryOrder)
    {
        int deliverId = DeliveryIds.addAndGet(INCREMENT);
        double CargoWeight = 0;
        final double MaxCargoWeight = VehicleLicenseCategory.GetMaxLoadWeight();

        // delivered[productId] = amount of productId missing in current delivery
        var unDelivered = new HashMap<Integer, Integer>();
        boolean isPartitioned = false;
        // Validate if the order need to be partitioned
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

        // Find available driver & truck
        DeliveryResources deliveryResources = DeliveryController.GetInstance().GetDeliveryResources(deliveryOrder.SubmissionDate, deliveryOrder.Supplier.Zone, CargoWeight);

        // if either driver or truck are unavailable, an error recipe will be returned
        Recipe output = deliveryResources.DeliveryDriver == null ? new ErrorRecipe(deliveryOrder.OrderId, NO_AVAILABLE_DRIVER) :
                 deliveryResources.DeliveryTruck == null ? new ErrorRecipe (deliveryOrder.OrderId, NO_AVAILABLE_TRUCK) :
                 new DeliveryRecipe(deliveryOrder.OrderId, deliverId, isPartitioned, deliveryResources , unDelivered);
        //
        DocumentDelivery(deliverId, deliveryOrder);
        return output;
    }

    private void DocumentDelivery(int deliverId, DeliveryOrder deliveryOrder)
    {
        Deliveries.put(deliverId, deliveryOrder);
        //insert to DB
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

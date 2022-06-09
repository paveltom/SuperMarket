package DeliveryModule.BusinessLayer.Controller;

import DAL.DALController;
import DAL.DTO.DeliveryRecipeDTO;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.Pair;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryExecutorController
{
    private final AtomicInteger DeliveryIds;
    private final Map<Integer, DeliveryRecipe> Deliveries;

    public DeliveryExecutorController()
    {
        DeliveryIds = new AtomicInteger(0);
        Deliveries = new HashMap<>();
        Init();
    }

    private void Init()
    {
        int deliveryId;
        List<DeliveryRecipeDTO> deliveriesData = DALController.getInstance().getAllDeliveries();
        for(DeliveryRecipeDTO src : deliveriesData)
        {
            deliveryId = Integer.parseInt(src.DeliveryId);
            Deliveries.put(deliveryId, new DeliveryRecipe(src));
            if(DeliveryIds.get() < deliveryId)
            {
                DeliveryIds.set(deliveryId);
            }
        }
    }

    public Recipe Deliver(DeliveryOrder deliveryOrder)
    {
        final int INCREMENT = 1;
        int deliverId = DeliveryIds.addAndGet(INCREMENT);
        double CargoWeight = 0;
        final double MaxCargoWeight = VehicleLicenseCategory.GetMaxLoadWeight();

        boolean exceedMaxLoadWeight = false;
        // Calculate total cargo weight. Validate it doesn't exceed max load weight.
        for(Product product : deliveryOrder.RequestedProducts)
        {
            double totalProductWeight = product.Amount * product.WeightPerUnit;
            if(CargoWeight < 0 || CargoWeight + totalProductWeight < MaxCargoWeight)
                CargoWeight += totalProductWeight;
            else // cargo's weight exceeds the maximal load weights.
            {
                exceedMaxLoadWeight = true;
                break;

            }
        }
        Recipe output;
        if(exceedMaxLoadWeight)
        {
            output = new ExceedsMaxLoadWeight(deliveryOrder.OrderId);
        }
        else
        {
            // Find available driver & truck
            DeliveryResources deliveryResources = DeliveryController.GetInstance().GetDeliveryResources
                    (deliveryOrder.SubmissionDate, deliveryOrder.Supplier.Zone, CargoWeight, deliveryOrder.SupplierWorkingDays);

            // if driver or truck are unavailable, or closest delivery date exceeds one week latency, an error recipe will be returned
           output = deliveryResources.DeliveryDriver == null ? new NoAvailableDriver(deliveryOrder.OrderId) :
                    deliveryResources.DeliveryTruck == null ? new NoAvailableTruck(deliveryOrder.OrderId) :
                    (deliveryResources.DueDate.Date.Day - deliveryOrder.SubmissionDate.Day > 7) ? new CannotDeliveryWithinWeek(deliveryOrder.OrderId, deliveryResources.DueDate):
                    new DeliveryRecipe(deliveryOrder.OrderId, deliverId, deliveryOrder.Supplier, deliveryOrder.Client,
                            deliveryOrder.RequestedProducts, deliveryResources.DueDate,
                            deliveryResources.DeliveryDriver.Name, deliveryResources.DeliveryDriver.Cellphone,
                            deliveryResources.DeliveryTruck.VehicleLicenseNumber, deliveryResources.DeliveryDriver.Id);
        }
        if(output instanceof DeliveryRecipe)
            DocumentDelivery(deliverId, (DeliveryRecipe)output);
        return output;
    }

    public CancelDeliveryRecipients CancelDelivery(int deliveryId)
    {
        DeliveryRecipe recipe = Deliveries.getOrDefault(deliveryId, null);
        if(recipe != null)
        {
            DALController.getInstance().removeDelivery(deliveryId);
            return new CancelDeliveryRecipients(recipe.DriverId, recipe.TruckLicenseNumber, recipe.DueDate);
        }
        return null;
    }

    private void DocumentDelivery(int deliverId, DeliveryRecipe recipe)
    {
        Deliveries.put(deliverId, recipe);
        recipe.Persist();
    }


    public String GetDeliveriesHistory()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Deliveries History ------------------------------\n");
        for(DeliveryRecipe recipe: Deliveries.values())
            sb.append(recipe);
        return sb.toString();
    }

    public void Clear()
    {
        Deliveries.clear();
    }

}

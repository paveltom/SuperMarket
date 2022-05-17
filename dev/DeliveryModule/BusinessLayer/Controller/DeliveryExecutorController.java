package DeliveryModule.BusinessLayer.Controller;

import DAL.DALController;
import DAL.DTO.DeliveryRecipeDTO;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryExecutorController
{
    private AtomicInteger DeliveryIds;
    private final int INCREMENT = 1;
    private Map<Integer, DeliveryRecipe> Deliveries;

    public DeliveryExecutorController()
    {
        DeliveryIds = new AtomicInteger(-1);
        Deliveries = new HashMap<>();
        Init();
    }

    private void Init()
    {
        List<DeliveryRecipeDTO> deliveriesData = DALController.getInstance().getAllDeliveries();
        for(DeliveryRecipeDTO src : deliveriesData)
            Deliveries.put(src.DeliveryId, new DeliveryRecipe(src));
    }

    public Recipe Deliver(DeliveryOrder deliveryOrder)
    {
        int deliverId = DeliveryIds.addAndGet(INCREMENT);
        double CargoWeight = 0;
        final double MaxCargoWeight = VehicleLicenseCategory.GetMaxLoadWeight();

        boolean exceedMaxLoadWeight = false;
        // Calculate total cargo weight. Validate it doesnt exceed max load weight.
        for(Product product : deliveryOrder.RequestedProducts)
        {
            double totalProductWeight = product.Amount * product.WeightPerUnit;
            if(CargoWeight + totalProductWeight < MaxCargoWeight)
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
            DeliveryResources deliveryResources = DeliveryController.GetInstance().GetDeliveryResources(deliveryOrder.SubmissionDate, deliveryOrder.Supplier.Zone, CargoWeight);

            // if either driver or truck are unavailable, an error recipe will be returned
           output = deliveryResources.DeliveryDriver == null ? new NoAvailableDriver(deliveryOrder.OrderId) :
                    deliveryResources.DeliveryTruck == null ? new NoAvailableTruck(deliveryOrder.OrderId) :
                    new DeliveryRecipe(deliveryOrder.OrderId, deliverId, deliveryOrder.Supplier, deliveryOrder.Client,
                            deliveryOrder.RequestedProducts, deliveryResources.DueDate,
                            deliveryResources.DeliveryDriver.Name, deliveryResources.DeliveryDriver.Cellphone,
                            deliveryResources.DeliveryTruck.VehicleLicenseNumber, deliveryResources.DeliveryDriver.Id);
        }
        if(output instanceof DeliveryRecipe)
            DocumentDelivery(deliverId, (DeliveryRecipe)output);
        return output;
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

package BusinessLayer.Element;

import java.util.Map;

public class DeliveryRecipe implements Recipe
{
    public final int OrderId, DeliveryId;
    /*
    * If an order request can not be shipped within one delivery, this flag will be set.
    * Delivery caller is responsible to demand shipment of UnDeliveredProduct.
    */
    public final boolean IsPartitioned;
    /* Enumerate remaining amount to supply for each product. */
    public final Map<Integer, Integer> UnDeliveredProducts;
    public final DeliveryDate DueDate;
    public final Driver DeliveryDriver;
    public final Truck DeliveryTruck;

    public DeliveryRecipe(int supplierOrderId, int deliveryId, boolean isPartitioned, DeliveryResources deliveryResources, Map<Integer, Integer> unDeliveredProduct)
    {
        OrderId = supplierOrderId;
        DeliveryId = deliveryId;
        IsPartitioned = isPartitioned;
        DueDate = deliveryResources.DueDate;
        DeliveryDriver = deliveryResources.DeliveryDriver;
        DeliveryTruck = deliveryResources.DeliveryTruck;
        UnDeliveredProducts = unDeliveredProduct;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Recipe ----------\n");
        sb.append(String.format("Delivery: %d\nOrder: %d\nDue Date: %s\n\n%s\n%s\n", DeliveryId, OrderId, DueDate, DeliveryDriver, DeliveryTruck));
        if(IsPartitioned)
        {
            sb.append("\nMissing products: show missing per product id\n");
            for(var entry : UnDeliveredProducts.entrySet())
                sb.append(String.format("\nProduct: %d\nMissing amount: %d\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

}

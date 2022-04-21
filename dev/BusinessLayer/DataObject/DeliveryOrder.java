package BusinessLayer.DataObject;

import BusinessLayer.Types.ShippingZone;

import java.util.List;

public class DeliveryOrder
{
    public final Site Supplier, Client;
    public final int OrderId;
    public final List<Product> RequestedProducts; /* Enumerate amount requested for each product. */
    public final Date SubmissionDate;
    public final ShippingZone Zone;

    public DeliveryOrder(Site supplier, Site client, int orderId, List<Product> requestedProducts, Date submissionDate, ShippingZone zone)
    {
        Supplier = supplier;
        Client = client;
        OrderId = orderId;
        RequestedProducts = requestedProducts;
        SubmissionDate = submissionDate;
        Zone = zone;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Order ----------\n");
        sb.append(String.format("Order: %d\nZone: %s\nSubmissionDate: %s\n\nOrigin\n%s\nDestination\n%s\n", OrderId, Zone.GetShippingZoneName(Zone), SubmissionDate, Supplier, Client));
        sb.append("Requested Products:\n");
        for(Product p: RequestedProducts)
            sb.append(String.format("\nProduct: %d\nRequested amount: %d\n", p.Id, p.Amount));
        return sb.toString();
    }
}

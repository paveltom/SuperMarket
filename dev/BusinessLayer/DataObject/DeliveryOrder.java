package BusinessLayer.DataObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeliveryOrder
{
    public final Site Supplier, Client;
    public final int OrderId;
    public final List<Product> RequestedProducts; /* Enumerate amount requested for each product. */
    public final Date SubmissionDate;

    public DeliveryOrder(Site supplier, Site client, int orderId, List<Product> requestedProducts, Date submissionDate)
    {
        Supplier = supplier;
        Client = client;
        OrderId = orderId;
        RequestedProducts = requestedProducts;
        SubmissionDate = submissionDate;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Order ----------\n");
        sb.append(String.format("Order: %d\nSubmissionDate: %s\n\nOrigin\n%s\nDestination\n%s\n", OrderId, SubmissionDate, Supplier, Client));
        sb.append("Requested Products:\n");
        for(Product p: RequestedProducts)
            sb.append(String.format("\nProduct: %d\nRequested amount: %d\n", p.Id, p.Amount));
        return sb.toString();
    }
}

package DeliveryModule.BusinessLayer.Element;

import DeliveryModule.BusinessLayer.Type.ShippingZone;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DeliveryOrder
{
    public final Site Supplier, Client;
    public final int OrderId;
    public final List<Product> RequestedProducts; /* Enumerate amount requested for each product. */
    public final Date SubmissionDate;
    public final boolean[] SupplierWorkingDays;


    public DeliveryOrder(Site supplier, Site client, int orderId, List<Product> requestedProducts, Date submissionDate)
    {
        Supplier = supplier;
        Client = client;
        OrderId = orderId;
        RequestedProducts = requestedProducts;
        SubmissionDate = submissionDate;
        SupplierWorkingDays = new boolean[7];
        Arrays.fill(SupplierWorkingDays, true);
    }

    public DeliveryOrder(Site supplier, Site client, int orderId, List<Product> requestedProducts,
                         Date submissionDate, boolean[] supplierWorkingDays)
    {
        Supplier = supplier;
        Client = client;
        OrderId = orderId;
        RequestedProducts = requestedProducts;
        SubmissionDate = submissionDate;
        SupplierWorkingDays = supplierWorkingDays;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Order ----------\n");
        sb.append(String.format("Order: %d\nSupplier Zone: %s\nClient Zone: %s\nSubmissionDate: %s\n\nOrigin\n%s\nDestination\n%s\n", OrderId, Supplier.Zone, Client.Zone, SubmissionDate, Supplier, Client));
        sb.append("Requested Products:\n");
        for(Product p: RequestedProducts)
            sb.append(String.format("%s\n", p));
        return sb.toString();
    }
}

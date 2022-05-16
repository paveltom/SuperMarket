package DeliveryModule.BusinessLayer.Element;

import DeliveryModule.BusinessLayer.Type.ShippingZone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeliveryOrder
{
    public final Site Supplier, Client;
    public final int OrderId;
    public final List<Product> RequestedProducts; /* Enumerate amount requested for each product. */
    public final Date SubmissionDate;
    private final String Delimiter = "~", ProductDelimiter = "#";


    public DeliveryOrder(Site supplier, Site client, int orderId, List<Product> requestedProducts, Date submissionDate, ShippingZone zone)
    {
        Supplier = supplier;
        Client = client;
        OrderId = orderId;
        RequestedProducts = requestedProducts;
        SubmissionDate = submissionDate;
    }

    public DeliveryOrder(String encoded)
    {
        final int SUPPLIER_INDEX = 0, CLIENT_INDEX = 1, ORDER_ID_INDEX = 2, REQUESTED_PRODUCTS_INDEX = 3, SUBMISSION_DATE_INDEX = 4;
        String tokens[] = encoded.split(Delimiter);
        Supplier = new Site(tokens[SUPPLIER_INDEX]);
        Client = new Site(tokens[CLIENT_INDEX]);
        OrderId = Integer.parseInt(tokens[ORDER_ID_INDEX]);
        SubmissionDate = new Date(tokens[SUBMISSION_DATE_INDEX]);
        String product_encodes[] = tokens[REQUESTED_PRODUCTS_INDEX].split(ProductDelimiter);
        List<Product> products = new ArrayList<>();
        for(String encode:product_encodes)
            products.add(new Product(encode));
        RequestedProducts = products;
    }

    public String Encode()
    {
        StringBuilder sb = new StringBuilder();
        int ndelimiters = RequestedProducts.size() - 1;
        for(Product p : RequestedProducts) {
            sb.append(p.Encode());
            if(ndelimiters > 0)
                sb.append(ProductDelimiter);
            ndelimiters--;
        }
        return String.format("%s$%s$%d$%s$%s", Supplier.Encode(), Client.Encode(), OrderId, sb.toString(), SubmissionDate.Encode());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Order ----------\n");
        sb.append(String.format("Order: %d\nSupplier Zone: %s\nClient Zone: %s\nSubmissionDate: %s\n\nOrigin\n%s\nDestination\n%s\n", OrderId, Supplier.Zone, Client.Zone, SubmissionDate, Supplier, Client));
        sb.append("Requested Products:\n");
        for(Product p: RequestedProducts)
            sb.append(String.format("\nProduct: %d\nRequested amount: %d\n", p.Id, p.Amount));
        return sb.toString();
    }
}

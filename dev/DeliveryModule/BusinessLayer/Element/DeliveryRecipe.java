package DeliveryModule.BusinessLayer.Element;

import DAL.DALController;
import DAL.DTO.DeliveryRecipeDTO;
import DeliveryModule.BusinessLayer.Type.ShippingZone;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRecipe implements Recipe
{
    public final int OrderId, DeliveryId;
    public final Site Supplier, Client;
    public final List<Product> DeliveredProducts;
    public final DeliveryDate DueDate;
    public final String DriverName;
    public final String DriverCellphone;
    public final String DriverId;
    public final long TruckLicenseNumber;
    private final String Delimiter = "#";


    public DeliveryRecipe(int orderId, int deliveryId, Site origin,
                          Site destination, List<Product> deliveredProducts, DeliveryDate dueDate,
                          String driverName, String driverCellphone, long truckLicenseNumber, String driverId)
    {
        OrderId = orderId;
        DeliveryId = deliveryId;
        Supplier = origin;
        Client = destination;
        DeliveredProducts = deliveredProducts;
        DueDate = dueDate;
        DriverName = driverName;
        DriverCellphone = driverCellphone;
        DriverId = driverId;
        TruckLicenseNumber = truckLicenseNumber;
    }

    public DeliveryRecipe(DeliveryRecipeDTO src)
    {
        OrderId = src.OrderId;
        DeliveryId = src.DeliveryId;
        Supplier = new Site(ShippingZone.CreateShippingZoneByName(src.SupplierZone), src.SupplierAddress, src.SupplierName, src.SupplierCellphone);
        Client = new Site(ShippingZone.CreateShippingZoneByName(src.ClientZone), src.ClientAddress, src.ClientName, src.ClientCellphone);
        DeliveredProducts = DecodeProducts(src.DeliveredProducts);
        DueDate = new DeliveryDate(src.DueDate);
        DriverName = src.DriverName;
        DriverCellphone = src.DriverCellphone;
        DriverId = src.DriverId;
        TruckLicenseNumber = src.TruckLicenseNumber;
    }

    public void Persist()
    {
        // origin details
        String supplierZone = ShippingZone.GetShippingZoneName(Supplier.Zone), supplierName = Supplier.Name,
                supplierAddress = Supplier.Address, supplierCellphone = Supplier.Cellphone;
        // destination details
        String clientZone = ShippingZone.GetShippingZoneName(Client.Zone), clientName = Client.Name,
                clientAddress = Client.Address, clientCellphone = Client.Cellphone;
        // encode products delivered as a string
        String deliveredProducts = EncodeProducts();
        String dueDate = DueDate.Encode();
        DeliveryRecipeDTO addMe = new DeliveryRecipeDTO(OrderId, DeliveryId, supplierZone, supplierAddress, supplierName, supplierCellphone,
                clientZone, clientAddress, clientName, clientCellphone, deliveredProducts, dueDate, DriverName, DriverCellphone, TruckLicenseNumber, DriverId);
        DALController.getInstance().addDelivery(addMe);
    }

    private List<Product> DecodeProducts(String encoded)
    {
        String product_encodes[] = encoded.split(Delimiter);
        List<Product> products = new ArrayList<>();
        for(String encode : product_encodes)
            products.add(new Product(encode));
        return products;
    }

    private String EncodeProducts()
    {
        StringBuilder sb = new StringBuilder();
        int ndelimiters = DeliveredProducts.size() - 1;
        for(Product p : DeliveredProducts)
        {
            sb.append(p.Encode());
            if(ndelimiters > 0)
                sb.append(Delimiter);
            ndelimiters--;
        }
        return sb.toString();
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Recipe ----------\n");
        sb.append(String.format("Delivery: %d\nOrder: %d\nDue Date: %s\n\nSupplier\n%s\nClient\n%s\nDriver: %s, Cellphone: %s, Id: %s\n TruckLicenseNumber: %d\nDelivered Products:\n%s\n",
                DeliveryId, OrderId, DueDate, Supplier, Client, DriverName, DriverCellphone,DriverId, TruckLicenseNumber,ConcatenateProducts()));
        return sb.toString();
    }

    private String ConcatenateProducts()
    {
        StringBuilder sb = new StringBuilder();
        for(Product p : DeliveredProducts)
            sb.append(p);
        return sb.toString();
    }

}

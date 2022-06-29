package DeliveryModule.BusinessLayer.Element;

import DAL.DALController;
import DAL.DTO.RecipeDTO;
import DeliveryModule.BusinessLayer.Type.RetCode;
import DeliveryModule.BusinessLayer.Type.ShippingZone;

import java.util.ArrayList;
import java.util.List;

public class Receipt
{

    public final RetCode Status;
    public final String OrderId;
    public Site Supplier, Client;
    public List<Product> DeliveredProducts;
    public Date DueDate;
    public Driver Driver;
    public Truck Truck;

    private final String Delimiter = "#";


    public Receipt(RetCode status,
                   String orderId,
                   Site supplier,
                   Site client,
                   List<Product> deliveredProducts,
                   Date dueDate,
                   Driver driver,
                   Truck truck)
    {
        Status = status;
        OrderId = orderId;
        Supplier = supplier;
        Client = client;
        DeliveredProducts = deliveredProducts;
        DueDate = dueDate;
        Driver = driver;
        Truck = truck;
    }

    public Receipt(RetCode status, String orderId)
    {
        Status = status;
        OrderId = orderId;
    }

    public Receipt(RecipeDTO src)
    {
        OrderId = src.OrderId;
        Status = RetCode.Ordinal2RetCode(Integer.parseInt(src.Status));
        if(Status == RetCode.SuccessfulDelivery)
        {
            Supplier = new Site(ShippingZone.CreateShippingZoneByName(src.SupplierZone), src.SupplierAddress, src.SupplierName, src.SupplierCellphone);
            Client = new Site(ShippingZone.CreateShippingZoneByName(src.ClientZone), src.ClientAddress, src.ClientName, src.ClientCellphone);
            DeliveredProducts = DecodeProducts(src.DeliveredProducts);
            DueDate = new Date(src.DueDate);
            Driver = new Driver(DALController.getInstance().getDriver(src.DriverId));
            Truck = new Truck(DALController.getInstance().getTruck(src.TruckLicenseNumber));
        }
    }

    private List<Product> DecodeProducts(String encoded)
    {
        String[] product_encodes = encoded.split(Delimiter);
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

    public void Persist()
    {
        RecipeDTO addMe;
        if(Status == RetCode.SuccessfulDelivery)
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
            addMe = new RecipeDTO(OrderId, supplierZone, supplierAddress, supplierName, supplierCellphone,
                    clientZone, clientAddress, clientName, clientCellphone, deliveredProducts, dueDate, Driver.Id, Truck.VehicleLicenseNumber, Status.ordinal());
        }
        else
            addMe = new RecipeDTO(Status.ordinal(), OrderId);

        DALController.getInstance().addDelivery(addMe);
    }

    @Override
    public String toString()
    {
        if(Status == RetCode.SuccessfulDelivery)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("---------- Delivery Recipe ----------\n");
            sb.append(String.format("Order: %s\nDue Date: %s\n\nSupplier\n%s\nClient\n%s\n%s" +
                            "\n%s\nDelivered Products:\n%s\n",
                             OrderId, DueDate, Supplier, Client, Driver, Truck, ConcatenateProducts()));
            return sb.toString();
        }
        return String.format("Order id: %s\n%s\n", OrderId, RetCode.GetRetCodeName(Status));
    }

    private String ConcatenateProducts()
    {
        StringBuilder sb = new StringBuilder();
        for(Product p : DeliveredProducts)
            sb.append(p);
        return sb.toString();
    }


}

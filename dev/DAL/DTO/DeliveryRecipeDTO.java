package DAL.DTO;

public class DeliveryRecipeDTO implements DTO
{
    public String OrderId, DeliveryId;
    public String SupplierZone, SupplierAddress, SupplierName, SupplierCellphone;
    public String ClientZone, ClientAddress, ClientName, ClientCellphone;
    public String DeliveredProducts;
    public String DueDate;
    public String DriverName;
    public String DriverId;
    public String DriverCellphone;
    public long TruckLicenseNumber;

    public DeliveryRecipeDTO(String orderId, String deliveryId, String supplierZone, String supplierAddress, String supplierName,
                             String supplierCellphone, String clientZone,String clientAddress, String clientName,
                             String clientCellphone, String deliveredProducts, String dueDate,
                             String driverName, String driverCellphone, long truckLicenseNumber, String driverId)
    {
        OrderId = orderId;
        DeliveryId = deliveryId;
        SupplierZone = supplierZone;
        SupplierAddress = supplierAddress;
        SupplierName = supplierName;
        SupplierCellphone = supplierCellphone;
        ClientZone = clientZone;
        ClientAddress = clientAddress;
        ClientName = clientName;
        ClientCellphone = clientCellphone;
        DeliveredProducts = deliveredProducts;
        DueDate = dueDate;
        DriverName = driverName;
        DriverId = driverId;
        DriverCellphone = driverCellphone;
        TruckLicenseNumber = truckLicenseNumber;
    }

    @Override
    public String getKey() {
        return DeliveryId;
    }

    @Override
    public String toString() {
        return null;
    }
}

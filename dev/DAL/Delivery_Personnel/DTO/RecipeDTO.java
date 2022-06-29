package DAL.Delivery_Personnel.DTO;

public class RecipeDTO implements DTO
{
    public String OrderId;
    public String SupplierZone, SupplierAddress, SupplierName, SupplierCellphone;
    public String ClientZone, ClientAddress, ClientName, ClientCellphone;
    public String DeliveredProducts;
    public String DueDate;
    public String DriverId;
    public String TruckLicenseNumber;
    public String Status;

    public RecipeDTO(String orderId, String supplierZone, String supplierAddress, String supplierName,
                     String supplierCellphone, String clientZone, String clientAddress, String clientName,
                     String clientCellphone, String deliveredProducts, String dueDate, String driverId,
                     long truckLicenseNumber, int status)
    {
        OrderId = orderId;
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
        DriverId = driverId;
        TruckLicenseNumber = String.valueOf(truckLicenseNumber);
        Status = String.valueOf(status);
    }

    public RecipeDTO(String orderId, String supplierZone, String supplierAddress, String supplierName,
                     String supplierCellphone, String clientZone, String clientAddress, String clientName,
                     String clientCellphone, String deliveredProducts, String dueDate, String driverId,
                     String truckLicenseNumber, String status)
    {
        OrderId = orderId;
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
        DriverId = driverId;
        TruckLicenseNumber = truckLicenseNumber;
        Status = status;
    }

    public RecipeDTO(int status, String orderId)
    {
        Status = String.valueOf(status);
        OrderId = orderId;
        SupplierZone = null;
        SupplierAddress = null;
        SupplierName = null;
        SupplierCellphone = null;
        ClientZone = null;
        ClientAddress = null;
        ClientName = null;
        ClientCellphone = null;
        DeliveredProducts = null;
        DueDate = null;
        DriverId = null;
        TruckLicenseNumber = null;
    }

    @Override
    public String getKey() {
        return OrderId;
    }

    @Override
    public String toString() {
        return "DeliveryRecipeDTO";
    }
}

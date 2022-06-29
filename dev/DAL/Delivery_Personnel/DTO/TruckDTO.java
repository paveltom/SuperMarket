package DAL.Delivery_Personnel.DTO;


public class        TruckDTO implements DTO
{

    public double MaxLoadWeight,NetWeight;
    public String VehicleLicenseNumber;
    public String AuthorizedLicense;
    public String Model;
    public String Zone;
    public String Diary;

    public TruckDTO(double maxLoadWeight, double netWeight, String vehicleLicenseNumber, String model, String zone, String diary, String authorizedLicense)
    {
        MaxLoadWeight = maxLoadWeight;
        NetWeight = netWeight;
        VehicleLicenseNumber = vehicleLicenseNumber;
        Model = model;
        Zone = zone;
        Diary = diary;
        AuthorizedLicense = authorizedLicense;
    }

    @Override
    public String getKey() {
        return VehicleLicenseNumber;
    }

    @Override
    public String toString() {
        return "TruckDTO";
    }
}

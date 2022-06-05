package DAL.DTO;


public class TruckDTO implements DTO
{

    public double MaxLoadWeight,NetWeight;
    public long VehicleLicenseNumber;
    public String AuthorizedLicense;
    public String Model;
    public String Zone;
    public String Diary;

    public TruckDTO(double maxLoadWeight, double netWeight, long vehicleLicenseNumber, String model, String zone, String diary, String authorizedLicense)
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
        return String.valueOf(VehicleLicenseNumber);
    }

    @Override
    public String toString() {
        return "TruckDTO";
    }
}

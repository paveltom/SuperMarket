package BusinessLayer.Element;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Type.VehicleLicenseCategory;

public class Driver
{
    public final String Id;
    public final VehicleLicenseCategory License;
    public final ShippingZone Zone;
    private Scheduler Dairy;

    public Driver(String id, VehicleLicenseCategory license, ShippingZone zone)
    {
        Id = id;
        License = license;
        Dairy = new Scheduler();
        Zone = zone;
    }

    public Driver(String id, VehicleLicenseCategory license, ShippingZone zone, Constraint constraint)
    {
        Id = id;
        License = license;
        Dairy = new Scheduler();
        Zone = zone;
        SetConstraint(constraint);
    }

    @Override
    public String toString()
    {
        return String.format("Driver: %s\nLicense: %s\nZone: %s\n", Id, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License), Zone);
    }

    public DeliveryDate GetAvailableDeliveryDate(int month, int day)
    {
        return Dairy.GetAvailableDeliveryDate(month, day);
    }

    public void SetOccupied(DeliveryDate occupiedDate)
    {
        Dairy.SetOccupied(occupiedDate);
    }

    public void SetConstraint(Constraint constraint)
    {
        Dairy.SetConstraint(constraint);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Driver))
            return false;
        else
        {
            Driver other = (Driver) obj;
            return Id.equals(other.Id);
        }
    }


}

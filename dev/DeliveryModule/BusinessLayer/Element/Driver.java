package DeliveryModule.BusinessLayer.Element;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

public class Driver
{
    public final long Id;
    public final VehicleLicenseCategory License;
    public final String FirstName, LastName, Cellphone;
    public final ShippingZone Zone;
    private Scheduler Dairy;


    public Driver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone)
    {
        Id = id;
        License = license;
        FirstName = fname;
        LastName = lname;
        Cellphone = cellphone;
        Dairy = new Scheduler();
        Zone = zone;
    }

    @Override
    public String toString()
    {
        return String.format("Driver: %s %s\nID: %d\nLicense: %s\nZone: %s\nCellphone: %s\n",
                FirstName, LastName, Id, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License), Zone, Cellphone);
    }

    public DeliveryDate GetAvailableShift(int month, int day)
    {
        return Dairy.GetAvailableShift(month, day);
    }

    public void SetOccupied(DeliveryDate occupiedDate)
    {
        Dairy.SetOccupied(occupiedDate);
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
            return Id == other.Id;
        }
    }


}

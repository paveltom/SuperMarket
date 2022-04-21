package BusinessLayer.DataObject;
import BusinessLayer.Types.VehicleLicenseCategory;

public class Driver
{
    public final long Id;
    public final VehicleLicenseCategory License;
    public final String FirstName, LastName, Cellphone;
    private Scheduler Dairy;

    public Driver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone)
    {
        Id = id;
        License = license;
        FirstName = fname;
        LastName = lname;
        Cellphone = cellphone;
        Dairy = new Scheduler();
    }

    @Override
    public String toString()
    {
        return String.format("Driver: %s %s\nID: %d\nLicense: %s\nCellphone: %s\n",
                FirstName, LastName, Id, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License), Cellphone);
    }

    public DeliveryDate GetAvailableShift(int month, int day)
    {
        return Dairy.GetAvailableShift(month, day);
    }

}

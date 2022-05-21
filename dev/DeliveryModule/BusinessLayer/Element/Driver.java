package DeliveryModule.BusinessLayer.Element;
import DAL.DALController;
import DAL.DTO.DriverDTO;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

public class Driver
{
    public final String Id, Name, Cellphone;
    public final VehicleLicenseCategory License;
    public final ShippingZone Zone;
    private Scheduler Diary;

    public Driver(String id,String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone)
    {
        Id = id;
        License = license;
        Zone = zone;
        Name = name;
        Cellphone = cellphone;
        Diary = new Scheduler();
        Persist();
    }

    public Driver(String id, String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone, Constraint constraint)
    {
        Id = id;
        License = license;
        Zone = zone;
        Name = name;
        Cellphone = cellphone;
        Diary = new Scheduler();
        SetConstraint(constraint);
        Persist();
    }

    public Driver(DriverDTO src)
    {
        Id = src.Id;
        License = VehicleLicenseCategory.CreateShippingZoneByName(src.License);
        Zone = ShippingZone.CreateShippingZoneByName(src.Zone);
        Name = src.Name;
        Cellphone = src.Cellphone;
        Diary = new Scheduler(src.Diary);
    }

    private void Persist()
    {
        DriverDTO persist = new DriverDTO(Id, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License),
                ShippingZone.GetShippingZoneName(Zone),Name, Cellphone, Diary.Encode(),"");
        DALController.getInstance().addDriver(persist);
    }

    private void PersistDiary()
    {
        DALController.getInstance().updateDriverDiary(Id, Diary.Encode());
    }

    @Override
    public String toString()
    {
        return String.format("Driver: %s\nID: %s\n Cellphone: %s\nLicense: %s\nZone: %s\n",
                Name, Id,Cellphone, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License), Zone);
    }

    public DeliveryDate GetAvailableDeliveryDate(int month, int day)
    {
        return Diary.GetAvailableDeliveryDate(month, day);
    }

    public void SetOccupied(DeliveryDate occupiedDate)
    {
        Diary.SetOccupied(occupiedDate);
        PersistDiary();
    }

    public void SetConstraint(Constraint constraint)
    {
        Diary.SetConstraint(constraint);
        PersistDiary();
    }

    /* Return true iff exists occupied shift from param month at param day */
    public boolean IsOccupied(int month, int day)
    {
        return Diary.IsOccupied(month, day);
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

package DeliveryModule.BusinessLayer.Element;

import DAL.Delivery_Personnel.DALController;
import DAL.Delivery_Personnel.DTO.DriverDTO;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.time.LocalDate;

public class Driver implements Comparable<Driver>
{
    public final String Id, Name, Cellphone;
    public final VehicleLicenseCategory License;
    public final ShippingZone Zone;
    private final Scheduler Diary;
    public Date NextAvailableShift;

    public Driver(String id,String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone)
    {
        Id = id;
        License = license;
        Zone = zone;
        Name = name;
        Cellphone = cellphone;
        Diary = new Scheduler();
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
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
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
    }

    public Driver(DriverDTO src)
    {
        Id = src.Id;
        License = VehicleLicenseCategory.CreateShippingZoneByName(src.License);
        Zone = ShippingZone.CreateShippingZoneByName(src.Zone);
        Name = src.Name;
        Cellphone = src.Cellphone;
        Diary = new Scheduler(src.Diary);
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
    }

    public void Persist()
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
        return String.format("Driver: %s\nID: %s\nCellphone: %s\nLicense: %s\nZone: %s\n",
                Name, Id,Cellphone, VehicleLicenseCategory.GetVehicleLicenseCategoryName(License), Zone);
    }


    public void SetOccupied(Date occupiedDate)
    {
        Diary.SetOccupied(occupiedDate);
        if(NextAvailableShift.compareTo(occupiedDate) >= 0)
            NextAvailableShift = Diary.NextShift(occupiedDate.Month, occupiedDate.Day);
        PersistDiary();
    }

    public void SetAvailable(Date occupiedDate)
    {
        Diary.SetAvailable(occupiedDate);
        if(occupiedDate.compareTo(NextAvailableShift) <= 0)
            NextAvailableShift = Diary.NextShift(occupiedDate.Month, occupiedDate.Day);
        PersistDiary();
    }

    public void SetConstraint(Constraint constraint)
    {
        Diary.SetConstraint(constraint);
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
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

    @Override
    public int compareTo(Driver o)
    {
        return o == null ? 1 : NextAvailableShift.compareTo(o.NextAvailableShift);
    }

    public Date Next()
    {
        Date res = NextAvailableShift;
        SetOccupied(res);
        NextAvailableShift = Diary.NextShift(res.Month, res.Day);
        return res;
    }

    public Date Next(Date submissionDate,boolean[] supplierWorkingDays)
    {
        return Diary.NextShift(submissionDate.Month, submissionDate.Day, supplierWorkingDays);
    }
}

package DeliveryModule.BusinessLayer.Element;

import DAL.Delivery_Personnel.DALController;
import DAL.Delivery_Personnel.DTO.TruckDTO;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.time.LocalDate;

public class Truck implements Comparable<Truck>
{
    public final double MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final VehicleLicenseCategory AuthorizedLicense;
    public final String Model;
    public final ShippingZone Zone;
    private Scheduler Diary;
    public Date NextAvailableShift;

    public Truck(long vehicleLicenseNumber)
    {
        VehicleLicenseNumber = vehicleLicenseNumber;
        MaxLoadWeight = -1;
        NetWeight = -1;
        AuthorizedLicense = null;
        Model = null;
        Zone = null;
    }

    public Truck(double mlw, double nw, long vln, String m, ShippingZone z, VehicleLicenseCategory authorizedLicense)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
        Zone = z;
        AuthorizedLicense = authorizedLicense;
        Diary = new Scheduler();
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
        Persist();
    }

    public Truck(TruckDTO src)
    {
        MaxLoadWeight = src.MaxLoadWeight;
        NetWeight = src.NetWeight;
        VehicleLicenseNumber = src.VehicleLicenseNumber;
        Model = src.Model;
        Zone = ShippingZone.CreateShippingZoneByName(src.Zone);
        AuthorizedLicense = VehicleLicenseCategory.CreateShippingZoneByName(src.AuthorizedLicense);
        Diary = new Scheduler(src.Diary);
        LocalDate currDay = LocalDate.now();
        NextAvailableShift = Diary.NextShift(currDay.getMonthValue(), currDay.getDayOfMonth());
    }

    public void Persist()
    {
        TruckDTO persist = new TruckDTO(MaxLoadWeight, NetWeight, VehicleLicenseNumber, Model, ShippingZone.GetShippingZoneName(Zone),
                Diary.Encode(), VehicleLicenseCategory.GetVehicleLicenseCategoryName(AuthorizedLicense));
        DALController.getInstance().addTruck(persist);
    }

    private void PersistDiary()
    {
        DALController.getInstance().updateTruckDiary(VehicleLicenseNumber, Diary.Encode());
    }

    @Override
    public String toString()
    {
        return String.format("Truck\nModel: %s\nVehicleLicenseNumber: %d\nZone: %s\nMaxLoadWeight: %f\nNetWeight: %f\nAuthorizedLicense: %s\n",
                Model, VehicleLicenseNumber, Zone, MaxLoadWeight, NetWeight, AuthorizedLicense);
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

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Truck))
            return false;
        else
        {
            Truck other = (Truck) obj;
            return VehicleLicenseNumber == other.VehicleLicenseNumber;
        }
    }

    @Override
    public int compareTo(Truck o) {
        return o == null ? 1 : NextAvailableShift.compareTo(o.NextAvailableShift);
    }

    public Date Next(Date submissionDate,boolean[] supplierWorkingDays)
    {
        return Diary.NextShift(submissionDate.Month, submissionDate.Day, supplierWorkingDays);
    }
}

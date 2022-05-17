package DeliveryModule.BusinessLayer.Element;

import DAL.DALController;
import DAL.DTO.TruckDTO;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

public class Truck
{
    public final double MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final VehicleLicenseCategory AuthorizedLicense;
    public final String Model;
    public final ShippingZone Zone;
    private Scheduler Diary;

    public Truck(double mlw, double nw, long vln, String m, ShippingZone z, VehicleLicenseCategory authorizedLicense)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
        Zone = z;
        AuthorizedLicense = authorizedLicense;
        Diary = new Scheduler();
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
    }

    private void Persist()
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
        return String.format("Truck\nModel: %s\nVehicleLicenseNumber: %d\nZone: %s\nMaxLoadWeight: %f\nNetWeight: %f\n", Model, VehicleLicenseNumber, Zone, MaxLoadWeight, NetWeight);
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

}

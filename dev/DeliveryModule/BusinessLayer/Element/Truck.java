package DeliveryModule.BusinessLayer.Element;

import DeliveryModule.BusinessLayer.Type.ShippingZone;

public class Truck
{
    public final double MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final String Model;
    public final ShippingZone Zone;
    private Scheduler Dairy;

    public Truck(double mlw, double nw, long vln, String m, ShippingZone z)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
        Zone = z;
        Dairy = new Scheduler();
    }

    @Override
    public String toString()
    {
        return String.format("Truck\nModel: %s\nVehicleLicenseNumber: %d\nZone: %s\nMaxLoadWeight: %f\nNetWeight: %f\n", Model, VehicleLicenseNumber, Zone, MaxLoadWeight, NetWeight);
    }

    public DeliveryDate GetAvailableDeliveryDate(int month, int day)
    {
        return Dairy.GetAvailableDeliveryDate(month, day);
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
        if(!(obj instanceof Truck))
            return false;
        else
        {
            Truck other = (Truck) obj;
            return VehicleLicenseNumber == other.VehicleLicenseNumber;
        }
    }

}

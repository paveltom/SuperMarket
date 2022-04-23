package BusinessLayer.Type;

import BusinessLayer.Element.DeliveryDate;
import BusinessLayer.Element.Scheduler;

public class Truck
{
    public final double MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final String Model;
    private Scheduler Dairy;

    public Truck(double mlw, double nw, long vln, String m)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
        Dairy = new Scheduler();
    }

    @Override
    public String toString()
    {
        return String.format("Truck\nModel: %s\nVehicleLicenseNumber: %d\nMaxLoadWeight: %f\nNetWeight: %f\n", Model, VehicleLicenseNumber, MaxLoadWeight, NetWeight);
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
        if(!(obj instanceof Truck))
            return false;
        else
        {
            Truck other = (Truck) obj;
            return VehicleLicenseNumber == other.VehicleLicenseNumber;
        }
    }

}

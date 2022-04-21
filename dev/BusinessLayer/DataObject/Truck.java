package BusinessLayer.DataObject;

public class Truck
{
    public final double MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final String Model;

    public Truck(double mlw, double nw, long vln, String m)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
    }

    @Override
    public String toString()
    {
        return String.format("Truck\nModel: %s\nVehicleLicenseNumber: %d\nMaxLoadWeight: %f\nNetWeight: %f\n", Model, VehicleLicenseNumber, MaxLoadWeight, NetWeight);
    }

}

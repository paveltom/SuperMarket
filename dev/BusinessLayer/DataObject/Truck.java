package BusinessLayer.DataObject;

public class Truck
{
    public final int MaxLoadWeight,NetWeight;
    public final long VehicleLicenseNumber;
    public final String Model;

    public Truck(int mlw, int nw, long vln, String m)
    {
        MaxLoadWeight = mlw;
        NetWeight = nw;
        VehicleLicenseNumber = vln;
        Model = m;
    }

    @Override
    public String toString()
    {
        return String.format("Truck\n Model: %s\n VehicleLicenseNumber: %d\n MaxLoadWeight: %d\n NetWeight: %d\n", Model, VehicleLicenseNumber, MaxLoadWeight, NetWeight);
    }

}

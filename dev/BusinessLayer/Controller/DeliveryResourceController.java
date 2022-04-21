package BusinessLayer.Controller;

import BusinessLayer.DataObject.*;
import BusinessLayer.Types.ShippingZone;
import BusinessLayer.Types.VehicleLicenseCategory;

public class DeliveryResourceController {

    //Drivers[zone][license] = Delivery driver of param zone, owner of param license.
    private Driver[][] Drivers;

    //Trucks[zone][license] = Truck belongs param zone, with maximal load restriction of param license.
    private Truck[][] Trucks;

    private final int ZONES = 9;
    private final int LICENSES = 3;

    public DeliveryResourceController()
    {
        Drivers = new Driver[ZONES][LICENSES];
        Trucks = new Truck[ZONES][LICENSES];
        Init();
    }

    /*
    *   Assumption: each shipping zone(9) has 1 driver of each vehicle license category.
     *  Initialize drivers and trucks, currently with default values.
     *  Further implementation will load from DB.
     */
    private void Init()
    {
        InitDrivers();
        InitTrucks();
    }

    private void InitDrivers()
    {
        final long DUMMY_ID = 999999999;
        final String DUMMY_FNAME = "DUMMY_FNAME";
        final String DUMMY_LNAME = "DUMMY_LNAME";
        final String DUMMY_CELLPHONE = "DUMMY_CELLPHONE";

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
                Drivers[zone.ordinal()][licenseCategory.ordinal()] = new Driver(DUMMY_ID, licenseCategory, DUMMY_FNAME, DUMMY_LNAME, DUMMY_CELLPHONE);
        }
    }

    private void InitTrucks()
    {
        final double DUMMY_MLW = 10000000.00;
        final long DUMMY_VEHICLE_NUMBER = 5555555;
        final String DUMMY_TRUCK_MODEL = "DUMMY_TRUCK_MODEL";

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
                Trucks[zone.ordinal()][licenseCategory.ordinal()] = new Truck(VehicleLicenseCategory.MaxLoadWeightByLicense(licenseCategory), DUMMY_MLW, DUMMY_VEHICLE_NUMBER, DUMMY_TRUCK_MODEL);
        }
    }

    // return closest delivery day.
    public Tuple<Driver, Truck, DeliveryDate> GetDeliveryDate(Date date, ShippingZone zone, double weight)
    {
        /* Find the Driver & Truck capable to deliver cargo of param weight */
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        var driver = Drivers[zone.ordinal()][matchingLicense.ordinal()];
        var truck = Trucks[zone.ordinal()][matchingLicense.ordinal()];
        var deliveryDate = driver.GetAvailableShift(date.Month, date.Day + 1);
        return new Tuple<>(driver, truck, deliveryDate);
    }

    public void AddDriver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone)
    {
        var newDriver = new Driver(id, license, fname, lname, cellphone);
        Drivers[zone.ordinal()][license.ordinal()] = newDriver;
    }

    public void AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone)
    {
        var newTruck = new Truck(mlw, nw, vln, m);
        Trucks[zone.ordinal()][VehicleLicenseCategory.FindLicense(mlw).ordinal()] = newTruck;
    }

    public String GetDrivers()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Drivers ------------------------------\n");
        for (ShippingZone zone : ShippingZone.values())
        {
            sb.append(String.format("--------------------Zone: %s--------------------\n", ShippingZone.GetShippingZoneName(zone)));
            for (Driver driver : Drivers[zone.ordinal()])
                sb.append(driver.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String GetTrucks()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Trucks ------------------------------\n");
        for (ShippingZone zone : ShippingZone.values())
        {
            sb.append(String.format("-------------------- Zone: %s --------------------\n", ShippingZone.GetShippingZoneName(zone)));
            for (Truck truck : Trucks[zone.ordinal()])
                sb.append(truck.toString());

        }
        return sb.toString();
    }
}

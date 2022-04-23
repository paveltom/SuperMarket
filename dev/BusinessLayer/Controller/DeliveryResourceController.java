package BusinessLayer.Controller;

import BusinessLayer.Element.*;
import BusinessLayer.Type.Pair;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Type.Truck;
import BusinessLayer.Type.VehicleLicenseCategory;

import java.util.ArrayList;
import java.util.List;

public class DeliveryResourceController {

    //Drivers[zone][license] = Delivery drivers of param zone, owner of param license.
    private List<Driver>[][] Drivers;

    //Trucks[zone][license] = Trucks belongs to param zone, with maximal load restriction of param license.
    private List<Truck>[][] Trucks;

    private final int ZONES = 9;
    private final int LICENSES = 3;

    public DeliveryResourceController()
    {
        Drivers = new ArrayList[ZONES][LICENSES];
        Trucks = new ArrayList[ZONES][LICENSES];
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
            {
                var driversList = new ArrayList<Driver>();
                driversList.add(new Driver(DUMMY_ID, licenseCategory, DUMMY_FNAME, DUMMY_LNAME, DUMMY_CELLPHONE));
                Drivers[zone.ordinal()][licenseCategory.ordinal()] = driversList;
            }
        }
    }

    private void InitTrucks()
    {
        final double DUMMY_NW = 10000000.00;
        final long DUMMY_VEHICLE_NUMBER = 5555555;
        final String DUMMY_TRUCK_MODEL = "DUMMY_TRUCK_MODEL";

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                var trucksList = new ArrayList<Truck>();
                trucksList.add(new Truck(VehicleLicenseCategory.MaxLoadWeightByLicense(licenseCategory), DUMMY_NW, DUMMY_VEHICLE_NUMBER, DUMMY_TRUCK_MODEL));
                Trucks[zone.ordinal()][licenseCategory.ordinal()] = trucksList;
            }
        }
    }

    // return closest delivery day.
    public Tuple<Driver, Truck, DeliveryDate> GetDeliveryDate(Date date, ShippingZone zone, double weight)
    {
        /* Find the Driver & Truck capable to deliver cargo of param weight one closest day */
        var pDriver = FindDriver(date, zone, weight);
        var pTruck = FindTruck(date, zone,weight);
        int deliveryDateDiff = pDriver.Second.compareTo(pTruck.Second);
        /* Choose the latest delivery date */
        DeliveryDate deliveryDate = (deliveryDateDiff > 0) ? pDriver.Second : pTruck.Second;
        /* Update deliveryDate for both driver and truck */
        pDriver.First.SetOccupied(deliveryDate);
        pTruck.First.SetOccupied(deliveryDate);
        return new Tuple<>(pDriver.First, pTruck.First, deliveryDate);
    }

    // return a Driver works at param zone, certified to transport a cargo of param weight at the closest day to param date.
    private Pair<Driver, DeliveryDate> FindDriver(Date date, ShippingZone zone, double weight)
    {
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        var drivers = Drivers[zone.ordinal()][matchingLicense.ordinal()];
        Driver resDriver = null;
        DeliveryDate resDate = null;
        for(Driver driver : drivers)
        {
            if(resDriver != null)
            {
                var d = driver.GetAvailableShift(date.Month, date.Day);
                int diff = resDate.compareTo(d);
                if(diff > 0)
                {
                    /* Found driver available on earlier date */
                    resDriver = driver;
                    resDate = d;
                }
            }
            else
            {
                /* initialize res at first iteration */
                resDriver = driver;
                resDate = driver.GetAvailableShift(date.Month, date.Day);
            }

        }
        return new Pair<>(resDriver, resDate);
    }

    // return a Driver works at param zone, certified to transport a cargo of param weight at the closest day to param date.
    private Pair<Truck, DeliveryDate> FindTruck(Date date, ShippingZone zone, double weight)
    {
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        var trucks = Trucks[zone.ordinal()][matchingLicense.ordinal()];
        Truck resTruck = null;
        DeliveryDate resDate = null;
        for(Truck truck : trucks)
        {
            if(resTruck != null)
            {
                var d = truck.GetAvailableShift(date.Month, date.Day);
                int diff = resDate.compareTo(d);
                if(diff > 0)
                {
                    /* Found truck available on earlier date */
                    resTruck = truck;
                    resDate = d;
                }
            }
            else
            {
                /* initialize res at first iteration */
                resTruck = truck;
                resDate = truck.GetAvailableShift(date.Month, date.Day);
            }

        }
        return new Pair<>(resTruck, resDate);
    }


    public void AddDriver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone)
    {
        var newDriver = new Driver(id, license, fname, lname, cellphone);
        Drivers[zone.ordinal()][license.ordinal()].add(newDriver);
    }

    public void AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone)
    {
        var newTruck = new Truck(mlw, nw, vln, m);
        Trucks[zone.ordinal()][VehicleLicenseCategory.FindLicense(mlw).ordinal()].add(newTruck);
    }

    public void RemoveDriver(long id)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (List<Driver> drivers : Drivers[zone.ordinal()]) {
                for(Driver driver : drivers)
                {
                    if(driver.Id == id)
                    {
                        drivers.remove(driver);
                        return;
                    }
                }
            }
        }
    }

    public void RemoveTruck(long vln)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (List<Truck> trucks : Trucks[zone.ordinal()]) {
                for(Truck truck : trucks)
                {
                    if(truck.VehicleLicenseNumber == vln)
                    {
                        trucks.remove(truck);
                        return;
                    }
                }
            }
        }
    }

    public Driver GetDriver(long id)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (List<Driver> drivers : Drivers[zone.ordinal()]) {
                for(Driver driver : drivers)
                {
                    if(driver.Id == id)
                        return driver;
                }
            }
        }
        return null;
    }

    public Truck GetTruck(long vln)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (List<Truck> trucks : Trucks[zone.ordinal()]) {
                for(Truck truck : trucks)
                {
                    if(truck.VehicleLicenseNumber == vln)
                    {
                        return truck;
                    }
                }
            }
        }
        return null;
    }

    public String GetDrivers()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Drivers ------------------------------\n");
        for (ShippingZone zone : ShippingZone.values())
        {
            sb.append(String.format("--------------------Zone: %s--------------------\n", ShippingZone.GetShippingZoneName(zone)));
            for (List<Driver> drivers : Drivers[zone.ordinal()])
            {
                for(Driver driver : drivers)
                    sb.append(driver.toString());
            }
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
            for (List<Truck> trucks : Trucks[zone.ordinal()])
            {
                for(Truck truck : trucks)
                    sb.append(truck.toString());
            }

        }
        return sb.toString();
    }

    public String ShowShippingZone()
    {
        var sb = new StringBuilder();
        sb.append("------------------------------ Shipping Zones ------------------------------\n");
        sb.append("------------------------------ South ------------------------------\n");
        for(ShippingZone zone : ShippingZone.values())
            sb.append(String.format("%s\n", zone));
        sb.append("------------------------------ North ------------------------------\n");
        return sb.toString();
    }
}

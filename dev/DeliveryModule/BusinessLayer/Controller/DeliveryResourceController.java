package BusinessLayer.Controller;

import BusinessLayer.Element.*;
import BusinessLayer.Type.Pair;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Type.VehicleLicenseCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryResourceController {

    //Drivers[zone][license] = Delivery driver's ID of param zone, owner of param license.
    private final List<String>[][] DriversDistribution;
    // Map driver's id to its representative object in the system.
    private final Map<String, Driver> Drivers;

    //Trucks[zone][license] = Truck's vehicle license numbers (vln) belongs to param zone, with maximal load restriction of param license.
    private final List<Long>[][] TrucksDistribution;
    // Map truck's vehicle license number to its representative object in the system.
    private final Map<Long, Truck> Trucks;

    private final int ZONES = 9;
    private final int LICENSES = 3;


    public DeliveryResourceController()
    {
        DriversDistribution = new ArrayList[ZONES][LICENSES];
        TrucksDistribution = new ArrayList[ZONES][LICENSES];
        Drivers = new HashMap<>();
        Trucks = new HashMap<>();
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
       long DUMMY_ID = 0;

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                String id = String.valueOf(DUMMY_ID);
                var driversList = new ArrayList<String>();
                var driver = new Driver(id, licenseCategory, zone);
                driversList.add(id);
                Drivers.put(id, driver);
                DriversDistribution[zone.ordinal()][licenseCategory.ordinal()] = driversList;
                DUMMY_ID++;
            }
        }
    }

    private void InitTrucks()
    {
        final double DUMMY_NW = 10000000.00;
        long DUMMY_VEHICLE_NUMBER = 5555555;
        final String DUMMY_TRUCK_MODEL = "DUMMY_TRUCK_MODEL";

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                var trucksList = new ArrayList<Long>();
                var truck = new Truck(VehicleLicenseCategory.MaxLoadWeightByLicense(licenseCategory), DUMMY_NW, DUMMY_VEHICLE_NUMBER, DUMMY_TRUCK_MODEL, zone);
                trucksList.add(DUMMY_VEHICLE_NUMBER);
                Trucks.put(DUMMY_VEHICLE_NUMBER, truck);
                TrucksDistribution[zone.ordinal()][licenseCategory.ordinal()] = trucksList;
                DUMMY_VEHICLE_NUMBER++;
            }
        }
    }

    // return closest delivery day.
    public DeliveryResources GetDeliveryResources(Date date, ShippingZone zone, double weight)
    {
        /* Find the Driver & Truck capable to deliver cargo of param weight one closest day */
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);

        var pDriver = FindAvailableDriver(date, zone, weight);
        var pTruck = FindAvailableTruck(date, zone, weight);
        int deliveryDateDiff = pDriver.Second.compareTo(pTruck.Second);
        /* Choose the latest delivery date */
        DeliveryDate deliveryDate = (deliveryDateDiff > 0) ? pDriver.Second : pTruck.Second;
        /* Update deliveryDate for both driver and truck */
        pDriver.First.SetOccupied(deliveryDate);
        pTruck.First.SetOccupied(deliveryDate);
        return new DeliveryResources(pDriver.First, pTruck.First, deliveryDate);
    }

    // return a Driver works at param zone, certified to transport a cargo of param weight at the closest day to param date.
    private Pair<Driver, DeliveryDate> FindAvailableDriver(Date date, ShippingZone zone, double weight)
    {
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        var driverIds = DriversDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Driver resDriver = null;
        DeliveryDate resDate = null;
        for(String driverId : driverIds)
        {
            var driver = Drivers.get(driverId);
            if(resDriver != null)
            {
                var d = driver.GetAvailableDeliveryDate(date.Month, date.Day);
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
                resDate = driver.GetAvailableDeliveryDate(date.Month, date.Day);
            }

        }
        return new Pair<>(resDriver, resDate);
    }

    // return a Driver works at param zone, certified to transport a cargo of param weight at the closest day to param date.
    private Pair<Truck, DeliveryDate> FindAvailableTruck(Date date, ShippingZone zone, double weight)
    {
        var matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        var trucksIds = TrucksDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Truck resTruck = null;
        DeliveryDate resDate = null;
        for(Long truckId : trucksIds)
        {
            var truck = Trucks.get(truckId);
            if(resTruck != null)
            {
                var d = truck.GetAvailableDeliveryDate(date.Month, date.Day);
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
                resDate = truck.GetAvailableDeliveryDate(date.Month, date.Day);
            }

        }
        return new Pair<>(resTruck, resDate);
    }


    public boolean AddDriver(String id, VehicleLicenseCategory license, ShippingZone zone)
    {
        if(!Drivers.containsKey(id))
        {
            var newDriver = new Driver(id, license, zone);
            Drivers.put(id, newDriver);
            DriversDistribution[zone.ordinal()][license.ordinal()].add(id);
            return true;
        }
        return false;
    }

    public boolean AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone)
    {
        if(!Trucks.containsKey(vln))
        {
            var newTruck = new Truck(mlw, nw, vln, m, zone);
            Trucks.put(vln, newTruck);
            TrucksDistribution[zone.ordinal()][VehicleLicenseCategory.FindLicense(mlw).ordinal()].add(vln);
            return true;
        }
        return false;
    }

    public Driver RemoveDriver(String id)
    {
        var removedDriver = Drivers.remove(id);
        if(removedDriver != null)
        {
            var zone = removedDriver.Zone.ordinal();
            var licenseCategory = removedDriver.License;
            DriversDistribution[zone][licenseCategory.ordinal()].remove(id);
            return removedDriver;
        }
        return null;
    }

    public Truck RemoveTruck(long vln)
    {
        var removedTruck = Trucks.remove(vln);
        if(removedTruck != null)
        {
            var zone = removedTruck.Zone.ordinal();
            var licenseCategory = VehicleLicenseCategory.FindLicense(removedTruck.MaxLoadWeight);
            TrucksDistribution[zone][licenseCategory.ordinal()].remove(vln);
            return removedTruck;
        }
        return null;
    }

    public Driver GetDriver(String id)
    {
        return Drivers.getOrDefault(id, null);
    }

    public Truck GetTruck(long vln)
    {
        return Trucks.getOrDefault(vln, null);
    }

    public String GetDrivers()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Trucks ------------------------------\n");
        for(Driver driver : Drivers.values())
            sb.append(String.format("%s\n", driver));
        return sb.toString();
    }

    public String GetTrucks()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Trucks ------------------------------\n");
        for (Truck truck :Trucks.values())
            sb.append(String.format("%s\n", truck));
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

    public void Clear()
    {
        Drivers.clear();
        Trucks.clear();
        for(var zone : ShippingZone.values())
        {
            for(var licenseCategory : VehicleLicenseCategory.values())
            {
                DriversDistribution[zone.ordinal()][licenseCategory.ordinal()].clear();
                TrucksDistribution[zone.ordinal()][licenseCategory.ordinal()].clear();
            }
        }
    }

    public void SetConstraint(String id, Constraint constraint)
    {
        var driver = Drivers.get(id);
        if(driver != null)
            driver.SetConstraint(constraint);
    }
}

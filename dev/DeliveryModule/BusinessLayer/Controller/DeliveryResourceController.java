package DeliveryModule.BusinessLayer.Controller;

import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.Pair;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Element.Truck;
import DeliveryModule.BusinessLayer.Type.Tuple;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryResourceController {

    //Drivers[zone][license] = Delivery driver's ID of param zone, owner of param license.
    private List<Long>[][] DriversDistribution;
    // Map driver's id to its representative object in the system.
    private Map<Long, Driver> Drivers;

    //Trucks[zone][license] = Truck's vehicle license numbers (vln) belongs to param zone, with maximal load restriction of param license.
    private List<Long>[][] TrucksDistribution;
    // Map truck's vehicle license number to its representative object in the system.
    private Map<Long, Truck> Trucks;

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
        final String DUMMY_FNAME = "DUMMY_FNAME";
        final String DUMMY_LNAME = "DUMMY_LNAME";
        final String DUMMY_CELLPHONE = "DUMMY_CELLPHONE";

        for (ShippingZone zone : ShippingZone.values()) {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                var driversList = new ArrayList<Long>();
                var driver = new Driver(DUMMY_ID, licenseCategory, DUMMY_FNAME, DUMMY_LNAME, DUMMY_CELLPHONE, zone);
                driversList.add(DUMMY_ID);
                Drivers.put(DUMMY_ID, driver);
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
    public Tuple<Driver, Truck, DeliveryDate> GetDeliveryDate(Date date, ShippingZone zone, double weight)
    {
        /* Find the Driver & Truck capable to deliver cargo of param weight one closest day */
        var pDriver = FindDriver(date, zone, weight);
        var pTruck = FindTruck(date, zone, weight);
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
        var driverIds = DriversDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Driver resDriver = null;
        DeliveryDate resDate = null;
        for(Long driverId : driverIds)
        {
            var driver = Drivers.get(driverId);
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
        var trucksIds = TrucksDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Truck resTruck = null;
        DeliveryDate resDate = null;
        for(Long truckId : trucksIds)
        {
            var truck = Trucks.get(truckId);
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


    public boolean AddDriver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone)
    {
        if(!Drivers.containsKey(id))
        {
            var newDriver = new Driver(id, license, fname, lname, cellphone, zone);
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

    public Driver RemoveDriver(long id)
    {
        var removedDriver = Drivers.remove(id);
        if(removedDriver != null)
        {
            var zone = removedDriver.Zone.ordinal();
            var licenseCategory = removedDriver.License;
            TrucksDistribution[zone][licenseCategory.ordinal()].remove(id);
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

    public Driver GetDriver(long id)
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
}

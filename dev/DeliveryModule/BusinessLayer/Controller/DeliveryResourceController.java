package DeliveryModule.BusinessLayer.Controller;

import DAL.DALController;
import DAL.DTO.DriverDTO;
import DAL.DTO.TruckDTO;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.Pair;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

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
        List<DriverDTO> driversData = DALController.getInstance().getAllDrivers();
        for(DriverDTO src : driversData)
        {
            Driver driver = new Driver(src);
            List<String> driversList = new ArrayList<String>();
            driversList.add(driver.Id);
            Drivers.put(driver.Id, driver);
            DriversDistribution[driver.Zone.ordinal()][driver.License.ordinal()] = driversList;
        }
//        long Init_Driver_Id = 0;
//        String Init_Driver_Name = "Augustin Louis Cauchy";
//        String Init_Driver_Cellphone = "496351";
//
//        for (ShippingZone zone : ShippingZone.values()) {
//            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
//            {
//                String id = String.valueOf(Init_Driver_Id);
//                List<String> driversList = new ArrayList<>();
//                Driver driver = new Driver(id, Init_Driver_Name,Init_Driver_Cellphone, licenseCategory, zone);
//                driversList.add(id);
//                Drivers.put(id, driver);
//                DriversDistribution[zone.ordinal()][licenseCategory.ordinal()] = driversList;
//                Init_Driver_Id++;
//            }
//        }
    }

    private void InitTrucks()
    {
        List<TruckDTO> trucksData = DALController.getInstance().getAllTrucks();
        for(TruckDTO src : trucksData)
        {
            Truck truck = new Truck(src);
            List<Long> trucksList = new ArrayList<>();
            trucksList.add(truck.VehicleLicenseNumber);
            Trucks.put(truck.VehicleLicenseNumber, truck);
            TrucksDistribution[truck.Zone.ordinal()][truck.AuthorizedLicense.ordinal()] = trucksList;
        }
//        final double DUMMY_NW = 10000000.00;
//        long DUMMY_VEHICLE_NUMBER = 5555555;
//        final String DUMMY_TRUCK_MODEL = "DUMMY_TRUCK_MODEL";
//
//        for (ShippingZone zone : ShippingZone.values()) {
//            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
//            {
//                List<Long> trucksList = new ArrayList<Long>();
//                Truck truck = new Truck(VehicleLicenseCategory.MaxLoadWeightByLicense(licenseCategory), DUMMY_NW, DUMMY_VEHICLE_NUMBER, DUMMY_TRUCK_MODEL, zone, licenseCategory);
//                trucksList.add(DUMMY_VEHICLE_NUMBER);
//                Trucks.put(DUMMY_VEHICLE_NUMBER, truck);
//                TrucksDistribution[zone.ordinal()][licenseCategory.ordinal()] = trucksList;
//                DUMMY_VEHICLE_NUMBER++;
//            }
//        }
    }

    // return closest delivery day.
    public DeliveryResources GetDeliveryResources(Date date, ShippingZone zone, double weight, boolean[] supplierWorkingDays)
    {
        /* Find the Driver & Truck capable to deliver cargo of param weight one closest day */
        Pair<Driver, DeliveryDate> pDriver = FindAvailableDriver(date, zone, weight, supplierWorkingDays);
        if(pDriver.First == null)
            return new DeliveryResources(null, null, null);
        Pair<Truck, DeliveryDate> pTruck = FindAvailableTruck(date, zone, weight, supplierWorkingDays);
        if(pTruck.First == null)
            return new DeliveryResources(null, null, null);
        int deliveryDateDiff = pDriver.Second.compareTo(pTruck.Second);
        /* Choose the latest delivery date */
        DeliveryDate deliveryDate = (deliveryDateDiff > 0) ? pDriver.Second : pTruck.Second;
        /* Update deliveryDate for both driver and truck */
        pDriver.First.SetOccupied(deliveryDate);
        pTruck.First.SetOccupied(deliveryDate);
        return new DeliveryResources(pDriver.First, pTruck.First, deliveryDate);
    }

    // return a Driver works at param zone, certified to transport a cargo of param weight at the closest day to param date.
    private Pair<Driver, DeliveryDate> FindAvailableDriver(Date date, ShippingZone zone, double weight, boolean[] supplierWorkingDays)
    {
        VehicleLicenseCategory matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        List<String> driverIds = DriversDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Driver resDriver = null;
        DeliveryDate resDate = null;
        if(driverIds != null)
        {
            for (String driverId : driverIds) {
                Driver driver = Drivers.get(driverId);
                if (resDriver != null)
                {
                    DeliveryDate d = driver.GetAvailableDeliveryDate(date.Month, date.Day, supplierWorkingDays);
                    if(d != null)
                    {
                        int diff = resDate.compareTo(d);
                        if (diff > 0) {
                            /* Found driver available on earlier date */
                            resDriver = driver;
                            resDate = d;
                        }
                    }
                }
                else
                {
                    /* initialize res at first iteration */
                    resDriver = driver;
                    resDate = driver.GetAvailableDeliveryDate(date.Month, date.Day, supplierWorkingDays);
                }

            }
            return new Pair<>(resDriver, resDate);
        }
        return new Pair<>(null, null);
    }

    // return a Truck works at param zone, able to load a cargo of param weight at the closest day to param date.
    private Pair<Truck, DeliveryDate> FindAvailableTruck(Date date, ShippingZone zone, double weight, boolean[] supplierWorkingDays)
    {
        VehicleLicenseCategory matchingLicense = VehicleLicenseCategory.FindLicense(weight);
        List<Long> trucksIds = TrucksDistribution[zone.ordinal()][matchingLicense.ordinal()];
        Truck resTruck = null;
        DeliveryDate resDate = null;
        if(trucksIds != null)
        {
            for (Long truckId : trucksIds) {
                Truck truck = Trucks.get(truckId);
                if (resTruck != null)
                {
                    DeliveryDate d = truck.GetAvailableDeliveryDate(date.Month, date.Day, supplierWorkingDays);
                    if(d != null)
                    {
                        int diff = resDate.compareTo(d);
                        if (diff > 0) {
                            /* Found truck available on earlier date */
                            resTruck = truck;
                            resDate = d;
                        }
                    }
                }
                else
                {
                    /* initialize res at first iteration */
                    resTruck = truck;
                    resDate = truck.GetAvailableDeliveryDate(date.Month, date.Day, supplierWorkingDays);
                }

            }
            return new Pair<>(resTruck, resDate);
        }
        return new Pair<>(null, null);
    }


    public boolean AddDriver(String id, String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone)
    {
        if(!Drivers.containsKey(id))
        {
            Driver newDriver = new Driver(id, name, cellphone, license, zone);
            Drivers.put(id, newDriver);
            DriversDistribution[zone.ordinal()][license.ordinal()].add(id);
            return true;
        }
        return false;
    }

    public boolean AddDriver(String id,String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone, Constraint constraint)
    {
        if(!Drivers.containsKey(id))
        {
            Driver newDriver = new Driver(id, name, cellphone, license, zone, constraint);
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
            Truck newTruck = new Truck(mlw, nw, vln, m, zone, VehicleLicenseCategory.FindLicense(mlw));
            Trucks.put(vln, newTruck);
            TrucksDistribution[zone.ordinal()][VehicleLicenseCategory.FindLicense(mlw).ordinal()].add(vln);
            return true;
        }
        return false;
    }

    public Driver RemoveDriver(String id)
    {
        Driver removedDriver = Drivers.remove(id);
        if(removedDriver != null)
        {
            int zone = removedDriver.Zone.ordinal();
            VehicleLicenseCategory licenseCategory = removedDriver.License;
            DriversDistribution[zone][licenseCategory.ordinal()].remove(id);
            DALController.getInstance().removeDriver(id);
            return removedDriver;
        }
        return null;
    }

    public Truck RemoveTruck(long vln)
    {
        Truck removedTruck = Trucks.remove(vln);
        if(removedTruck != null)
        {
            int zone = removedTruck.Zone.ordinal();
            VehicleLicenseCategory licenseCategory = VehicleLicenseCategory.FindLicense(removedTruck.MaxLoadWeight);
            TrucksDistribution[zone][licenseCategory.ordinal()].remove(vln);
            DALController.getInstance().removeTruck(vln);
            return removedTruck;
        }
        return null;
    }

    public boolean IsDriverOccupied(String driverId, int month, int day)
    {
        Driver driver = Drivers.get(driverId);
        if(driver != null)
            return driver.IsOccupied(month, day);
        return false;
    }

    public void SetConstraint(String driverId, Constraint constraint)
    {
        Driver driver = Drivers.get(driverId);
        if(driver != null)
            driver.SetConstraint(constraint);
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
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Shipping Zones ------------------------------\n");
        sb.append("------------------------------ South ------------------------------\n");
        for(ShippingZone zone : ShippingZone.values())
            sb.append(String.format("%s\n", zone));
        sb.append("------------------------------ North ------------------------------\n");
        return sb.toString();
    }

    public void Clear()
    {
        for(Driver driver : Drivers.values())
            RemoveDriver(driver.Id);
        for(Truck truck : Trucks.values())
            RemoveTruck(truck.VehicleLicenseNumber);
        Drivers.clear();
        Trucks.clear();

        for(ShippingZone zone : ShippingZone.values())
        {
            for(VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                DriversDistribution[zone.ordinal()][licenseCategory.ordinal()].clear();
                TrucksDistribution[zone.ordinal()][licenseCategory.ordinal()].clear();
            }
        }
    }

    public boolean CancelDelivery(CancelDeliveryRecipients cancelDeliveryRecipients)
    {
        if(cancelDeliveryRecipients != null)
        {
            Driver driver = Drivers.getOrDefault(cancelDeliveryRecipients.DriverId, null);
            Truck truck = Trucks.getOrDefault(cancelDeliveryRecipients.TruckId, null);
            if(driver != null && truck != null)
            {
                driver.SetAvailable(cancelDeliveryRecipients.DueDate);
                truck.SetAvailable(cancelDeliveryRecipients.DueDate);
                return true;
            }
        }
        return false;

    }

}

package DeliveryModule.BusinessLayer.Controller;

import DAL.Delivery_Personnel.DALController;
import DAL.Delivery_Personnel.DTO.DriverDTO;
import DAL.Delivery_Personnel.DTO.RecipeDTO;
import DAL.Delivery_Personnel.DTO.TruckDTO;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Element.Date;
import DeliveryModule.BusinessLayer.Type.RetCode;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;

import java.util.*;

public class DeliveryController
{
    private final PriorityQueue<Driver>[][] Drivers;
    private final PriorityQueue<Truck>[][] Trucks;
    private final Map<String, Recipe> Recipes;
    private final int NZONES = 9, NLICENSES = 3;

    private static class DeliveryControllerHolder2
    {
        private static final DeliveryController instance = new DeliveryController();
    }

    private DeliveryController()
    {
        Drivers = new PriorityQueue[NZONES][NLICENSES];
        Trucks = new PriorityQueue[NZONES][NLICENSES];
        Recipes = new HashMap<>();
        Init();
    }

    public static DeliveryController GetInstance() {
        return DeliveryControllerHolder2.instance;
    }


    private void Init()
    {
        InitDistributionTable();
        InitDrivers();
        InitTrucks();
        InitRecipes();
    }

    private void InitDistributionTable()
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                Drivers[zone.ordinal()][licenseCategory.ordinal()] = new PriorityQueue<>();
                Trucks[zone.ordinal()][licenseCategory.ordinal()] = new PriorityQueue<>();
            }
        }
    }
    private void InitDrivers()
    {
        List<DriverDTO> driversData = DALController.getInstance().getAllDrivers();
        for (DriverDTO src : driversData) {
            Driver driver = new Driver(src);
            Drivers[driver.Zone.ordinal()][driver.License.ordinal()].add(driver);
        }
    }

    private void InitTrucks()
    {
        List<TruckDTO> trucksData = DALController.getInstance().getAllTrucks();
        for (TruckDTO src : trucksData) {
            Truck truck = new Truck(src);
            Trucks[truck.Zone.ordinal()][truck.AuthorizedLicense.ordinal()].add(truck);
        }
    }

    private void InitRecipes()
    {
        List<RecipeDTO> recipes = DALController.getInstance().getAllDeliveries();
        for(RecipeDTO src : recipes)
            Recipes.put(src.OrderId, new Recipe(src));
    }

    private double CalculateDeliveryWeight(List<Product> products)
    {
        double CargoWeight = 0;
        final double MaxCargoWeight = VehicleLicenseCategory.GetMaxLoadWeight();

        boolean exceedMaxLoadWeight = false;
        // Calculate total cargo weight. Validate it doesn't exceed max load weight.
        for(Product product : products)
        {
            double totalProductWeight = product.Amount * product.WeightPerUnit;
            if(CargoWeight < 0 || CargoWeight + totalProductWeight < MaxCargoWeight)
                CargoWeight += totalProductWeight;
            else // cargo's weight exceeds the maximal load weights.
            {
                exceedMaxLoadWeight = true;
                break;

            }
        }
        return exceedMaxLoadWeight ? -1 : CargoWeight;
    }

    private Driver GetAvailableDriver(int shippingZoneOrdinal,  int matchingLicenseOrdinal)
    {
        Driver res = null;

        for(int i = matchingLicenseOrdinal; i<NLICENSES && res == null; i++)
            res = Drivers[shippingZoneOrdinal][i].poll();

        return res;
    }

    private Truck GetAvailableTruck(int shippingZoneOrdinal,  int matchingLicenseOrdinal)
    {
        Truck res = null;

        for(int i = 0; i <= matchingLicenseOrdinal && res == null; i++)
            res = Trucks[shippingZoneOrdinal][i].poll();

        return res;
    }

    private Truck FindTruck(long vln)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                for(Truck truck : Trucks[zone.ordinal()][licenseCategory.ordinal()])
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

    private Driver FindDriver(String Id)
    {
        for (ShippingZone zone : ShippingZone.values())
        {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                for(Driver driver : Drivers[zone.ordinal()][licenseCategory.ordinal()])
                {
                    if(driver.Id.equals(Id))
                    {
                        return driver;
                    }
                }
            }
        }
        return null;
    }

    public Recipe Deliver(DeliveryOrder deliveryOrder)
    {
        Recipe res;
        String orderId = deliveryOrder.OrderId;
        double CargoWeight = CalculateDeliveryWeight(deliveryOrder.RequestedProducts);
        if(CargoWeight == -1)
        {
            res = new Recipe(RetCode.FailedDelivery_CargoExceedMaxLoadWeight, orderId);
            Persist(res);
            return res;
        }

        int shippingZoneOrdinal = deliveryOrder.Supplier.Zone.ordinal();
        // license category with permission to deliver CargoWeight
        int matchingLicenseOrdinal = VehicleLicenseCategory.FindLicense(CargoWeight).ordinal();

        Driver selectedDriver = GetAvailableDriver(shippingZoneOrdinal, matchingLicenseOrdinal);
        if(selectedDriver == null)
        {
            res = new Recipe(RetCode.FailedDelivery_NoAvailableDriver, orderId);
            Persist(res);
            return res;
        }

        Truck selectedTruck = GetAvailableTruck(shippingZoneOrdinal, selectedDriver.License.ordinal());
        if(selectedTruck == null)
        {
            res = new Recipe(RetCode.FailedDelivery_NoAvailableTruck, orderId);
            Persist(res);
            return res;
        }

        Date driverDueDate = selectedDriver.Next(deliveryOrder.SubmissionDate, deliveryOrder.SupplierWorkingDays);
        Date truckDueDate = selectedTruck.Next(deliveryOrder.SubmissionDate, deliveryOrder.SupplierWorkingDays);

        // determine delivery day
        Date selectedDueDate = driverDueDate.compareTo(truckDueDate) > 0 ? driverDueDate : truckDueDate;
        Date submissionDate = deliveryOrder.SubmissionDate;

        if(submissionDate.DifferenceBetweenTwoDates(selectedDueDate) > 7)
        {
            res = new Recipe(RetCode.FailedDelivery_CannotDeliverWithinAWeek, orderId);
            Persist(res);
            return res;
        }


        selectedDriver.SetOccupied(selectedDueDate);
        selectedTruck.SetOccupied(selectedDueDate);

        // reinsert selected truck & driver to maintain min heap property
        Drivers[shippingZoneOrdinal][selectedDriver.License.ordinal()].add(selectedDriver);
        Trucks[shippingZoneOrdinal][selectedTruck.AuthorizedLicense.ordinal()].add(selectedTruck);

        res =  new Recipe(RetCode.SuccessfulDelivery,
                            orderId,
                            deliveryOrder.Supplier,
                            deliveryOrder.Client,
                            deliveryOrder.RequestedProducts,
                            selectedDueDate,
                            selectedDriver,
                            selectedTruck);

        Persist(res);
        return res;

    }

    public boolean AddDriver(String id, String name, String cellphone, VehicleLicenseCategory license, ShippingZone zone)
    {
        Driver newDriver = new Driver(id, name, cellphone, license, zone);
        if(!Drivers[zone.ordinal()][license.ordinal()].contains(newDriver))
        {
            Drivers[zone.ordinal()][license.ordinal()].add(newDriver);
            newDriver.Persist();
            return true;
        }
        return false;
    }

    public boolean AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone)
    {
        VehicleLicenseCategory matchingLicense = VehicleLicenseCategory.FindLicense(mlw-nw);
        Truck newTruck = new Truck(mlw, nw, vln, m, zone, matchingLicense);
        if(!Trucks[zone.ordinal()][matchingLicense.ordinal()].contains(newTruck))
        {
            Trucks[zone.ordinal()][matchingLicense.ordinal()].add(newTruck);
            newTruck.Persist();
            return true;
        }
        return false;
    }

    public Truck RemoveTruck(long vln)
    {
        Truck truck = FindTruck(vln);
        if(truck != null)
            DALController.getInstance().removeTruck(vln);
        return truck;
    }

    public Driver RemoveDriver(String id)
    {
        Driver driver = FindDriver(id);
        if(driver != null)
            DALController.getInstance().removeDriver(id);
        return driver;
    }

    public Recipe RemoveRecipe(String orderId)
    {
        Recipe r = Recipes.getOrDefault(orderId, null);
        if(r != null)
            DALController.getInstance().removeDelivery(orderId);
        return r;
    }

    private void ClearDrivers(Collection<Driver> drivers)
    {
        for(Driver driver : drivers)
            DALController.getInstance().removeDriver(driver.Id);
    }

    private void ClearTrucks(Collection<Truck> trucks)
    {
        for(Truck truck : trucks)
            DALController.getInstance().removeTruck(truck.VehicleLicenseNumber);
    }

    private void Persist(Recipe recipe)
    {
        Recipes.put(recipe.OrderId, recipe);
        recipe.Persist();
    }

    public void Clear()
    {
        for(ShippingZone zone : ShippingZone.values())
        {
            for(VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {

                ClearDrivers(Drivers[zone.ordinal()][licenseCategory.ordinal()]);
                Drivers[zone.ordinal()][licenseCategory.ordinal()].clear();
                ClearTrucks(Trucks[zone.ordinal()][licenseCategory.ordinal()]);
                Trucks[zone.ordinal()][licenseCategory.ordinal()].clear();
            }
        }

        for(String i : Recipes.keySet())
            RemoveRecipe(i);
    }

    public void CancelDelivery(String orderId)
    {
        Recipe recipe = Recipes.getOrDefault(orderId, null);
        if(recipe != null && recipe.Status == RetCode.SuccessfulDelivery)
        {
            Date canceledDeliveryDate = recipe.DueDate;

            // re-insert driver & truck to maintain min heap property
            Driver driver = recipe.Driver;
            driver.SetAvailable(canceledDeliveryDate);
            Drivers[driver.Zone.ordinal()][driver.License.ordinal()].remove(driver);
            Drivers[driver.Zone.ordinal()][driver.License.ordinal()].add(driver);

            Truck truck = recipe.Truck;
            truck.SetAvailable(canceledDeliveryDate);
            Trucks[truck.Zone.ordinal()][truck.AuthorizedLicense.ordinal()].remove(truck);
            Trucks[truck.Zone.ordinal()][truck.AuthorizedLicense.ordinal()].add(truck);
        }

        RemoveRecipe(orderId);
    }

    public boolean IsDriverOccupied(String id, int month, int day)
    {
        Driver driver = FindDriver(id);
        if(driver != null)
            return driver.IsOccupied(month, day);
        return false;
    }

    public void SetConstraint(String id, Constraint constraint)
    {
        Driver driver = FindDriver(id);
        if(driver != null)
        {
            // re-insert driver to maintain min heap property
            driver.SetConstraint(constraint);
            Drivers[driver.Zone.ordinal()][driver.License.ordinal()].remove(driver);
            Drivers[driver.Zone.ordinal()][driver.License.ordinal()].add(driver);
        }
    }

    public Driver GetDriver(String id)
    {
        return FindDriver(id);
    }

    public Truck GetTruck(long vln)
    {
        return FindTruck(vln);
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

    public String GetDrivers()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Drivers ------------------------------\n");
        for (ShippingZone zone : ShippingZone.values())
        {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                for(Driver driver : Drivers[zone.ordinal()][licenseCategory.ordinal()])
                    sb.append(String.format("%s\n", driver));
            }
        }
        return sb.toString();
    }

    public String GetTrucks()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Trucks ------------------------------\n");
        for (ShippingZone zone : ShippingZone.values())
        {
            for (VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            {
                for(Truck truck : Trucks[zone.ordinal()][licenseCategory.ordinal()])
                    sb.append(String.format("%s\n", truck));
            }
        }
        return sb.toString();
    }

    public String GetDeliveriesHistory()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Deliveries History ------------------------------\n");
        for(Recipe recipe: Recipes.values())
        {
            if(recipe.Status == RetCode.SuccessfulDelivery)
                sb.append(recipe);
        }
        return sb.toString();
    }

    public String GetFailedDeliveriesHistory()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Failed Deliveries History ------------------------------\n");
        for(Recipe recipe: Recipes.values())
        {
            if(recipe.Status != RetCode.SuccessfulDelivery)
                sb.append(recipe);
        }
        return sb.toString();
    }

    public static DeliveryController newInstanceForTests(String code){
        if(code.equals("sudo"))
            return new DeliveryController();
        else
            return GetInstance();
    }
}

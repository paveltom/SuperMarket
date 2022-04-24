package BusinessLayer.Controller;

import BusinessLayer.Element.*;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Type.Truck;
import BusinessLayer.Type.VehicleLicenseCategory;

/* Major controller
* Implemented using Singelton pattern.
* Provide communication between BusinessLayer controllers.
* Facade layer communicate with BusinessLayer through DeliveryController API'S.
*/
public class DeliveryController {
    private DeliveryExecutorController Executor;
    private DeliveryResourceController Resource;

    private static class DeliveryControllerHolder {
        private static DeliveryController instance = new DeliveryController();
    }

    private DeliveryController() {
        Executor = new DeliveryExecutorController();
        Resource = new DeliveryResourceController();
    }

    public static DeliveryController GetInstance() {
        return DeliveryControllerHolder.instance;
    }

    public DeliveryRecipe Deliver(DeliveryOrder deliveryOrder) {
        return Executor.Deliver(deliveryOrder);
    }

    public String GetDeliveriesHistory() {
        return Executor.GetDeliveriesHistory();
    }

    public Tuple<Driver, Truck, DeliveryDate> GetDeliveryDate(Date date, ShippingZone zone, double weight) {
        return Resource.GetDeliveryDate(date, zone, weight);
    }

    public String GetDrivers() {
        return Resource.GetDrivers();
    }

    public String GetTrucks() {
        return Resource.GetTrucks();
    }

    public void AddDriver(long id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone) {
        Resource.AddDriver(id, license, fname, lname, cellphone, zone);
    }

    public void AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone) {
        Resource.AddTruck(mlw, nw, vln, m, zone);
    }

    public void RemoveDriver(long id) {
        Resource.RemoveDriver(id);
    }

    public void RemoveTruck(long vln) {
        Resource.RemoveTruck(vln);
    }

    public Driver GetDriver(long id) {
        return Resource.GetDriver(id);
    }

    public Truck GetTruck(long vln) {
        return Resource.GetTruck(vln);
    }

    public String ShowShippingZone() {
        return Resource.ShowShippingZone();
    }

    public static DeliveryController newInstanceForTests(String code){
        if(code.equals("sudo"))
            return new DeliveryController();
        else
            return GetInstance();
    }

}

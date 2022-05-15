package BusinessLayer.Controller;

import BusinessLayer.Element.*;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Element.Truck;
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

    public Recipe Deliver(DeliveryOrder deliveryOrder) {
        return Executor.Deliver(deliveryOrder);
    }

    public String GetDeliveriesHistory() {
        return Executor.GetDeliveriesHistory();
    }

    public DeliveryResources GetDeliveryResources(Date date, ShippingZone zone, double weight) {
        return Resource.GetDeliveryResources(date, zone, weight);
    }

    public String GetDrivers() {
        return Resource.GetDrivers();
    }

    public String GetTrucks() {
        return Resource.GetTrucks();
    }

    public boolean AddDriver(String id, VehicleLicenseCategory license, String fname, String lname, String cellphone, ShippingZone zone) {
        return Resource.AddDriver(id, license, zone);
    }

    public boolean AddTruck(double mlw, double nw, long vln, String m, ShippingZone zone) {
        return Resource.AddTruck(mlw, nw, vln, m, zone);
    }

    public Driver RemoveDriver(String id) {
       return Resource.RemoveDriver(id);
    }

    public Truck RemoveTruck(long vln) {
        return Resource.RemoveTruck(vln);
    }

    public Driver GetDriver(String id) {
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

    public void Clear()
    {
        Resource.Clear();
        Executor.Clear();
    }

    public void SetConstraint(String id, Constraint constraint)
    {
        Resource.SetConstraint(id, constraint);
    }

}

package Facade;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.Element.Driver;
import BusinessLayer.Type.Truck;
import BusinessLayer.Type.VehicleLicenseCategory;
import BusinessLayer.Type.ShippingZone;
import Facade.FacadeObjects.FacadeDriver;
import Facade.FacadeObjects.FacadeTruck;

public class DeliveryResourcesService {

    private final DeliveryController delController;

    public DeliveryResourcesService(){
        delController = DeliveryController.GetInstance();
    }

    public DeliveryResourcesService(String code){
        if(code.equals("sudo"))
            delController = DeliveryController.newInstanceForTests();
        else
            delController = DeliveryController.GetInstance();
    }

    public Response addDriver(FacadeDriver facDriver){
        try {
            int id = facDriver.getId();
            String firstName = facDriver.getFirstName();
            String lastName = facDriver.getLastName();
            String cellphone = facDriver.getCellphone();
            VehicleLicenseCategory licCategory = VehicleLicenseCategory.valueOf(facDriver.getVehicleCategory());
            ShippingZone shipZone = ShippingZone.valueOf(facDriver.getLivingArea());
            delController.AddDriver(id, licCategory, firstName, lastName, cellphone, shipZone);
            String strId = id + "";
            return new ResponseT<String>(strId ,true);
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response addTruck(FacadeTruck facTruck){
        try {
            long licensePlate = facTruck.getLicensePlate();
            String model = facTruck.getModel();
            double netWeight = facTruck.getNetWeight();
            double maxLoadWeight = facTruck.getMaxLoadWeight();
            ShippingZone parkingArea = ShippingZone.valueOf(facTruck.getParkingArea());
            delController.AddTruck(maxLoadWeight, netWeight, licensePlate, model, parkingArea);
            String strLicPlate = licensePlate + "";
            return new ResponseT<String>(strLicPlate, true);
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeTruck(int licensePlate){
        try {
            delController.RemoveTruck(licensePlate);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeDriver(int id){
        try {
            delController.RemoveDriver(id);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<FacadeDriver> getDriverById(int id){
        Driver tempDriver = delController.GetDriver(id);
        return new ResponseT<>(new FacadeDriver( id, tempDriver.FirstName, tempDriver.LastName, tempDriver.Cellphone, tempDriver.License.toString(), ""), true);
    }

    public ResponseT<FacadeTruck> getTruckByPlate(int licPlate){
        Truck tempTruck = delController.GetTruck(licPlate);
        return new ResponseT<>(new FacadeTruck(licPlate, tempTruck.Model, "", tempTruck.NetWeight, tempTruck.MaxLoadWeight), true);
    }

    public ResponseT<String> showDrivers(){
        return new ResponseT<>(delController.GetDrivers(), true);
    }

    public ResponseT<String> showTrucks(){
        return new ResponseT<>(delController.GetTrucks(), true);
    }

    public ResponseT<String> showShippingZones(){
        return new ResponseT<>(delController.ShowShippingZone(), true);
    }

    public ResponseT<String> showLicenseCategories(){
        VehicleLicenseCategory[] cats = VehicleLicenseCategory.values();
        String names = VehicleLicenseCategory.GetVehicleLicenseCategoryName(cats[0]);
        for (int i = 1; i < cats.length; i++) { names += ", " +  VehicleLicenseCategory.GetVehicleLicenseCategoryName(cats[i]); }
        return new ResponseT<>(names, true);
    }
}

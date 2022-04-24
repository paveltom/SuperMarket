package Facade;

import BusinessLayer.Controller.DeliveryController;
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
            delController = DeliveryController.newInstanceForTests("sudo");
        else
            delController = DeliveryController.GetInstance();
    }

    public Response addDriver(FacadeDriver facDriver){
        try {
            long id = facDriver.getId();
            String firstName = facDriver.getFirstName();
            String lastName = facDriver.getLastName();
            String cellphone = facDriver.getCellphone();
            VehicleLicenseCategory licCategory = VehicleLicenseCategory.valueOf(facDriver.getVehicleCategory());
            ShippingZone shipZone = ShippingZone.valueOf(facDriver.getLivingArea());
            boolean added = delController.AddDriver(id, licCategory, firstName, lastName, cellphone, shipZone);
            if(added) {
                String strId = id + "";
                return new ResponseT<String>(strId, true);
            }
            else
                return new Response("System cannot allow to perform this action.");
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
            boolean added = delController.AddTruck(maxLoadWeight, netWeight, licensePlate, model, parkingArea);
            if(added) {
                String strLicPlate = licensePlate + "";
                return new ResponseT<String>(strLicPlate, true);
            }
            else
                return new Response("System cannot allow to perform this action.");
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeTruck(long licensePlate){
        try {
            delController.RemoveTruck(licensePlate);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeDriver(long id){
        try {
            delController.RemoveDriver(id);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<FacadeDriver> getDriverById(long id){
        FacadeDriver facadeDriver = new FacadeDriver(delController.GetDriver(id));
        return new ResponseT<>(facadeDriver, true);
    }

    public ResponseT<FacadeTruck> getTruckByPlate(long licPlate){
        FacadeTruck facadeTruck = new FacadeTruck(delController.GetTruck(licPlate));
        return new ResponseT<>(facadeTruck, true);
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

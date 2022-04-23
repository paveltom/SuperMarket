package Facade;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.Type.VehicleLicenseCategory;
import BusinessLayer.Type.ShippingZone;
import Facade.FacadeObjects.FacadeDriver;
import Facade.FacadeObjects.FacadeTruck;

public class DeliveryResourcesService {

    private DeliveryController delController;

    public DeliveryResourcesService(){
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
}

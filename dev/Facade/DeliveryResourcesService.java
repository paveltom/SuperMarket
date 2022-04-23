package Facade;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.Types.VehicleLicenseCategory;
import BusinessLayer.Types.ShippingZone;
import Facade.FacadeObjects.FacadeDriver;
import Facade.FacadeObjects.FacadeTruck;

import java.util.Date;
import java.util.List;

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
            return new ResponseT<Boolean>(true, strId);
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
            return new ResponseT<Boolean>(true, strLicPlate);
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}

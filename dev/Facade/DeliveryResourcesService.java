package Facade;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.Types.VehicleLicenseCategory;
import BusinessLayer.Types.ShippingZone;
import java.util.Date;
import java.util.List;

public class DeliveryResourcesService {

    private DeliveryController delController;

    public DeliveryResourcesService(){
        delController = DeliveryController.GetInstance();
    }

    //driveParams: id, firstName, lastName, cellphone, vehicleCategory, livingArea
    public Response addDriver(String[] driverParams){
        try {
            int id = Integer.parseInt(driverParams[0]);
            String firstName = driverParams[1];
            String lastName = driverParams[2];
            String cellphone = driverParams[3];
            VehicleLicenseCategory licCategory = VehicleLicenseCategory.valueOf(driverParams[4]);
            ShippingZone shipZone = ShippingZone.valueOf(driverParams[5]);
            delController.AddDriver(id, licCategory, firstName, lastName, cellphone, shipZone);
            String strId = id + "";
            return new ResponseT<Boolean>(true, strId);
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }


    //truckParams: licensePlate, model, netWeight, maxLoadWeight, parkingArea
    public Response addTruck(String[] truckParams){
        try {
            long licensePlate = Integer.parseInt(truckParams[0]);
            String model = truckParams[1];
            double netWeight = Double.parseDouble(truckParams[2]);
            double maxLoadWeight = Double.parseDouble(truckParams[3]);
            ShippingZone parkingArea = ShippingZone.valueOf(truckParams[4]);
            delController.AddTruck(maxLoadWeight, netWeight, licensePlate, model, parkingArea);
            String strLicPlate = licensePlate + "";
            return new ResponseT<Boolean>(true, strLicPlate);
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}

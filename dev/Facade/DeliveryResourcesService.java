package com.company.Facade;

import BusinessLayer.DataObject.Driver;
import BusinessLayer.DataObject.Truck;
import BusinessLayer.Controller.DeliveryResourceController;
import BusinessLayer.Types.VehicleLicenseCategory;
import BusinessLayer.Types.ShippingZone;
import java.util.Date;
import java.util.List;

public class DeliveryResourcesService {

    private DeliveryResourceController delResController;

    public DeliveryResourcesService(){
        delResController = new DeliveryResourceController();
    }

    //driveParams: id, firstName, lastName, cellphone, vehicleCategory, livingArea
    public void addDriver(String[] driverParams){
        int id = Integer.parseInt(driverParams[0]);
        String firstName = driverParams[1];
        String lastName = driverParams[2];
        String cellphone = driverParams[3];
        VehicleLicenseCategory licCategory = VehicleLicenseCategory.valueOf(driverParams[4]);
        ShippingZone shipZone = ShippingZone.valueOf(driverParams[5]);
        delResController.addDriver(id, firstName, lastName, cellphone, licCategory, shipZone);
    }


    //truckParams: licensePlate, model, netWeight, maxLoadWeight, parkingArea
    public void addTruck(String[] truckParams){
        int id = Integer.parseInt(truckParams[0]);
        String firstName = truckParams[1];
        String lastName = truckParams[2];
        String cellphone = truckParams[3];
        ShippingZone shipZone = ShippingZone.valueOf(truckParams[4]);
        delResController.addTruck();
    }
}

package Facade;

import BusinessLayer.Element.*;
import Facade.FacadeObjects.*;
import java.util.List;

public class Service implements IService{

    private DeliveryService deliveryService;
    private DeliveryResourcesService deliveryResourcesService;

    public Service(){
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
    }

    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        return deliveryService.deliver(origin, destination, orderId, facProducts, facSubDate);
    }

    public ResponseT<String> getDeliveryHistory(){
        return deliveryService.getDeliveryHistory();
    }

    public Response addDriver(FacadeDriver facadeDriver){
        return deliveryResourcesService.addDriver(facadeDriver);
    }

    public Response addTruck(FacadeTruck facadeTruck){ return deliveryResourcesService.addTruck(facadeTruck); }

    public Response removeTruck(int licensePlate){ return deliveryResourcesService.removeTruck(licensePlate); }

    public Response removeDriver(int id){ return deliveryResourcesService.removeDriver(id); }

    public ResponseT<FacadeDriver> getDriverById(int id){ return deliveryResourcesService.getDriverById(id); }

    public ResponseT<FacadeTruck> getTruckByPlate(int licPlate){ return deliveryResourcesService.getTruckByPlate(licPlate); }

    public ResponseT<String> showDrivers(){ return deliveryResourcesService.showDrivers(); }

    public ResponseT<String> showTrucks(){ return deliveryResourcesService.showTrucks(); }

    public ResponseT<String> showShippingZones(){ return deliveryResourcesService.showShippingZones(); }

    public ResponseT<String> showLicenseCategories(){ return deliveryResourcesService.showLicenseCategories(); }

    /*
    public ResponseT<String> getDeliveryHistoryBySupplierId(String supplierId){
        return deliveryService.getDeliveryHistoryBySupplierId(supplierId);
    }

    public ResponseT<String> getDeliveryHistoryByDate(Date deliveryDate){
        return deliveryService.getDeliveryHistoryByDate(deliveryDate);
    }

    public ResponseT<String> getDeliveryHistoryByZone(String zone){
        return deliveryService.getDeliveryHistoryByZone(zone);
    }
     */

}

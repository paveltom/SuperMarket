package BusinessLayer.Facade;

import BusinessLayer.DeliveryRecipe;
import jdk.jshell.spi.ExecutionControl;

import java.util.Date;
import java.util.List;

public class Service implements IService{

    private DeliveryService deliveryService;
    private DeliveryResourcesService deliveryResourcesService;

    public Service(){
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
    }

    public ResponseT<DeliveryRecipe> deliver(String[] orderParams){
        return deliveryService.deliver(orderParams);
    }

    public ResponseT<String> getDeliveryHistoryBySupplierId(String supplierId){
        return deliveryService.getDeliveryHistoryBySupplierId(supplierId);
    }

    public ResponseT<String> getDeliveryHistoryByDate(Date deliveryDate){
        return deliveryService.getDeliveryHistoryByDate(deliveryDate);
    }

    public ResponseT<String> getDeliveryHistoryByZone(String zone){
        return deliveryService.getDeliveryHistoryByZone(zone);
    }

    public ResponseT<List<String[]>> getDeliveryHistory(){
        return deliveryService.getDeliveryHistory();
    }

    public ResponseT<Boolean> addDriver(String[] driverParams){
        return deliveryResourcesService.addDriver(driverParams);
    }

    public ResponseT<Boolean> addTruck(String[] truckParams){
        return deliveryResourcesService.addTruck(truckParams);
    }

}

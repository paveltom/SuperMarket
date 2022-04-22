package Facade;

import BusinessLayer.DataObject.*;
import Facade.FacadeObjects.*;
import java.util.List;

public class Service implements IService{

    private DeliveryService deliveryService;
    private DeliveryResourcesService deliveryResourcesService;

    public Service(){
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
    }

    public Response deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        return deliveryService.deliver(origin, destination, orderId, facProducts, facSubDate);
    }

    public Response getDeliveryHistory(){
        return deliveryService.getDeliveryHistory();
    }

    public Response addDriver(String[] driverParams){
        return deliveryResourcesService.addDriver(driverParams);
    }

    public Response addTruck(String[] truckParams){
        return deliveryResourcesService.addTruck(truckParams);
    }

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

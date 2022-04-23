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

    public ResponseT<String> getDeliveryHistory(){
        return deliveryService.getDeliveryHistory();
    }

    public Response addDriver(FacadeDriver facadeDriver){
        return deliveryResourcesService.addDriver(facadeDriver);
    }

    public Response addTruck(FacadeTruck facadeTruck){ return deliveryResourcesService.addTruck(facadeTruck); }

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

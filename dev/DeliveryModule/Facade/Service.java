package DeliveryModule.Facade;

import DeliveryModule.Facade.FacadeObjects.*;
import java.util.List;

public class Service implements IService{

    private DeliveryService deliveryService;
    private DeliveryResourcesService deliveryResourcesService;

    //private PersonelModule pm;

    public Service(){ // needs to receive Personel Module instance
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
    }

    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        // i need that Nir will return driver's params - the one that was chosen to this delivery
        ResponseT<FacadeRecipe> res = deliveryService.deliver(origin, destination, orderId, facProducts, facSubDate);
        String[] output = new String[2];
        if(res.getErrorOccurred()){
            output[0] = "An error occured: " + res.getErrorMessage();
            output[1] = "";
        }
        //pm.addDriverFuture(id,date,shiftType); // sends new occupied driver's shift to Personel Module, shiftType - 0 or 1
        return res;
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

    public void addConstraints(String ID, FacadeDate date, int shift){
        // Doesn't has to be String[] - could be void
        // method that called by PersonelModule - sends constraints to Business Layer
        Response res = deliveryResourcesService.addConstraints(ID, date, shift);
        if(res.getErrorOccurred()){
            System.out.println(res.getErrorMessage());
        }
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

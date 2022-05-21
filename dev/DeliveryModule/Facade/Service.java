package DeliveryModule.Facade;

import DeliveryModule.Facade.FacadeObjects.*;
import java.util.List;

public class Service implements IService{

    private DeliveryService deliveryService;
    private DeliveryResourcesService deliveryResourcesService;
    private PersonelModule.BusinessLayer.ServiceLayer.Service pmService;

    //private PersonelModule pm;

    public Service(PersonelModule.BusinessLayer.ServiceLayer.Service otherService){ // needs to receive Personel Module instance
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
        this.pmService = otherService;
        pmService.AddDmService(this);
    }

    public Service(){ // needs to receive Personel Module instance
        deliveryService = new DeliveryService();
        deliveryResourcesService = new DeliveryResourcesService();
        this.pmService = null;
    }



    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, String orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        ResponseT<FacadeRecipe> res = deliveryService.deliver(origin, destination, orderId, facProducts, facSubDate);
        if(res.getErrorOccurred()){
            return res;
        }
        String driverID = res.getValue().getDeliveryPerson().getId();
        FacadeDate date = res.getValue().getDueDate();
        int shiftType = res.getValue().getDueDate().getShift();
        if(shiftType > 1) shiftType = 1;
        else shiftType = 0;
//        pmService.addDriverFuture(driverID, date, shiftType); // sends new occupied driver's shift to Personel Module, shiftType - 0 or 1
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

    public Response removeDriver(String id){ return deliveryResourcesService.removeDriver(id); }

    public ResponseT<FacadeDriver> getDriverById(String id){ return deliveryResourcesService.getDriverById(id); }

    public ResponseT<FacadeTruck> getTruckByPlate(int licPlate){ return deliveryResourcesService.getTruckByPlate(licPlate); }

    public ResponseT<String> showDrivers(){ return deliveryResourcesService.showDrivers(); }

    public ResponseT<String> showTrucks(){ return deliveryResourcesService.showTrucks(); }

    public ResponseT<String> showShippingZones(){ return deliveryResourcesService.showShippingZones(); }

    public ResponseT<String> showLicenseCategories(){ return deliveryResourcesService.showLicenseCategories(); }

    public void addConstraints(String ID, FacadeDate date, int shift){
        // method that called by PersonelModule - sends constraints to Business Layer
        Response res = deliveryResourcesService.addConstraints(ID, date, shift);
        if(res.getErrorOccurred()){
            System.out.println(res.getErrorMessage());
        }
    }

    public boolean isOccupied(String driverID, int month, int day){
        return deliveryResourcesService.isDriverOccupied(driverID, month, day);
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

package DeliveryModule.Facade;

import DeliveryModule.Facade.FacadeObjects.*;
import java.util.List;

public interface IService {

    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate);

    public ResponseT<String> getDeliveryHistory();

    public Response addDriver(FacadeDriver facadeDriver);

    public Response addTruck(FacadeTruck facadeTruck);

    public Response removeTruck(int licensePlate);

    public Response removeDriver(String id);

    public ResponseT<FacadeDriver> getDriverById(String id);

    public ResponseT<FacadeTruck> getTruckByPlate(int licPlate);

    public ResponseT<String> showDrivers();

    public ResponseT<String> showTrucks();

    public ResponseT<String> showShippingZones();

    public ResponseT<String> showLicenseCategories();

    public void addConstraints(String ID, FacadeDate date, int shift);

    public boolean isOccupied(String driverID, int month, int day);

}


/*

    public Response getDeliveryHistoryBySupplierId(String SupplierId);

    public Response getDeliveryHistoryByDate(FacadeDate deliveryDate);

    public Response getDeliveryHistoryByZone(String zone);

*/
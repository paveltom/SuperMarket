package Facade;

import Facade.FacadeObjects.*;
import java.util.List;

public interface IService {

    public Response deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate);

    public Response getDeliveryHistory();

    public Response addDriver(String[] driverParams);

    public Response addTruck(String[] truckParams);

}


/*

    public Response getDeliveryHistoryBySupplierId(String SupplierId);

    public Response getDeliveryHistoryByDate(FacadeDate deliveryDate);

    public Response getDeliveryHistoryByZone(String zone);

*/
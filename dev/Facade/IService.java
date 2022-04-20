package BusinessLayer.Facade;
import BusinessLayer.DeliveryRecipe;
import java.util.Date;
import java.util.List;

public interface IService {

    public ResponseT<DeliveryService> deliver(String[] orderParams);

    public ResponseT<String> getDeliveryHistoryBySupplierId(String SupplierId);

    public ResponseT<String> getDeliveryHistoryByDate(Date deliveryDate);

    public ResponseT<String> getDeliveryHistoryByZone(String zone);

    public ResponseT<List<String[]>> getDeliveryHistory();

    public Response addDriver(String[] driverParams);

    public Response addTruck(String[] truckParams);

}

/*

Delivery Controller:
+ Deliver(DeliveryOrder): DeliveryRecipe

+ GetDeliveryHistoryBySupplierId(SupplierId): string

+ GetDeliveryHistoryByDate(Date): string

+ GetDeliveryHistoryByZone(ShippingZone): string

+ GetDeliveryHistory(void): string

- DocumentDeliveryOrder(DeliveryOrder): string
Resources Controller:
+ AddDriver(...): void
+ AddTruck(...): void
ationalScalar rationalScalar);

*/
package Facade.Tests;

import BusinessLayer.Test.Testable;
import BusinessLayer.Type.ShippingZone;
import Facade.DeliveryService;
import Facade.FacadeObjects.*;
import Facade.ResponseT;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class DeliveryServiceTest implements Testable {

    private DeliveryService drService = new DeliveryService("sudo");

    @Before
    public void tearDown(){
        drService = new DeliveryService("sudo");
    }

    public void testDeliver() {
        drService = new DeliveryService();
        FacadeSite origin = new FacadeSite(ShippingZone.values()[0].toString(), "Rager 120", "Israel Israeli", "0123456789");
        FacadeSite dest = new FacadeSite(ShippingZone.values()[0].toString(), "Jabotinsky 10", "David Davidi", "9876543210");
        int orderId = 1100110011;
        List<FacadeProduct> facProducts = new ArrayList<>();
        facProducts.add(new FacadeProduct(123, 1000000, 153));
        FacadeDate facadeDate = new FacadeDate(20, 4, 2022);

        ResponseT<String> resDelivery = drService.deliver(origin, dest, orderId, facProducts, facadeDate);

        String result = resDelivery.getValue();

        String deliveries = drService.getDeliveryHistory().getValue();
        assertTrue(deliveries.contains(result));

        assertTrue(result.contains(orderId + ""));
        assertTrue(result.contains("Missing amount: 111111"));
    }

    @Test
    public void testGetDeliveryHistory(){
        drService = new DeliveryService();
        FacadeSite origin = new FacadeSite(ShippingZone.values()[0].toString(), "Rager 120", "Israel Israeli", "0123456789");
        FacadeSite dest = new FacadeSite(ShippingZone.values()[0].toString(), "Jabotinsky 10", "David Davidi", "9876543210");
        int orderId = 1100110011;
        List<FacadeProduct> facProducts = new ArrayList<>();
        facProducts.add(new FacadeProduct(123, 1000000, 153));
        FacadeDate facadeDate = new FacadeDate(20, 4, 2022);

        String strResHistory = drService.getDeliveryHistory().getValue();
        assertFalse(strResHistory.contains(orderId + ""));

        ResponseT<String> resDelivery = drService.deliver(origin, dest, orderId, facProducts, facadeDate);

        strResHistory = drService.getDeliveryHistory().getValue();
        assertTrue(strResHistory.contains(orderId + ""));


    }

    @Override
    public void ExecTest() {
        testDeliver();
        testGetDeliveryHistory();
    }
}

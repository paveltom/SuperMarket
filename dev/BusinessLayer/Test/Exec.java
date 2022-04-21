package BusinessLayer.Test;
import BusinessLayer.Controller.DeliveryExecutorController;
import BusinessLayer.DataObject.*;
import BusinessLayer.Types.ShippingZone;
import BusinessLayer.Types.VehicleLicenseCategory;
import java.time.LocalDate; // import the LocalDate class

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


public class Exec {
    public static void main(String[] args)
    {
        var s1 = new Site(ShippingZone.Negev, "A1", "N1", "C1");
        var s2 = new Site(ShippingZone.Negev, "A2", "N2", "C2");
        var products = Arrays.asList(new Product(1,37.5,13), new Product(2,1.73,646513), new Product(3,456,45564561));
        var deliveryOrder = new DeliveryOrder(s1, s2, 0, products, new Date());
        System.out.println(deliveryOrder);

        var d = new Driver(316534072, VehicleLicenseCategory.C,"Nir", "Malka", "0548826400");
        var t = new Truck(12,13,15,"Suzuki harata");

        var deliveryExecutor = new DeliveryExecutorController();
        var deliveryRecipe = deliveryExecutor.Deliver(deliveryOrder);
        System.out.println(deliveryRecipe);

        products = new ArrayList<>();
        for(var entry : deliveryRecipe.UnDeliveredProducts.entrySet())
            products.add(new Product(entry.getKey(), 1.48646, entry.getValue()));

        deliveryOrder = new DeliveryOrder(s1, s2, 1, products, new Date());
        System.out.println(deliveryOrder);
        deliveryRecipe = deliveryExecutor.Deliver(deliveryOrder);
        System.out.println(deliveryRecipe);

        System.out.println(deliveryExecutor.GetDeliveriesHistory());

    }
}

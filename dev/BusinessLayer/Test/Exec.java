package BusinessLayer.Test;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.DataObject.Date;
import BusinessLayer.DataObject.DeliveryOrder;
import BusinessLayer.DataObject.Product;
import BusinessLayer.DataObject.Site;
import BusinessLayer.Types.ShippingZone;

import java.util.Arrays;
import java.util.Calendar;


public class Exec {
    public static void main(String[] args)
    {
        var majorController = DeliveryController.GetInstance();
        var cal = Calendar.getInstance();
        //System.out.println(majorController.GetDrivers());
        //System.out.println(majorController.GetTrucks());

        var supplier = new Site(ShippingZone.Shfela_JerusalemMountains, "Ashdod Rotshield 25", "Nir Malka", "0548826400");
        var client = new Site(ShippingZone.Sharon, "Neve Tzedek", "Pavel tomshin", "0545555555");
        var order_1 = 0;
        var products = Arrays.asList(new Product(12,354.123,7896), new Product(1789,17.19313,14), new Product(777,32441.111,32));
        var submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        var shippingZone = ShippingZone.Shfela_JerusalemMountains;
        var deliveryOrder_1 = new DeliveryOrder(supplier, client, order_1, products, submissionDate, shippingZone);
        var deliveryOrder_2 = new DeliveryOrder(supplier, client, order_1 + 1, products, submissionDate, shippingZone);
        var deliveryOrder_3 = new DeliveryOrder(supplier, client, order_1 + 2, products, submissionDate, shippingZone);
        var deliveryOrder_4 = new DeliveryOrder(supplier, client, order_1 + 3, products, submissionDate, shippingZone);
        var deliveryOrder_5 = new DeliveryOrder(supplier, client, order_1 + 4, products, submissionDate, shippingZone);
        var deliveryOrder_6 = new DeliveryOrder(supplier, client, order_1 + 5, products, submissionDate, shippingZone);

        //System.out.println(deliveryOrder_1);
        var deliveryRecipe_1 = majorController.Deliver(deliveryOrder_1);
        var deliveryRecipe_2 = majorController.Deliver(deliveryOrder_2);
        var deliveryRecipe_3 = majorController.Deliver(deliveryOrder_3);
        var deliveryRecipe_4 = majorController.Deliver(deliveryOrder_4);
        var deliveryRecipe_5 = majorController.Deliver(deliveryOrder_5);
        var deliveryRecipe_6 = majorController.Deliver(deliveryOrder_6);

        System.out.println(deliveryOrder_1);
        System.out.println(deliveryRecipe_1);
        System.out.println(deliveryOrder_2);
        System.out.println(deliveryRecipe_2);
        System.out.println(deliveryOrder_3);
        System.out.println(deliveryRecipe_3);
        System.out.println(deliveryOrder_4);
        System.out.println(deliveryRecipe_4);
        System.out.println(deliveryOrder_5);
        System.out.println(deliveryRecipe_5);
        System.out.println(deliveryOrder_6);
        System.out.println(deliveryRecipe_6);

        //System.out.println(majorController.GetDeliveriesHistory());
    }
}

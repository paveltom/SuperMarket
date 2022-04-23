package BusinessLayer.Test;

import BusinessLayer.Controller.DeliveryController;
import BusinessLayer.Element.*;
import BusinessLayer.Type.ShippingZone;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryControllerTest implements Testable
{

    DeliveryController testObject = DeliveryController.GetInstance();
    Calendar cal = Calendar.getInstance();

    /*
     * init: Create 6 delivery orders.
     * Step: apply Deliver on each delivery order
     * Expect: Correct Due date for last DeliveryRecipe (assume 4 delivery shifts per day).
     * */
    @Test
    void deliver()
    {
        var supplier = new Site(ShippingZone.Shfela_JerusalemMountains, "Ashdod Rotshield 25", "Nir Malka", "0548826400");
        var client = new Site(ShippingZone.Sharon, "Neve Tzedek", "Pavel tomshin", "0545555555");
        var order_1 = 0;
        var products = Arrays.asList(new Product(12,354.123,7896), new Product(1789,17.19313,14), new Product(777,32441.111,32));
        var submissionDate = new Date(31, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        var shippingZone = ShippingZone.Shfela_JerusalemMountains;
        var deliveryOrder_1 = new DeliveryOrder(supplier, client, order_1, products, submissionDate, shippingZone);
        var deliveryOrder_2 = new DeliveryOrder(supplier, client, order_1 + 1, products, submissionDate, shippingZone);
        var deliveryOrder_3 = new DeliveryOrder(supplier, client, order_1 + 2, products, submissionDate, shippingZone);
        var deliveryOrder_4 = new DeliveryOrder(supplier, client, order_1 + 3, products, submissionDate, shippingZone);
        var deliveryOrder_5 = new DeliveryOrder(supplier, client, order_1 + 4, products, submissionDate, shippingZone);
        var deliveryOrder_6 = new DeliveryOrder(supplier, client, order_1 + 5, products, submissionDate, shippingZone);

        var deliveryRecipe_1 = testObject.Deliver(deliveryOrder_1);
        var deliveryRecipe_2 = testObject.Deliver(deliveryOrder_2);
        var deliveryRecipe_3 = testObject.Deliver(deliveryOrder_3);
        var deliveryRecipe_4 = testObject.Deliver(deliveryOrder_4);
        var deliveryRecipe_5 = testObject.Deliver(deliveryOrder_5);
        var deliveryRecipe_6 = testObject.Deliver(deliveryOrder_6);

        var exptected = new DeliveryDate(1,5,2022, 1);
        assertEquals(exptected, deliveryRecipe_6.DueDate);
    }

    @Override
    public void ExecTest()
    {
        deliver();
    }

    @Override
    public String toString()
    {
        return "DeliveryController";
    }
}
package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import org.junit.Test;
import static org.junit.Assert.*;import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


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
        Site supplier = new Site(ShippingZone.Shfela_JerusalemMountains, "Ashdod Rotshield 25", "Nir Malka", "0548826400");
        Site client = new Site(ShippingZone.Sharon, "Neve Tzedek", "Pavel tomshin", "0545555555");
        int order_1 = 6;
        List<Product> products = Arrays.asList(new Product(12,354.123,7896), new Product(1789,17.19313,14), new Product(777,32441.111,32));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, order_1, products, submissionDate);
        DeliveryOrder deliveryOrder_2 = new DeliveryOrder(supplier, client, order_1 + 1, products, submissionDate);
        DeliveryOrder deliveryOrder_3 = new DeliveryOrder(supplier, client, order_1 + 2, products, submissionDate);
        DeliveryOrder deliveryOrder_4 = new DeliveryOrder(supplier, client, order_1 + 3, products, submissionDate);
        DeliveryOrder deliveryOrder_5 = new DeliveryOrder(supplier, client, order_1 + 4, products, submissionDate);
        DeliveryOrder deliveryOrder_6 = new DeliveryOrder(supplier, client, order_1 + 5, products, submissionDate);

        Recipe deliveryRecipe_1 = testObject.Deliver(deliveryOrder_1);
        Recipe deliveryRecipe_2 = testObject.Deliver(deliveryOrder_2);
        Recipe deliveryRecipe_3 = testObject.Deliver(deliveryOrder_3);
        Recipe deliveryRecipe_4 = testObject.Deliver(deliveryOrder_4);
        Recipe deliveryRecipe_5 = testObject.Deliver(deliveryOrder_5);
        Recipe deliveryRecipe_6 = testObject.Deliver(deliveryOrder_6);

        assert(deliveryRecipe_6 instanceof DeliveryRecipe);
    }

    /*
     * init: Create a delivery order exceeds max load weight of heaviest truck (license C).
     * Step: apply Deliver
     * Expect: ExceedsMaxLoadWeight instance to be returned
     * */
    @Test
    void deliver_exceedsMaxLoadWeight()
    {
        Site supplier = new Site(ShippingZone.Shfela_JerusalemMountains, "Ashdod Rotshield 25", "Nir Malka", "0548826400");
        Site client = new Site(ShippingZone.Sharon, "Neve Tzedek", "Pavel tomshin", "0545555555");
        int order_1 = 0;
        List<Product> products = Arrays.asList(new Product(12,153,1000000));
        Date submissionDate = new Date(31, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        ShippingZone shippingZone = ShippingZone.Shfela_JerusalemMountains;
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, order_1, products, submissionDate);

        Recipe deliveryRecipe_1 = testObject.Deliver(deliveryOrder_1);

        assert(deliveryRecipe_1 instanceof  ExceedsMaxLoadWeight);
        assertEquals(order_1, ((ExceedsMaxLoadWeight) deliveryRecipe_1).OrderId);
    }

    @Override
    public void ExecTest()
    {

        deliver();
        deliver_exceedsMaxLoadWeight();
    }

    @Override
    public String toString()
    {
        return "DeliveryController";
    }
}

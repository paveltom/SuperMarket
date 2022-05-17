package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Controller.DeliveryResourceController;
import DeliveryModule.BusinessLayer.Element.Date;
import DeliveryModule.BusinessLayer.Element.DeliveryResources;
import DeliveryModule.BusinessLayer.Element.Driver;
import DeliveryModule.BusinessLayer.Element.Truck;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryResourceControllerTest implements Testable{

    DeliveryResourceController testObject = new DeliveryResourceController();


    /*
     * init: -
     * Step: Create delivery request of load weight of each vehicle license.
     * Expect: True: The assigned delivery driver is certified to deliver the cargo && cargo isn't exceeds vehicle max load restriction.
     * */
    @Test
    void getDeliveryDate()
    {
        final Date DATE = new Date(24, 4, 2022);
        final ShippingZone ZONE = ShippingZone.Shfela_JerusalemMountains;
        int i = 0;
        final double[] deliveryWeights = {3496351.00, 10045670.00, 16190000.0354};


        for(VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
        {
            DeliveryResources res = testObject.GetDeliveryResources(DATE, ZONE, deliveryWeights[i]);
            assertEquals(res.DeliveryDriver.License, licenseCategory);
            assertTrue(res.DeliveryTruck.MaxLoadWeight >= deliveryWeights[i]);
            i++;
        }
    }

    /*
     * init: Create two different Driver object(different HEAP address) with same key attribute(ID).
     * Step: Add one of the instances to testObject and than retrieve it back via testObject.GetDriver(id).
     * Expect: True: returned instance equals to other instance with same ID.
     * */
    @Test
    void addDriver()
    {
        final String ID = "123456789";
        final String FNAME = "ADDED_DRIVER_FNAME";
        final String LNAME = "ADDED_DRIVER_LNAME";
        final String CELLPHONE = "ADDED_DRIVER_CELLPHONE";

        testObject.AddDriver(ID,FNAME + LNAME,CELLPHONE, VehicleLicenseCategory.E, ShippingZone.JezreelValley);
        Driver actual = testObject.GetDriver(ID);
        Driver expected = new Driver(ID,FNAME + LNAME,CELLPHONE, VehicleLicenseCategory.E, ShippingZone.JezreelValley);

        assertEquals(expected, actual);


    }


    /*
     * init: Create two different Truck object(different HEAP address) with same key attribute(vln).
     * Step: Add one of the instances to testObject and than retrieve it back via testObject.GetTruck(vln).
     * Expect: True: returned instance equals to other instance with same ID.
     * */
    @Test
    void addTruck()
    {
        final double NET_WEIGHT = 10000000.00;
        final double MAXIMAL_LOAD_WEIGHT = 20000000.00;
        final long VEHICLE_NUMBER = 496351;
        final String TRUCK_MODEL = "ADDED_TRUCK_MODEL";

        testObject.AddTruck(NET_WEIGHT, MAXIMAL_LOAD_WEIGHT, VEHICLE_NUMBER, TRUCK_MODEL, ShippingZone.Galilee);
        Truck actual = testObject.GetTruck(VEHICLE_NUMBER);

        Truck expected = new Truck(NET_WEIGHT, MAXIMAL_LOAD_WEIGHT, VEHICLE_NUMBER, TRUCK_MODEL, ShippingZone.Galilee, VehicleLicenseCategory.C);

        assertEquals(expected, actual);
    }

    /*
     * init: Create Truck object.
     * Step: Add the truck and then remove it.
     * Expect: null: no truck is found.
     * */
    @Test
    void removeDriver()
    {
        final String ID = "123456798";
        final String FNAME = "ADDED_DRIVER_FNAME";
        final String LNAME = "ADDED_DRIVER_LNAME";
        final String CELLPHONE = "ADDED_DRIVER_CELLPHONE";

        testObject.AddDriver(ID, FNAME + LNAME,CELLPHONE, VehicleLicenseCategory.E, ShippingZone.JezreelValley);
        Driver actual = testObject.GetDriver(ID);

        assertNotNull(actual);

        testObject.RemoveDriver(ID);

        actual = testObject.GetDriver(ID);

        assertNull(actual);

    }

    /*
     * init: Create Truck object.
     * Step: Add the truck and then remove it.
     * Expect: null: no truck is found.
     * */
    @Test
    void removeTruck()
    {
        final double NET_WEIGHT = 10000000.00;
        final double MAXIMAL_LOAD_WEIGHT = 20000000.00;
        final long VEHICLE_NUMBER = 496315;
        final String TRUCK_MODEL = "ADDED_TRUCK_MODEL";

        testObject.AddTruck(NET_WEIGHT, MAXIMAL_LOAD_WEIGHT, VEHICLE_NUMBER, TRUCK_MODEL, ShippingZone.Galilee);
        Truck actual = testObject.GetTruck(VEHICLE_NUMBER);

        assertNotNull(actual);

        testObject.RemoveTruck(VEHICLE_NUMBER);

        actual = testObject.GetTruck(VEHICLE_NUMBER);

        assertNull(actual);
    }

    @Override
    public void ExecTest()
    {
        getDeliveryDate();
        addDriver();
        addTruck();
        removeDriver();
        removeTruck();
    }

    @Override
    public String toString()
    {
        return "DeliveryResourceController";
    }
}
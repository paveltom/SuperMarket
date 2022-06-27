package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Element.Date;
import DeliveryModule.BusinessLayer.Type.RetCode;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.*;

public class DeliveryControllerTest {

    DeliveryController testObj = DeliveryController.GetInstance();
    Calendar cal = Calendar.getInstance();
    final ShippingZone srcZone = ShippingZone.Shfela_JerusalemMountains;
    final ShippingZone dstZone = ShippingZone.Sharon;

    final String srcAddr = "Ashdod Rotshield 25", srcName = "Nir Malka", srcCell = "0548826400";
    final String dstAddr = "Neve Tzedek", dstName = "Pavel tomshin", dstCell = "0545555555";

    final double TRUCK_NET_WEIGHT = 1000000.00, TRUCK_LOAD_WEIGHT = 3500000.00,
            P1_WEIGHT = 2502.55, P2_WEIGHT = 354.123;
    final long TRUCK_LICENSE_NUMBER = 496351;
    final int P1_AMOUNT = 6394, P1_ID = 12, P2_AMOUNT = 78, P2_ID = 27;

    final Site supplier = new Site(srcZone, srcAddr, srcName, srcCell);
    final Site client = new Site(dstZone, dstAddr, dstName, dstCell);

    final String driverId = "316534072", driverName = "Nir Malka", driverCell = "0548826400";
    final String TRUCK_MODEL = "Volvo V9";
    final VehicleLicenseCategory driverLicenseCategory = VehicleLicenseCategory.C1;

    final int DAY = 1, MONTH = 7, YEAR = 2022;

    static final boolean[] supplier_constraint = new boolean[7];
    static {
        Arrays.fill(supplier_constraint, true);
    }



    @Before
    public void setUp() throws Exception {
        clear();
    }

    @After
    public void tearDown() throws Exception {
        clear();
    }

    @Test
    public void deliver_failed_max_load_weight() {
        int norder = 13;
        List<Product> products = Arrays.asList(new Product(P1_ID, P1_WEIGHT, P1_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, String.valueOf(norder), products, submissionDate);

        Recipe r = testObj.Deliver(deliveryOrder_1);
        assertEquals(r.Status, RetCode.FailedDelivery_CargoExceedMaxLoadWeight);
    }

    @Test
    public void deliver_failed_no_available_driver() {
        int norder = 19;
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, String.valueOf(norder), products, submissionDate);

        Recipe r = testObj.Deliver(deliveryOrder_1);
        assertEquals(r.Status, RetCode.FailedDelivery_NoAvailableDriver);
    }

    @Test
    public void deliver_failed_no_available_truck() {
        int norder = 21;
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, String.valueOf(norder), products, submissionDate);

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);

        Recipe r = testObj.Deliver(deliveryOrder_1);
        assertEquals(r.Status, RetCode.FailedDelivery_NoAvailableTruck);
    }

    //ass 3
    @Test
    public void deliver_failed_cannot_deliver_within_a_week() {
        int norder = 55;
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(DAY, MONTH, YEAR);
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, String.valueOf(norder), products, submissionDate);

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);

        List<Shift> shifts = new ArrayList<>();
        LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY);
        int month = localDate.getMonthValue();

        int ndays = 8;
        while (ndays > 0) {
            for (int i = 0; i <= 3; i++) {
                shifts.add(new Shift(localDate.getDayOfMonth(), i));
            }
            localDate = localDate.plusDays(1);
            ndays--;
        }

        Constraint constraint = new Constraint(month, shifts);
        testObj.SetConstraint(driverId, constraint);

        Recipe r = testObj.Deliver(deliveryOrder_1);
        assertEquals(r.Status, RetCode.FailedDelivery_CannotDeliverWithinAWeek);
    }

    // insert 1000 drivers; order 28 deliveries; verify different driver is assigned to each delivery;
    // only one truck -- cannot deliver on same shift
    @Test
    public void deliver_balanced_selection_single_truck() {
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        LocalDate localDate = LocalDate.now();
        Date submissionDate = new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());

        addDriver();
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);


        int norders = 28;
        Driver prevDriver = null, currDriver;
        Date prevDeliveryDate = null, currDeliveryDate;
        while (norders-- > 0) {
            DeliveryOrder deliveryOrder = new DeliveryOrder(supplier, client, String.valueOf(norders), products, submissionDate);
            Recipe r = testObj.Deliver(deliveryOrder);
//            System.out.println(r.DueDate);
            assertEquals(RetCode.SuccessfulDelivery, r.Status);
            currDriver = r.Driver;
//            System.out.printf("\ncurrDriver = %s\nprevDriver = %s\n", currDriver, prevDriver);
            assertNotEquals(currDriver, prevDriver);
            currDeliveryDate = r.DueDate;
//            System.out.printf("%s%s\n", currDriver, currDeliveryDate);
            assertNotEquals(prevDeliveryDate, currDeliveryDate);
            prevDriver = currDriver;
            prevDeliveryDate = currDeliveryDate;
        }
    }

    // insert 1000 drivers; order 28 deliveries; verify different assigned to each delivery;
    // ntrucks = ndrivers -- same delivery date for all orders
    @Test
    public void deliver_balanced_selection_multiple_trucks() {
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        addDriver();
        addTruck();

        int norders = 28;
        Driver prevDriver = null, currDriver;
        Date prevDeliveryDate = null, currDeliveryDate;
        boolean firstIter = true;
        while (norders-- > 0) {
            DeliveryOrder deliveryOrder = new DeliveryOrder(supplier, client, String.valueOf(norders), products, submissionDate);
            Recipe r = testObj.Deliver(deliveryOrder);
            assertEquals(RetCode.SuccessfulDelivery, r.Status);
            currDriver = r.Driver;
            assertNotEquals(currDriver, prevDriver);
            currDeliveryDate = r.DueDate;
            if (!firstIter)
                assertEquals(prevDeliveryDate, currDeliveryDate);
            else {
                firstIter = false;
            }
            prevDriver = currDriver;
            prevDeliveryDate = currDeliveryDate;
//            System.out.printf("%s%s\n", currDriver, currDeliveryDate);
        }
    }

    @Test
    public void addDriver() {
        long ndrivers = 1000;
        while (ndrivers-- > 0) {
            String s = String.valueOf(ndrivers);
            testObj.AddDriver(s, driverName, driverCell, driverLicenseCategory, srcZone);
            Driver driver = testObj.GetDriver(s);
            assertEquals(s, driver.Id);
        }
    }

    @Test
    public void addTruck() {
        long ntrucks = 1000;
        while (ntrucks-- > 0) {
            testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, ntrucks, TRUCK_MODEL, srcZone);
            Truck truck = testObj.GetTruck(ntrucks);
            assertEquals(truck.VehicleLicenseNumber, ntrucks);
        }
    }

    @Test
    public void removeTruck() {
        long ntrucks = 1000;
        while (ntrucks-- > 0) {
            Truck currTruck = testObj.GetTruck(ntrucks);
            Truck removedTruck = testObj.RemoveTruck(ntrucks);
            assertEquals(currTruck, removedTruck);
        }
    }

    @Test
    public void removeDriver() {
        long ndrivers = 1000;
        while (ndrivers-- > 0) {
            String s = String.valueOf(ndrivers);
            Driver currDriver = testObj.GetDriver(s);
            Driver removedDriver = testObj.RemoveDriver(s);
            assertEquals(currDriver, removedDriver);
        }
    }

    @Test
    public void cancelDelivery() {
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);

        int norders = 28;
        while (norders-- > 0) {
            DeliveryOrder deliveryOrder = new DeliveryOrder(supplier, client, String.valueOf(norders), products, submissionDate);
            Recipe r = testObj.Deliver(deliveryOrder);
            System.out.println(r.DueDate);
            assertEquals(RetCode.SuccessfulDelivery, r.Status);
            testObj.CancelDelivery(r.OrderId);
            assertEquals(r.DueDate, testObj.GetDriver(driverId).Next());
        }
    }

    @Test
    public void isDriverOccupied() {

        int norder = 9876;
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        DeliveryOrder deliveryOrder_1 = new DeliveryOrder(supplier, client, String.valueOf(norder), products, submissionDate);

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);

        Recipe r = testObj.Deliver(deliveryOrder_1);

        assertEquals(r.Status, RetCode.SuccessfulDelivery);
        boolean res = testObj.IsDriverOccupied(driverId, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        assertTrue(res);

    }

    // set constraint for a whole week. expect next available delivery day to be after exactly one week.
    @Test
    public void setConstraint()
    {
        Date submissionDate = new Date(DAY, MONTH, YEAR);

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);

        // set driver as unavailable for whole week
        List<Shift> shifts = new ArrayList<>();
        LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY);
        int month = localDate.getMonthValue();

        int ndays = 7;
        while (ndays > 0) {
            for (int i = 0; i <= 3; i++) {
                shifts.add(new Shift(localDate.getDayOfMonth(), i));
            }
            localDate = localDate.plusDays(1);
            ndays--;
        }

        Constraint constraint = new Constraint(month, shifts);
        testObj.SetConstraint(driverId, constraint);

        boolean res;

        for (Shift shift : constraint.WeeklyConstraints) {
            res = testObj.IsDriverOccupied(driverId, constraint.Month, shift.Day);
            assertTrue(res);
        }

        Date availableShift = testObj.GetDriver(driverId).Next(submissionDate, supplier_constraint);
        System.out.println(availableShift);
        assertEquals(submissionDate.DifferenceBetweenTwoDates(availableShift), 7);
    }

    @Test
    public void showShippingZone() {
        System.out.println(testObj.ShowShippingZone());
    }

    @Test
    public void getDrivers() {
        testObj.GetDrivers();
    }

    @Test
    public void getTrucks() {
        testObj.GetTrucks();
    }

    @Test
    public void getDeliveriesHistory() {
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        addDriver();
        addTruck();

        int norders = 28;
        while (norders-- > 0) {
            DeliveryOrder deliveryOrder = new DeliveryOrder(supplier, client, String.valueOf(norders), products, submissionDate);
            Recipe r = testObj.Deliver(deliveryOrder);
            assertEquals(RetCode.SuccessfulDelivery, r.Status);
        }

        System.out.println(testObj.GetDeliveriesHistory());
    }

    @Test
    public void getFailedDeliveriesHistory() {
        deliver_failed_max_load_weight();
        deliver_failed_cannot_deliver_within_a_week();
        deliver_failed_no_available_driver();
        deliver_failed_no_available_truck();
        System.out.println(testObj.GetFailedDeliveriesHistory());
    }

    @Test
    public void manyOrders()
    {
        List<Product> products = Arrays.asList(new Product(P2_ID, P2_WEIGHT, P2_AMOUNT));
        Date submissionDate = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        testObj.AddDriver(driverId, driverName, driverCell, driverLicenseCategory, srcZone);
        testObj.AddTruck(TRUCK_LOAD_WEIGHT, TRUCK_NET_WEIGHT, TRUCK_LICENSE_NUMBER, TRUCK_MODEL, srcZone);

        Date prev_date = null;
        int norders = 32;
        while (norders-- > 0)
        {
            DeliveryOrder deliveryOrder = new DeliveryOrder(supplier, client, String.valueOf(norders), products, submissionDate);
            Recipe r = testObj.Deliver(deliveryOrder);
            System.out.println(r);
            if(prev_date != null)
            {
                int diff = r.DueDate.compareTo(prev_date);
                assertEquals(diff, 1);
            }
            prev_date = r.DueDate;
        }
        // expect next available delivery date to be one week later than submissionDate
        Recipe r = testObj.Deliver(new DeliveryOrder(supplier, client, String.valueOf(--norders), products, submissionDate));
        assertEquals(RetCode.FailedDelivery_CannotDeliverWithinAWeek, r.Status);
    }

    @Test
    public void clear()
    {
        testObj.Clear();
    }

}
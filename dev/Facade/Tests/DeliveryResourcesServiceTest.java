package Facade.Tests;

import BusinessLayer.Test.Testable;
import BusinessLayer.Type.ShippingZone;
import BusinessLayer.Type.VehicleLicenseCategory;
import Facade.DeliveryResourcesService;
import Facade.FacadeObjects.FacadeDriver;
import Facade.FacadeObjects.FacadeTruck;
import Facade.ResponseT;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryResourcesServiceTest implements Testable {

    private DeliveryResourcesService drService = new DeliveryResourcesService("sudo");

    @Before
    public void tearDown(){
        drService = new DeliveryResourcesService("sudo");
    }

    @Test
    public void testAddDriver() {
        //DeliveryResourcesService drService = new DeliveryResourcesService();
        FacadeDriver facadeDriver = getTempDriver();
        String allDrivers = drService.showDrivers().getValue();
        CharSequence charSequence = facadeDriver.getId() + "";
        assertFalse(allDrivers.contains(charSequence));

        drService.addDriver(facadeDriver);

        allDrivers = drService.showDrivers().getValue();

        assertTrue(allDrivers.contains(charSequence));

        charSequence = facadeDriver.getFirstName();
        assertTrue(allDrivers.contains(charSequence));

        charSequence = facadeDriver.getLastName();
        assertTrue(allDrivers.contains(charSequence));

        charSequence = facadeDriver.getCellphone();
        assertTrue(allDrivers.contains(charSequence));
    }

    @Test
    public void testGetDriverByID() {
        //DeliveryResourcesService drService = new DeliveryResourcesService();
        FacadeDriver facadeDriver = getTempDriver();

        drService.addDriver(facadeDriver);
        ResponseT<FacadeDriver> resDriver = drService.getDriverById(facadeDriver.getId());
        assertEquals(facadeDriver.getFirstName(), resDriver.getValue().getFirstName());
        assertEquals(facadeDriver.getLastName(), resDriver.getValue().getLastName());
        assertEquals(facadeDriver.getCellphone(), resDriver.getValue().getCellphone());
        assertEquals(facadeDriver.getVehicleCategory(), resDriver.getValue().getVehicleCategory());
    }

    @Test
    public void testRemoveDriver() {
        //DeliveryResourcesService drService = new DeliveryResourcesService();
        FacadeDriver facadeDriver = getTempDriver();

        drService.addDriver(facadeDriver);
        String allDrivers = drService.showDrivers().getValue();
        CharSequence charSequence = facadeDriver.getId() + "";
        assertTrue(allDrivers.contains(charSequence));

        drService.removeDriver(facadeDriver.getId());

        allDrivers = drService.showDrivers().getValue();

        assertFalse(allDrivers.contains(charSequence));
    }

    @Test
    public void testAddTruck() {
        FacadeTruck facadeTruck = getTempTruck();

        String allTrucks = drService.showTrucks().getValue();
        CharSequence charSequence = facadeTruck.getModel();
        assertFalse(allTrucks.contains(charSequence));

        drService.addTruck(facadeTruck);

        allTrucks = drService.showTrucks().getValue();

        assertTrue(allTrucks.contains(charSequence));
    }

    @Test
    public void testGetTruckByID() {
        FacadeTruck facadeTruck = getTempTruck();

        drService.addTruck(facadeTruck);

        ResponseT<FacadeTruck> resTruck = drService.getTruckByPlate(facadeTruck.getLicensePlate());

        assertEquals(facadeTruck.getLicensePlate(), resTruck.getValue().getLicensePlate());
        assertEquals(facadeTruck.getModel(), resTruck.getValue().getModel());
        assertEquals(facadeTruck.getNetWeight(), resTruck.getValue().getNetWeight());
        assertEquals(facadeTruck.getMaxLoadWeight(), resTruck.getValue().getMaxLoadWeight());
    }

    @Test
    public void testRemoveTruck() {
        FacadeTruck facadeTruck = getTempTruck();

        String allTrucks = drService.showTrucks().getValue();
        System.out.println(allTrucks);
        assertFalse(allTrucks.contains(facadeTruck.getModel()));

        drService.addTruck(facadeTruck);
        allTrucks = drService.showTrucks().getValue();

        assertTrue(allTrucks.contains(facadeTruck.getModel()));

        drService.removeTruck(facadeTruck.getLicensePlate());

        allTrucks = drService.showTrucks().getValue();

        assertFalse(allTrucks.contains(facadeTruck.getModel()));

    }

    private FacadeDriver getTempDriver(){
        int id = 326971249;
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String cellphone = "0501234567";
        VehicleLicenseCategory vLicCat = VehicleLicenseCategory.values()[0];
        ShippingZone sZone = ShippingZone.values()[0];
        FacadeDriver facDriver = new FacadeDriver(id, firstName, lastName, cellphone, vLicCat.toString(), sZone.toString());
        return facDriver;
    }

    private FacadeTruck getTempTruck(){
        int licPlate = 326971249;
        String model = "testModel";
        double netWeight = 1000000000.0;
        double maxLoadWeight = 500000.0;
        ShippingZone pZone = ShippingZone.values()[0];
        FacadeTruck facTruck = new FacadeTruck(licPlate, model, pZone.toString(), netWeight, maxLoadWeight);
        return facTruck;
    }


    @Override
    public void ExecTest() {

        testAddDriver();
        testGetDriverByID();
        testRemoveDriver();
        testAddTruck();
        testGetTruckByID();
        testRemoveTruck();

    }

}

package Tests;

import DAL.Delivery_Personnel.DALController;
import DAL.Delivery_Personnel.DataBaseConnection;
import DeliveryModule.BusinessLayer.Element.Product;
import DeliveryModule.BusinessLayer.Element.Recipe;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.BusinessLayer.Type.VehicleLicenseCategory;
import DeliveryModule.Facade.*;
import DeliveryModule.Facade.FacadeObjects.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DeliveryTests {
    Service service;
    PersonelModule.BusinessLayer.ServiceLayer.Service perService;

    @Before
    public void setUp() throws Exception {
        DALController.getInstance().deleteDB();
//        String dir = System.getProperty("user.dir");
//        File f = new File(dir + "/dev/DataBase/PerDel.db");
//        boolean deleted = false;
//        boolean exists = f.exists();
//        if(exists) deleted = f.delete();
        perService = new PersonelModule.BusinessLayer.ServiceLayer.Service();
        service = new Service(perService);
        service.tearDownDelControllerSingletone();
    }

//    @Test
//    public void changeSMQual() {
//        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
//        assertEquals("Changed SMQualification successfully",service.ChangeQual("212589691","no"));
//        service.DeleteWorker("212589691");//To remove from DB
//    }

    @Test
    public void regularDelivery() { // add delivery, add same id delivery
        addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertFalse(res.getErrorOccurred());

        res = service.getDeliveryHistory();
        assertFalse(res.getErrorOccurred());

        assertTrue(res.getValue().contains("Order: 135792468"));
        assertTrue(res.getValue().contains("testAddress1"));
    }

    @Test
    public void sameIDDelivery() {
        addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res1 = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertFalse(res1.getErrorOccurred());

        ResponseT<String> res2 = service.deliver(facadeSiteNumber(3, "Negev"), facadeSiteNumber(4, "Negev"), "135792468", listOfFacProducts(), facadeDate());
        assertTrue(res2.getErrorOccurred());

        history = service.getDeliveryHistory();

        assertTrue(history.getValue().contains("Order: 135792468"));
        assertTrue(history.getValue().contains("testAddress1"));
        assertFalse(history.getValue().contains("testAddress3"));
    }


    @Test
    public void noDriverDelivery() {
        //addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertTrue(res.getErrorOccurred());
    }

    @Test
    public void noTruckDelivery() {
        addDriver("987654321", "Golan", null);
        //addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertTrue(res.getErrorOccurred());
    }

    @Test
    public void overWeightedDelivery() {
        addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 10);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertTrue(res.getErrorOccurred());
    }

    @Test
    public void cancelDelivery() { // add delivery => cancel it => validate through isOccupied and get history
        addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertFalse(res.getErrorOccurred());

        history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertTrue(history.getValue().contains("135792468"));

        service.cancelDelivery("135792468"); // restore after Nir updates RemoveRecipe
        history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("135792468"));
    }

    @Test
    public void getDeliveryHistory() { // validate history before adding, after adding, after deleting
        addDriver("987654321", "Golan", null);
        addTruck(123456789, "Golan", 0);

        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1, "Golan"), facadeSiteNumber(2, "Golan"), "135792468", listOfFacProducts(), facadeDate());
        assertFalse(res.getErrorOccurred());
        String[] included = {"Order: 135792468", "Supplier", "testAddress1", "testContact1", "01234567891",
                                                 "Client", "testAddress2", "testContact2", "01234567892",
                                                ""+listOfFacProducts().get(0).getId(), ""+listOfFacProducts().get(0).getAmount(), ""+listOfFacProducts().get(0).getWeight(),
                                                ""+listOfFacProducts().get(1).getId(), ""+listOfFacProducts().get(1).getAmount(), ""+listOfFacProducts().get(1).getWeight(),
                                                ""+listOfFacProducts().get(2).getId(), ""+listOfFacProducts().get(2).getAmount(), ""+listOfFacProducts().get(2).getWeight()};

        history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        for(String s : included){
            assertTrue(history.getValue().contains(s));
        }

        service.cancelDelivery("135792468"); // restore after Nir updates RemoveRecipe
        history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("135792468"));
    }

    @Test
    public void showShippingZones() {
        ResponseT<String> res = service.showShippingZones();
        assertFalse(res.getErrorOccurred());
        for (ShippingZone value : ShippingZone.values()) {
            assertTrue(res.getValue().contains(value.name()));
        }
    }

    @Test
    public void addTruck(){
        ResponseT<String> res = service.showTrucks();
        assertFalse(res.getErrorOccurred());

        addTruck(326971231, "Golan", 0);

        res = service.showTrucks();
        assertFalse(res.getErrorOccurred());
        assertTrue(res.getValue().contains("326971231"));

    }
//
//    public Response removeTruck(long licensePlate);
//
//    public ResponseT<FacadeDriver> getDriverById(String id);
//
//    public ResponseT<FacadeTruck> getTruckByPlate(long licPlate);
//
//    public ResponseT<String> showDrivers();
//
//    public ResponseT<String> showTrucks();
//
//    public void addConstraints(String ID, FacadeDate date, int shift);
//
//    public boolean isOccupied(String driverID, int month, int day);



    private FacadeDate facadeDate(){
        LocalDate currTime = LocalDate.now();
        FacadeDate facDate = new FacadeDate(currTime.getDayOfMonth(), currTime.getMonthValue(), currTime.getYear());
        return facDate;
    }

    private List<FacadeProduct> listOfFacProducts(){
        FacadeProduct p1 = new FacadeProduct(123, 2, 1000);
        FacadeProduct p2 = new FacadeProduct(456, 5, 500);
        FacadeProduct p3 = new FacadeProduct(789, 10, 100);
        List<FacadeProduct> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        return list;
    }

    private FacadeSite facadeSiteNumber(int number, String zone){
        return new FacadeSite(zone, "testAddress" + number, "testContact" + number, "0123456789" + number);
    }

    private void addDriver(String id9Digits, String area, String optionalNonNullLicense){
        if(optionalNonNullLicense == null)
            perService.AddDriver(id9Digits,"Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","C",area,"0525670092");
        else
            perService.AddDriver(id9Digits,"Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...",optionalNonNullLicense,area,"0525670092");

    }

    private void addTruck(long licPlate, String area, double optionalNonZeroWeight){
        // long licensePlate, String model, String parkingArea, double netWeight, double maxLoadWeight
        if(optionalNonZeroWeight == 0)
            service.addTruck(new FacadeTruck(licPlate, "model" + licPlate, area, 10000000, 10000000));
        else
            service.addTruck(new FacadeTruck(licPlate, "model" + licPlate, area, optionalNonZeroWeight, optionalNonZeroWeight));

    }

}



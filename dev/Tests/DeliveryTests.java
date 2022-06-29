package Tests;

import DeliveryModule.BusinessLayer.Type.ShippingZone;
import DeliveryModule.Facade.*;
import DeliveryModule.Facade.FacadeObjects.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeliveryTests {
    Service service;
    PersonelModule.BusinessLayer.ServiceLayer.Service perService;

    @Before
    public void setUp() throws Exception {
        String dir = System.getProperty("user.dir");
        File f = new File(dir + "/PerDel.db");
        if(f.exists()) f.delete();
        perService = new PersonelModule.BusinessLayer.ServiceLayer.Service();
        service = new Service(perService);
    }


//    @Test
//    public void changeSMQual() {
//        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
//        assertEquals("Changed SMQualification successfully",service.ChangeQual("212589691","no"));
//        service.DeleteWorker("212589691");//To remove from DB
//    }

    @Test
    public void regularDeliver() { // add delivery, add same id delivery


    }

    public void overWeightedDelivery() { // add over-weighted delivery

    }

    @Test
    public void cancelDelivery() { // add delivery => cancel it => validate through isOccupied and get history

    }

    @Test
    public void getDeliveryHistory() { // validate history before adding, after adding, after deleting
        ResponseT<String> history = service.getDeliveryHistory();
        assertFalse(history.getErrorOccurred());
        assertFalse(history.getValue().contains("Order:"));

        ResponseT<String> res = service.deliver(facadeSiteNumber(1), facadeSiteNumber(2), "135792468", listOfFacProducts(), facadeDate());
        assertFalse(res.getErrorOccurred());
        String[] included = {"Order: 135792468", "Supplier", "testAddress1", "testContact1", "0123456789",
                                                 "Client", "testAddress2", "testContact2", "9876543210",
                                                ""+listOfFacProducts().get(0).getId(), ""+listOfFacProducts().get(0).getAmount(), ""+listOfFacProducts().get(0).getWeight(),
                                                ""+listOfFacProducts().get(1).getId(), ""+listOfFacProducts().get(1).getAmount(), ""+listOfFacProducts().get(1).getWeight(),
                                                ""+listOfFacProducts().get(2).getId(), ""+listOfFacProducts().get(2).getAmount(), ""+listOfFacProducts().get(2).getWeight()};

        res = service.getDeliveryHistory();
        assertFalse(res.getErrorOccurred());
        for(String s : included){
            assertTrue(res.getValue().contains(s));
        }

        service.cancelDelivery("135792468");
        res = service.getDeliveryHistory();
        assertFalse(res.getErrorOccurred());
        assertFalse(res.getValue().contains("135792468"));
    }

    @Test
    public void showShippingZones() {
        ResponseT<String> res = service.showShippingZones();
        assertFalse(res.getErrorOccurred());
        for (ShippingZone value : ShippingZone.values()) {
            assertTrue(res.getValue().contains(value.name()));
        }
    }

//    public Response addDriver(FacadeDriver facadeDriver);
//
//    public Response addTruck(FacadeTruck facadeTruck);
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

    private FacadeSite facadeSiteNumber(int number){
        return new FacadeSite("Golan", "testAddress" + number, "testContact" + number, "0123456789" + number);
    }

}



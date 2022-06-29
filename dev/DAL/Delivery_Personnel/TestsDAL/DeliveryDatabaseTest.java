//package DAL.Delivery_Personnel.TestsDAL;
//
//import DeliveryModule.Facade.ResponseT;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Test;
//
//public class DeliveryDatabaseTest implements  {

//        Scanner scanner = new Scanner(System.in);
//                DALController dalController = DALController.getInstance();
//                DriverDTO driver1 = new DriverDTO("123", "C", "Negev", "Yosi Ben", "0521234567", "1905220");
//
//                System.out.println("hit enter to add driver");
//                String input = scanner.nextLine();
//
//                dalController.addDriver(driver1);
//
//                System.out.println("hit enter to update driver");
//                input = scanner.nextLine();
//
//                driver1.License = "updated";
//                driver1.Cellphone = "updated";
//                driver1.Name = "updated";
//                driver1.Zone = "updated";
//                driver1.Diary = "updated";
//                dalController.updateDriver(driver1);
//
//                System.out.println("hit enter to remove driver");
//                input = scanner.nextLine();
//
//                dalController.removeDriver(driver1.Id);
//                System.out.println("performed delete");



//
//        @Before
//        public void tearDown(){
//            drService = new DeliveryService("sudo");
//        }
//
//        @Test
//        public void testGetDeliveryHistory(){
//            drService = new DeliveryService();
//            FacadeSite origin = new FacadeSite(ShippingZone.values()[0].toString(), "Rager 120", "Israel Israeli", "0123456789");
//            FacadeSite dest = new FacadeSite(ShippingZone.values()[0].toString(), "Jabotinsky 10", "David Davidi", "9876543210");
//            int orderId = 1100110011;
//            List<FacadeProduct> facProducts = new ArrayList<>();
//            facProducts.add(new FacadeProduct(123, 1000000, 153));
//            FacadeDate facadeDate = new FacadeDate(20, 4, 2022);
//
//            String strResHistory = drService.getDeliveryHistory().getValue();
//            assertFalse(strResHistory.contains(orderId + ""));
//
//            ResponseT<FacadeRecipe> resDelivery = drService.deliver(origin, dest, orderId, facProducts, facadeDate);
//
//            strResHistory = drService.getDeliveryHistory().getValue();
//            assertTrue(strResHistory.contains(orderId + ""));
//
//        }
//
//        @Override
//        public void ExecTest() {
//            testDeliver();
//            testGetDeliveryHistory();
//        }
//    }
//
//
//}

//import SuppliersModule.DomainLayer.Contract;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Objects;
//
//public class ContractTest {
//
//    Contract contract;
//
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        contract = new Contract("10", new boolean[]{false, false, true, false, true, false, false}, 1, -1, "111", "1", 50.5f);
//    }
//
//    @Test
//    public void addSupplyDay() {
//        contract.changeDaysOfDelivery(1, true);
//        try {
//            assert(contract.getDaysOfDelivery()[0]);
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//
//    @Test
//    public void setSupplyMaxDays() {
//        contract.setMaxDeliveryDuration(5);
//        try {
//            assert(contract.getMaxDeliveryDuration() == 5);
//        } catch (Exception e) {
//            assert false;
//        }
//
//        try {
//            contract.setMaxDeliveryDuration(-5);
//            assert false;
//        } catch (Exception e) {
//            assert true;
//        }
//    }
//
//    @Test
//    public void addProduct() {
//        contract.addProduct("5", "2", 9.99f);
//        try {
//            assert(contract.getCatalog().size() == 1);
//        } catch (Exception e) {
//            assert false;
//        }
//
//        try {
//            contract.addProduct("5", "4", 12.99f);
//            assert false;
//        } catch (Exception e) {
//            assert true;
//        }
//    }
//
//    @Test
//    public void updateProductCatalogNum() {
//        contract.addProduct("2", "1", 20.05f);
//        contract.updateCatalogNum("2","5");
//        try {
//            assert(Objects.equals(contract.getCatalog().get(0).getCatalogNum(), "5"));
//        } catch (Exception e) {
//            assert false;
//        }
//    }
//}
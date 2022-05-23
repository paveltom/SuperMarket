import SuppliersModule.DomainLayer.Contract;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ContractTest {

    Contract contract = new Contract(new boolean[]{false, false, true, false, true, false, false}, 3, true);


    @Before
    public void setUp() throws Exception {
        contract = new Contract(new boolean[]{false, false, true, false, true, false, false}, 3, true);
    }

    @Test
    public void addSupplyDay() {
        contract.addSupplyDay(2);
        try {
            assert(contract.getSupplyDays()[1]);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void setSupplyMaxDays() {
        contract.setSupplyMaxDays(5);
        try {
            assert(contract.getSupplyMaxDays() == 5);
        } catch (Exception e) {
            assert false;
        }

        try {
            contract.setSupplyMaxDays(-5);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void addProduct() {
        contract.addProduct("5", "milk", 9.99f);
        try {
            assert(contract.getCatalog().size() == 1);
        } catch (Exception e) {
            assert false;
        }

        try {
            contract.addProduct("5", "sugar", 12.99f);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void updateProductCatalogNum() {
        contract.addProduct("2", "coffee", 20.05f);
        contract.updateProductCatalogNum("2","5");
        try {
            assert(Objects.equals(contract.getCatalog().get(0).getCatalogNum(), "5"));
        } catch (Exception e) {
            assert false;
        }
    }
}
import DomainLayer.Contract;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContractTest {

    Contract contract = new Contract(new boolean[]{false, false, true, false, true, false, false}, 3, true);


    @Before
    public void setUp() throws Exception {
        contract = new Contract(new boolean[]{false, false, true, false, true, false, false}, 3, true);
    }

    @Test
    public void addSupplyDay() {
        contract.addSupplyDay(2);
        assert(contract.getSupplyDays()[1]);
    }

    @Test
    public void setSupplyMaxDays() {
    }

    @Test
    public void addProduct() {
    }

    @Test
    public void updateProductCatalogNum() {
    }
}
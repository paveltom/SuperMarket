import SuppliersModule.DomainLayer.Supplier;
import SuppliersModule.DomainLayer.SupplierController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

public class SupplierControllerTest {

    SupplierController sc = new SupplierController();
    SupplierController sc2 = new SupplierController();

    @Before
    public void setUp() throws Exception {

    }

    public void addSupplierTest(){
        /*sc.addSupplier("18", "milkySupp", "adrress18", "swis12", true, false, "Moshe", "0524837824",
                new boolean[]{false, false, false, false, false, false, false}, 5, 5, false,
                "1", "118", 50);

        Supplier s1 = sc2.getSupplier("18");;
        assertEquals(s1.getContract().getCatalog().get(0).getId(), "1");
        assertEquals(s1.getContract().getCatalog().get(0).getCatalogNum(), "118");
        assertEquals(s1.getContract().getCatalog().get(0).getPrice(), 50.0f);
        assertEquals(s1.getContract().getCatalog().get(0).getsId(), "18");
        assertEquals(s1.getBankAccount(), "swis12");
        assertEquals(s1.getAddress(), "adrress18");
        assertEquals(s1.getContacts().get("Moshe"), "0524837824");
        assertEquals(s1.getSupplyDays(), new boolean[]{false, false, false, false, false, false, false});
        assertEquals(s1.getSupplyCycle(), 5);


    }

    @After
    public void tearDown() throws Exception {
    }
}
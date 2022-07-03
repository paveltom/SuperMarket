package Tests;

import DAL.Delivery_Personnel.DALController;
import DAL.Delivery_Personnel.DataBaseConnection;
import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import StockModule.BusinessLogicLayer.Product;
import StockModule.BusinessLogicLayer.StockController;
import SuppliersModule.DomainLayer.SupplierController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class sup_stock_integrationTests {

    @Before
    public void setUp(){
        //clear db all stock and supplier tables
        DALController.getInstance().deleteDB();

        //make category -> make product -> make items
        DeliveryController.GetInstance();
        StockController st = StockController.getInstance();
        st.insertNewCategory("sababa");
        st.insertNewProduct("sus", "rezah", 400, 5, 0, 10);
        st.insertNewItem("susrezah", "lz12", new Date(2022, 10, 10), false, 20);

        //make supplier with prduct above
        SupplierController sc = SupplierController.getInstance();
        boolean[] workingDays = {true, true, true, false, false, true, false};
        boolean[] orderingDays = {true, true, true, true, false, false, false};
        sc.addSupplier("s1", "name", "address", "bank", true, true, workingDays,
                "contactname", "05264545484", orderingDays, -1,
                "susrezah", "15", 12.5f);
    }

    @After
    public void tearDown(){
        StockController st = StockController.getInstance();
        SupplierController sc = SupplierController.getInstance();
        st.deleteProduct("susrezah");
        sc.removeSupplier("s1");
    }

    @Test
    public void test_shortageOrder(){
        StockController st = StockController.getInstance();
        SupplierController sc = SupplierController.getInstance();
        try {
            st.reduceItemAmount("susrezah", 0, 16);
            boolean ans = sc.getSupplier("s1").getOrders().get(0).hasProduct("susrezah");

            assertTrue(ans);


        } catch (Exception e) {
            assertFalse(e.getMessage(), true);
        }
    }

    @Test
    public void test_periodicOrder(){
        StockController st = StockController.getInstance();
        SupplierController sc = SupplierController.getInstance();
        try {
            st.reduceItemAmount("susrezah", 0, 10);

            sc.endDay();
            assertTrue("msg",sc.getSupplier("s1").getCatalog().stream().filter(catalogProduct -> catalogProduct.getId().
                    equals("susrezah")).findFirst().get() != null);
            boolean ans = sc.getSupplier("s1").getOrders().get(0).hasProduct("susrezah");

            assertTrue(ans);


        } catch (Exception e) {
            assertFalse(e.getMessage(), true);
        }
    }

}

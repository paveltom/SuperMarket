package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.Product;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest implements Testable
{
    Product testObj;

    @Test
    void test_encode()
    {
        testObj = new Product(0, 45632.1123, 216);
        String actual = testObj.Encode();
        String expected = "0$216$45632.112300";
        assertEquals(expected, actual);
    }

    @Test
    void test_decode()
    {
        String encoded = "0$216$45632.112300";
        testObj = new Product(encoded);
        Product expected = new Product(0, 45632.1123, 216);
        assertEquals(testObj, expected);
    }

    @Override
    public void ExecTest()
    {
        test_encode();
        test_decode();
    }

    @Override
    public String toString()
    {
        return "Product";
    }
}

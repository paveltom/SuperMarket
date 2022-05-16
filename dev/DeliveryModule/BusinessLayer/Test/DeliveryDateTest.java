package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.DeliveryDate;
import DeliveryModule.BusinessLayer.Element.Shift;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryDateTest implements Testable
{
    DeliveryDate testObj;

    @Test
    void test_encode()
    {
        testObj = new DeliveryDate(8, 1996, new Shift(4, 0));
        String actual = testObj.Encode();
        System.out.println(actual);
        String expected = "Saturday 8 4 1996$0";
        assertEquals(expected, actual);
    }

    @Test
    void test_decode()
    {
        String encoded = "Saturday 8 4 1996$0";
        testObj = new DeliveryDate(encoded);
        DeliveryDate expected = new DeliveryDate(8, 1996, new Shift(4, 0));
        assertEquals(expected, testObj);
    }

    @Override
    public void ExecTest()
    {
        test_encode();
        test_decode();
    }
}

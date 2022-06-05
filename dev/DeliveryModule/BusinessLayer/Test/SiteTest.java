package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.Site;
import DeliveryModule.BusinessLayer.Type.ShippingZone;
import org.junit.Test;
import static org.junit.Assert.*;

public class SiteTest implements Testable
{
    Site testObj;

    @Test
    void test_encode()
    {
        testObj = new Site(ShippingZone.Negev, "Sderot Rotshield", "Kol-Bo-Shop", "0543332130");
        String actual = testObj.Encode();
        String expected = "Negev~Sderot Rotshield~Kol-Bo-Shop~0543332130";
        assertEquals(expected, actual);
    }

    @Test
    void test_decode()
    {
        String encoded = "Negev~Sderot Rotshield~Kol-Bo-Shop~0543332130";
        Site actual = new Site(encoded);
        Site expected = new Site(ShippingZone.Negev, "Sderot Rotshield", "Kol-Bo-Shop", "0543332130");
        assertEquals(actual, expected);
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
        return "Site";
    }
}

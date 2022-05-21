package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.Date;
import org.junit.Test;
import static org.junit.Assert.*;

class DateTest implements Testable{

    private Date testObject1, testObject2;

    /*
     * init: Create two Date object with same data member.
     * Step: call equals
     * Expect: True
     * */
    @Test
    void testEquals()
    {
        testObject1 = new Date(3, 5, 2033);
        testObject2 = new Date(3, 5, 2033);

        assertEquals(testObject2, testObject1);
    }

    /*
     * init: Create two different Date object.
     * Step: call equals
     * Expect: False
     * */
    @Test
    void testNotEquals()
    {
        testObject1 = new Date(3, 5, 2033);
        testObject2 = new Date(3, 4, 2033);

        assertNotEquals(testObject2, testObject1);
    }

    /*
     * init: Create two different Date object such that testObj1 is later testObj2
     * Step: call compareTo
     * Expect: 1
     * */
    @Test
    void compareTo_later()
    {
        testObject1 = new Date(1, 1, 2035);
        testObject2 = new Date(3, 4, 2033);

        int diff = testObject1.compareTo(testObject2);
        assertEquals(diff, 1);
    }

    /*
     * init: Create two different Date object such that testObj1 is earlier testObj2
     * Step: call compareTo
     * Expect: -1
     * */
    @Test
    void compareTo_earlier()
    {
        testObject1 = new Date(1, 1, 2013);
        testObject2 = new Date(3, 4, 2033);

        int diff = testObject1.compareTo(testObject2);
        assertEquals(diff, -1);
    }

    /*
     * init: Create Date objects
     * Step: call Date.Name
     * Expect: Correct day name for a given date.
     * */
    @Test
    void validateDayName()
    {
        testObject1 = new Date(1, 1, 2015);
        assertEquals(testObject1.Name, "Thursday");

        testObject1 = new Date(1, 1, 2022);
        assertEquals(testObject1.Name, "Saturday");

        testObject1 = new Date(30, 4, 2022);
        assertEquals(testObject1.Name, "Saturday");

        testObject1 = new Date(25, 5, 2022);
        assertEquals(testObject1.Name, "Wednesday");

        testObject1 = new Date(22, 6, 2026);
        assertEquals(testObject1.Name, "Monday");

        testObject1 = new Date(13, 9, 2022);
        assertEquals(testObject1.Name, "Tuesday");

    }

    @Test
    void test_encode()
    {
        testObject1 = new Date(13, 9, 2022);
        String actual = testObject1.Encode();
        String expected = "Tuesday 9 13 2022";
        assertEquals(expected, actual);
    }

    @Test
    void test_decode()
    {
        String encoded = "Tuesday 9 13 2022";
        Date actual = new Date(encoded);
        Date expected = new Date(13, 9, 2022);
        assertEquals(expected, actual);
    }

    @Override
    public void ExecTest()
    {
        testEquals();
        testNotEquals();
        compareTo_later();
        compareTo_earlier();
        validateDayName();
        test_encode();
        test_decode();
    }

    @Override
    public String toString()
    {
        return "Date";
    }
}
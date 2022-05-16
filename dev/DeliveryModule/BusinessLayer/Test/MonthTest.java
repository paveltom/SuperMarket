package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.Month;
import DeliveryModule.BusinessLayer.Element.Shift;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest implements Testable{

    private final int JANUARY = 31, FEBRUARY = 28;
    private Month testObject;


    /*
    * init: Set 3 shipments on same day.
    * Step: call GetAvailableShift.
    * Expect: Shift number 4 (15:00-18:00) of the same day to be returned.
    * */
    @Test
    void getAvailableShift_1()
    {
        testObject = new Month(JANUARY);
        final int shipmentDay = 31;

        testObject.SetOccupied(new Shift(shipmentDay, 0));
        testObject.SetOccupied(new Shift(shipmentDay, 1));
        testObject.SetOccupied(new Shift(shipmentDay, 2));

        var returnedShift = testObject.GetAvailableShift(shipmentDay);
        var expectedShift = new Shift (shipmentDay, 3);

        assertEquals(returnedShift, expectedShift);

    }

    /*
     * init: Set 4 shipments on same day.
     * Step: call GetAvailableShift.
     * Expect: Shift number 0 (06:00-09:00) of the next day to be returned.
     * */
    @Test
    void getAvailableShift_2()
    {
        testObject = new Month(JANUARY);
        final int shipmentDay = 30;

        testObject.SetOccupied(new Shift(shipmentDay, 0));
        testObject.SetOccupied(new Shift(shipmentDay, 1));
        testObject.SetOccupied(new Shift(shipmentDay, 2));
        testObject.SetOccupied(new Shift(shipmentDay, 3));


        var returnedShift = testObject.GetAvailableShift(shipmentDay);
        var expectedShift = new Shift(shipmentDay + 1, 0);

        assertEquals(returnedShift, expectedShift);

    }

    /*
     * init: Set whole month as booked.
     * Step: call GetAvailableShift.
     * Expect: null to be returned.
     * */
    @Test
    void getAvailableShift_3()
    {
        testObject = new Month(JANUARY);
        for(int day = 1; day <= testObject.NumOfDays; day++)
        {
            for(int shift = 0; shift < testObject.ShiftsPerDay; shift++)
                testObject.SetOccupied(new Shift(day, shift));
        }

        var returnedShift = testObject.GetAvailableShift(1);
        Shift expectedShift = null;

        assertEquals(returnedShift, expectedShift);

    }

    /*
     * init: Set new fresh month - without any booking.
     * Step: call GetAvailableShift(someDay)
     * Expect: Shift 0 of someDay to be returned.
     * */
    @Test
    void getAvailableShift_4()
    {
        testObject = new Month(JANUARY);
        final int someDay = 3;
        var returnedShift = testObject.GetAvailableShift(someDay);
        Shift expectedShift = new Shift(someDay, 0);

        assertEquals(returnedShift, expectedShift);

    }

    /*
     * init: Set new fresh month - without any booking.
     * Step: call GetAvailableShift(invalidDay)
     * Expect: null to be returned.
     * */
    @Test
    void getAvailableShift_5()
    {
        testObject = new Month(FEBRUARY);
        final int invalidDay = 30;
       var returnedShift = testObject.GetAvailableShift(invalidDay);
       Shift expectedShift = null;

        assertEquals(returnedShift, expectedShift);

    }

    /*
     * Validate encode
     * */
    @Test
    void test_encode()
    {
        Month month = new Month(31);
        String expected = "31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000";
        String actual = month.Encode();
        assertEquals(expected, actual);
        List<Shift> constraint = new ArrayList<>(Arrays.asList(new Shift(1, 0), new Shift(1, 1), new Shift(1,2), new Shift(1,3), new Shift(5,3), new Shift(29,2)));
        month.SetConstraints(constraint);
        expected = "31@1111#0000#0000#0000#0001#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0010#0000#0000";
        actual = month.Encode();
        assertEquals(expected, actual);
    }

    /*
     * Validate decode
     * */
    @Test
    void test_decode()
    {
        String actual = "31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000";
        Month month = new Month(actual);
        String expected = month.Encode();
        assertEquals(expected, actual);
        actual = "31@0000#0000#0110#0000#0000#0000#0101#1000#0000#1111#0000#0000#0110#0000#0000#1001#0000#0001#1111#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000";
        month = new Month(actual);
        expected = month.Encode();
        assertEquals(expected, actual);
        assertEquals(new Month(30), new Month(30));
        assertNotEquals(new Month(28), new Month(30));
    }

    @Override
    public void ExecTest()
    {
        getAvailableShift_1();
        getAvailableShift_2();
        getAvailableShift_3();
        getAvailableShift_4();
        getAvailableShift_5();
        test_encode();
        test_decode();
    }

    @Override
    public String toString()
    {
        return "Month";
    }
}
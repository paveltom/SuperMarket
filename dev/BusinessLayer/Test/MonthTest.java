package BusinessLayer.Test;

import BusinessLayer.Element.Month;
import org.junit.jupiter.api.Test;

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

        testObject.setOccupied(shipmentDay, 0);
        testObject.setOccupied(shipmentDay, 1);
        testObject.setOccupied(shipmentDay, 2);

        int[] returnedShift = testObject.GetAvailableShift(shipmentDay);
        int[] expectedShift = {shipmentDay, 3};

        assertArrayEquals(returnedShift, expectedShift);

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

        testObject.setOccupied(shipmentDay, 0);
        testObject.setOccupied(shipmentDay, 1);
        testObject.setOccupied(shipmentDay, 2);
        testObject.setOccupied(shipmentDay, 3);


        int[] returnedShift = testObject.GetAvailableShift(shipmentDay);
        int[] expectedShift = {shipmentDay + 1, 0};

        assertArrayEquals(returnedShift, expectedShift);

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
                testObject.setOccupied(day, shift);
        }

        int[] returnedShift = testObject.GetAvailableShift(1);
        int[] expectedShift = null;

        assertArrayEquals(returnedShift, expectedShift);

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
        int[] returnedShift = testObject.GetAvailableShift(someDay);
        int[] expectedShift = {someDay, 0};

        assertArrayEquals(returnedShift, expectedShift);

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
        int[] returnedShift = testObject.GetAvailableShift(invalidDay);
        int[] expectedShift = null;

        assertArrayEquals(returnedShift, expectedShift);

    }

    @Override
    public void ExecTest()
    {
        getAvailableShift_1();
        getAvailableShift_2();
        getAvailableShift_3();
        getAvailableShift_4();
        getAvailableShift_5();
    }

    @Override
    public String toString()
    {
        return "Month";
    }
}
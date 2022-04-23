package BusinessLayer.Test;

import BusinessLayer.Element.DeliveryDate;
import BusinessLayer.Element.Scheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest implements Testable{

    private Scheduler testObject = new Scheduler();


    /*
     * init: Order numOfDeliveries at selectedMonth on selectedDay
     * Step: call GetAvailableShift
     * Expect: Correct shift to be returned assuming 4 shifts per day.
     * */
    @Test
    void getAvailableShift_1()
    {
        final int numOfDeliveries = 13, selectedMonth = 3, selectedDay = 25;
        for(int i = 0; i < numOfDeliveries; i++)
            testObject.GetAvailableShift(selectedMonth, selectedDay);

        var returnedShift = testObject.GetAvailableShift(selectedMonth, selectedDay);
        var expectedShift = new DeliveryDate(28, selectedMonth, 2022, 1);

        assertEquals(expectedShift, returnedShift);
    }

    /*
     * init: Order numOfDeliveries impose provide deliveries at successive month.
     * Step: call GetAvailableShift
     * Expect: Correct shift to be returned assuming 4 shifts per day.
     * */
    @Test
    void getAvailableShift_2()
    {
        final int numOfDeliveries = 17, selectedMonth = 2, selectedDay = 26, successiveMonth = 3;
        for(int i = 0; i < numOfDeliveries; i++)
            testObject.GetAvailableShift(selectedMonth, selectedDay);

        var returnedShift = testObject.GetAvailableShift(selectedMonth, selectedDay);
        var expectedShift = new DeliveryDate(2, successiveMonth, 2022, 1);

        assertEquals(expectedShift, returnedShift);
    }

    /*
     * init: Order numOfDeliveries exceeds delivery capacity for whole year.
     * Step: call GetAvailableShift
     * Expect: null - No available delivery
     * */
    @Test
    void getAvailableShift_3()
    {
        final int numOfMonths = 12, numOfDays = 31, numOfShifts = 4;
        for(int i = 1; i <= numOfMonths; i++)
        {
            for(int j = 1; j <= numOfDays; j++)
            {
                for(int k = 0; k < numOfShifts; k++)
                    testObject.GetAvailableShift(i, j);
            }
        }

        /* Search for any available day since the beginning of the year */
        var returnedShift = testObject.GetAvailableShift(1, 1);
        DeliveryDate expectedShift = null;

        assertEquals(expectedShift, returnedShift);
    }

    @Override
    public void ExecTest()
    {
        getAvailableShift_1();
        getAvailableShift_2();
        getAvailableShift_3();
    }

    @Override
    public String toString()
    {
        return "Scheduler";
    }
}
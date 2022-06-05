package DeliveryModule.BusinessLayer.Test;

import DeliveryModule.BusinessLayer.Element.DeliveryDate;
import DeliveryModule.BusinessLayer.Element.Scheduler;
import DeliveryModule.BusinessLayer.Element.Shift;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

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

        final boolean[] SupplierWorkingDays = new boolean[7];
        Arrays.fill(SupplierWorkingDays, true);

        for(int i = 0; i < numOfDeliveries; i++)
            testObject.GetAvailableDeliveryDate(selectedMonth, selectedDay, SupplierWorkingDays);

        DeliveryDate returnedShift = testObject.GetAvailableDeliveryDate(selectedMonth, selectedDay, SupplierWorkingDays);
        DeliveryDate expectedShift = new DeliveryDate(selectedMonth, 2022, new Shift(28, 1));

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

        final boolean[] SupplierWorkingDays = new boolean[7];
        Arrays.fill(SupplierWorkingDays, true);

        for(int i = 0; i < numOfDeliveries; i++)
            testObject.GetAvailableDeliveryDate(selectedMonth, selectedDay, SupplierWorkingDays);

        DeliveryDate returnedShift = testObject.GetAvailableDeliveryDate(selectedMonth, selectedDay, SupplierWorkingDays);
        DeliveryDate expectedShift = new DeliveryDate(successiveMonth, 2022, new Shift(2, 1));

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

        final boolean[] SupplierWorkingDays = new boolean[7];
        Arrays.fill(SupplierWorkingDays, true);

        for(int i = 1; i <= numOfMonths; i++)
        {
            for(int j = 1; j <= numOfDays; j++)
            {
                for(int k = 0; k < numOfShifts; k++)
                    testObject.GetAvailableDeliveryDate(i, j, SupplierWorkingDays);
            }
        }

        /* Search for any available day since the beginning of the year */
        DeliveryDate returnedShift = testObject.GetAvailableDeliveryDate(1, 1, SupplierWorkingDays);
        DeliveryDate expectedShift = null;

        assertEquals(expectedShift, returnedShift);
    }

    @Test
    void test_decode()
    {
        testObject = new Scheduler();
        Scheduler expected = new Scheduler("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000");
        assertEquals(testObject, expected);
    }

    @Test
    void test_encode()
    {
        testObject = new Scheduler();
        Scheduler expected = new Scheduler("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000");
        assertEquals(testObject, expected);
    }
    @Override
    public void ExecTest()
    {
        getAvailableShift_1();
        getAvailableShift_2();
        getAvailableShift_3();
        test_decode();
        test_encode();
    }

    @Override
    public String toString()
    {
        return "Scheduler";
    }
}
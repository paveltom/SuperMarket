package DeliveryModule.BusinessLayer.Test;


public class TestsExecutor {
    public static void main(String[] args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------ Tests start ------------------------------\n");
        Testable[] tests = {new DateTest(), new DeliveryControllerTest(), new DeliveryResourceControllerTest(), new MonthTest(), new SchedulerTest(), new SiteTest(), new ProductTest(), new DeliveryDateTest()};
        for(Testable test : tests)
        {
            sb.append(String.format("Check unit: %s\n", test));
            test.ExecTest();
            /* If one of the test applied in ExecTest() is failed, the process will terminate. */
            sb.append(String.format("Unit: %s pass all tests\n", test));
        }
        sb.append("------------------------------ Tests end ------------------------------\n");
        System.out.println(sb);
    }
}

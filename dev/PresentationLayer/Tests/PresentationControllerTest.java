package PresentationLayer.Tests;

import org.testng.annotations.Test;

public class PresentationControllerTest {

    @Test
    public void givenRadius_whenCalculateArea_thenReturnArea() {
        double actualArea = Circle.calculateArea(1d);
        double expectedArea = 3.141592653589793;
        Assert.assertEquals(expectedArea, actualArea);
    }

    // addDriver success: add 2 drivers whilst hitting 'cancel' after first
    //    "    fail: enter bad zone; enter bad vehicle category;
    // addTruck success / fail
    // getHistory empty / several deliveries
    // new delivery success / fail
    //


}

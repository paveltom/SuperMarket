package PresentationLayer.Tests;

import BusinessLayer.Test.Testable;
import PresentationLayer.PresentationController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PresentationControllerTest implements Testable {

    @Test
    public void testDeliver(PresentationController pcState) {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        pcState.run();
        System.setIn(in);
        //System.setIn(in);
        //System.setIn(sysInBackup);

    }

    @Override
    public void ExecTest() {
        PresentationController pcState = new PresentationController();
        testDeliver(pcState);
    }

}

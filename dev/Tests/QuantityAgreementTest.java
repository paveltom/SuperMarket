import SuppliersModule.DomainLayer.QuantityAgreement;
import org.junit.Before;
import org.junit.Test;

public class QuantityAgreementTest {

    QuantityAgreement q;

    @Before
    public void Before(){
        q = new QuantityAgreement();
    }

    @Test
    public void addDiscount() {
        q.updateDiscount("1", 5, 10);
        try{
            assert (q.getDiscount("1", 5) == 10);
        } catch (Exception e) {
            assert false;
        }
        try{
            q.updateDiscount("1", 5, 0);
            assert(q.getDiscount("1", 5) == 0) ;
        } catch (Exception e) {
            assert true;
        }
        try{
            q.updateDiscount("1", 0, 10);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void removeDiscount() {
        q.updateDiscount("2", 20, 50);
        try {
            q.updateDiscount("2", 20, 0);
            assert (q.getDiscount("2", 20) == 0);
        } catch (Exception e) {
            assert (e.getMessage().equals("element ot found"));
        }
    }
}
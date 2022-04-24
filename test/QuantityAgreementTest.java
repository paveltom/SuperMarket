import DomainLayer.QuantityAgreement;
import org.junit.Before;
import org.junit.Test;

public class QuantityAgreementTest {

    QuantityAgreement q;

    @Before
    public void Before(){
        q = new QuantityAgreement();
    }

    @Test
    public void addDiscountPerItemTest() {
        q.addDiscountPerItem("1", 5, 10);
        try{
            assert (q.getDiscountsForProductPerItem("1").get(5) == 10);
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void addDiscountPerOrderTest() {
        q.addDiscountPerOrder("1", 5, 10);

        try {
            assert (q.getDiscountsForProductPerOrder("1").get(5) == 10);
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void updateDiscountPerItemTest() {
        q.addDiscountPerItem("1", 5, 10);
        try{
            q.updateDiscountPerItem("1", 5, 5);
            assert (q.getDiscountsForProductPerItem("1").get(5) == 5);
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void removeDiscountPerItem() {
        q.addDiscountPerItem("1", 5, 10);
        try {
            q.removeDiscountPerItem("1", 5);
            assert (q.getDiscountsForProductPerItem("1").get(5)==null);
        } catch (Exception e) {
            assert (e.getMessage().equals("element ot found"));
        }
    }

    @Test
    public void updateDiscountPerOrder() {
        q.addDiscountPerOrder("1", 5, 10);
        try{
            q.updateDiscountPerOrder("1", 5, 5);
            assert (q.getDiscountsForProductPerOrder("1").get(5) == 5);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removeDiscountPerOrder() {
        q.addDiscountPerOrder("1", 5, 10);
        try {
            q.removeDiscountPerOrder("1", 5);
            assert (q.getDiscountsForProductPerOrder("1").get(5)==null);
        } catch (Exception e) {
            assert (e.getMessage().equals("element ot found"));
        }
    }

}
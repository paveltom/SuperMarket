package SuppliersModule.DomainLayer;

import java.util.Dictionary;
import java.util.Hashtable;

public class QuantityAgreement {
    private final Dictionary<String, Dictionary<Integer, Float>> Discounts = new Hashtable<>();
    public Dictionary<String, Dictionary<Integer, Float>> getDiscounts() {
        return Discounts;
    }
    public void updateDiscount(String pId, int quantity, float discount){
        validParams(quantity, discount);
        if (discount == 0 && hasInDiscounts(pId)){
            Discounts.get(pId).remove(quantity);
            if(Discounts.get(pId).isEmpty()){
                Discounts.remove(pId);
            }
        }
        else if (discount > 0) {
            if (!hasInDiscounts(pId))
                Discounts.put(pId, new Hashtable<>());
            Discounts.get(pId).put(quantity, discount);
        }
    }
    public Dictionary<Integer,Float> getDiscounts(String pId){
        return Discounts.get(pId);
    }
    public void removeProduct(String pId){
        Discounts.remove(pId);
    }
    private boolean hasInDiscounts(String pId){
        return Discounts.get(pId) != null;
    }
    private void validParams(int q, float d){
        validQuantity(q);
        validDiscount(d);
    }
    private void validQuantity(int q) {
        if (q <= 0)
            throw new IllegalArgumentException("quantity must be greater than 0");
    }
    private void validDiscount(float d) {
        if (d < 0)
            throw new IllegalArgumentException("discount can not be negative");
    }
}

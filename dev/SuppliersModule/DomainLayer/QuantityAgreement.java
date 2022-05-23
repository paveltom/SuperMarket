package com.company.SuppliersModule.DomainLayer;

import java.util.Hashtable;
import java.util.Map;

public class QuantityAgreement {
    private final Map<String, Map<Integer, Float>> Discounts = new Hashtable<>();
    public Map<String, Map<Integer, Float>> getDiscounts() {
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
    public Map<Integer, Float> getDiscounts(String pId){
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
        if (d < 0 | d >= 100)
            throw new IllegalArgumentException("discount must be in % format between 0 to 100-");
    }

    public float getDiscount(String pId, int amount) {
        Map<Integer, Float> disc = Discounts.get(pId);
        if(disc == null) {
            return 0;
        }
        else{
            int maxDiscKey = -1;
            for (Integer lvl : disc.keySet()){
                if(maxDiscKey < lvl & lvl <= amount){
                    maxDiscKey = lvl;
                }
            }
            return disc.get(maxDiscKey);
        }
    }

}

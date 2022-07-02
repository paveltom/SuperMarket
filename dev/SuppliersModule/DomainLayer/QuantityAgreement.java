package SuppliersModule.DomainLayer;

import DAL.Stock_Suppliers.DAOS.SupplierObjects.QuantityAgreementDao;
import java.util.Hashtable;
import java.util.Map;

public class QuantityAgreement {
    private final QuantityAgreementDao dao;
    private String sId;
    private final Map<String, Map<Integer, Float>> Discounts = new Hashtable<>();
    public Map<String, Map<Integer, Float>> getDiscounts() {
        return Discounts;
    }

    public QuantityAgreement(String sId){
        this.sId = sId;
        dao = new QuantityAgreementDao();
    }
    public void updateDiscount(String pId, int quantity, float discount){
        validParams(quantity, discount);
        if (discount == 0 && hasInDiscounts(pId)){
            Discounts.get(pId).remove(quantity);
            dao.removeDiscount(sId, pId, quantity);
            if(Discounts.get(pId).isEmpty()){
                Discounts.remove(pId);
            }
        }
        else if (discount > 0) {
            if (!hasInDiscounts(pId)) {
                Discounts.put(pId, new Hashtable<>());
                dao.addDiscount(sId, pId, quantity, discount);
                Discounts.get(pId).put(quantity, discount);
            }
            else {
                Discounts.get(pId).put(quantity, discount);
                dao.updateDiscount(sId, pId, quantity, discount);
            }
        }
    }
    public void loadDiscountFromDB(String pId, int quantity, float discount){
        if (!hasInDiscounts(pId)) {
            Discounts.put(pId, new Hashtable<>());
            Discounts.get(pId).put(quantity, discount);
        }
        else {
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

    public String toString() {
        return Discounts.toString();
    }
}

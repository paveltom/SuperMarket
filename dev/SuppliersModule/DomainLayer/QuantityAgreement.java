package SuppliersModule.DomainLayer;

import java.util.Dictionary;
import java.util.Hashtable;

public class QuantityAgreement {
    private Dictionary<String, Dictionary<Integer, Float>> perItem;
    private Dictionary<String, Dictionary<Integer, Float>> perOrder;

    public QuantityAgreement(){
        perItem = new Hashtable<>();
        perOrder = new Hashtable<>();
    }

    public void addDiscountPerItem(String productID, int quantity, float discount){
        validParams(quantity, discount);
        if(!hasItemInperItem(productID)){
            createItemDictInperItem(productID);
        }

        perItem.get(productID).put(quantity, discount);
    }

    private void createItemDictInperItem(String productID){
        perItem.put(productID, new Hashtable<>());
    }

    private boolean hasItemInperItem(String productID){
        return perItem.get(productID) != null;
    }


    public void addDiscountPerOrder(String productID, int quantity, float discount){
        if(!hasItemInperOrder(productID)){
            createItemDictInperOrder(productID);
        }

        perOrder.get(productID).put(quantity, discount);
    }

    private void createItemDictInperOrder(String productID){
        perOrder.put(productID, new Hashtable<>());
    }

    private boolean hasItemInperOrder(String productID){
        return perOrder.get(productID) != null;
    }


    public void updateDiscountPerItem(String productID, int quantity, float discount){
        hasItemInperItem(productID);

        removeDiscountPerItem(productID, quantity);
        addDiscountPerItem(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String productID, int quantity){
        if(!hasItemInperItem(productID))
            throw new IllegalArgumentException("element ot found");

        perItem.get(productID).remove(quantity);
    }


    public void updateDiscountPerOrder(String productID, int quantity, float discount){
        if(!hasItemInperOrder(productID))
            throw new IllegalArgumentException("element ot found");

        removeDiscountPerOrder(productID, quantity);
        addDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerOrder(String productID, int quantity){
        if(!hasItemInperOrder(productID))
            throw new IllegalArgumentException("element ot found");

        perOrder.get(productID).remove(quantity);
    }


    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String productID){
        if(!hasItemInperItem(productID))
            throw new IllegalArgumentException("element ot found");

        return perItem.get(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String productID){
        if(!hasItemInperOrder(productID))
            throw new IllegalArgumentException("element ot found");
        return perOrder.get(productID);
    }

    private void validParams(int q, float d){
        validQuantity(q);
        validDiscount(d);
    }

    private void validQuantity(int q) {
        if (q <= 0){
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
    }

    private void validDiscount(float d) {
        if (d <= 0){
            throw new IllegalArgumentException("discount must be greater than 0");
        }
    }

    public void removeProduct(String productID){
        if(hasItemInperItem(productID))
            perItem.remove(productID);
        if(hasItemInperOrder(productID))
            perOrder.remove(productID);
    }

    public void updateProductCatalogNum(String old, String newNum){
        if(hasItemInperItem(old))
            perItem.put(newNum, perItem.get(old));
        if(hasItemInperOrder(old))
            perOrder.put(newNum, perOrder.get(old));

        removeProduct(old);
    }

    public Dictionary<String, Dictionary<Integer, Float>> getPerItem() {
        return perItem;
    }

    public Dictionary<String, Dictionary<Integer, Float>> getPerOrder() {
        return perOrder;
    }
}

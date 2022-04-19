package DomainLayer;

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
//        if(!hasItemInperItem(productID))
//            throw new Exception("element ot found");

        removeDiscountPerItem(productID, quantity, discount);
        addDiscountPerItem(productID, quantity, discount);
    }

    public void removeDiscountPerItem(String productID, int quantity, float discount){
//        if(!hasItemInperItem(productID))
//            throw new Exception("element ot found");

        perItem.get(productID).remove(quantity);
    }


    public void updateDiscountPerOrder(String productID, int quantity, float discount){
//        if(!hasItemInperItem(productID))
//            throw new Exception("element ot found");

        removeDiscountPerOrder(productID, quantity, discount);
        addDiscountPerOrder(productID, quantity, discount);
    }

    public void removeDiscountPerOrder(String productID, int quantity, float discount){
//        if(!hasItemInperItem(productID))
//            throw new Exception("element ot found");

        perOrder.get(productID).remove(quantity);
    }


    public Dictionary<Integer,Float> getDiscountsForProductPerItem(String productID){
        return perItem.get(productID);
    }

    public Dictionary<Integer,Float> getDiscountsForProductPerOrder(String productID){
        return perOrder.get(productID);
    }
}

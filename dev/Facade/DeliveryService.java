package com.company.Facade;

import BusinessLayer.DataObject.DeliveryRecipe;
import BusinessLayer.DataObject.DeliveryOrder;
import BusinessLayer.Controller.DeliveryExecutorController;
import java.util.Date;
import java.util.List;


public class DeliveryService {
    private DeliveryExecutorController delExecController;

    public DeliveryService(){
        delExecController = new DeliveryExecutorController();
    }


    //orderParams: siteId, clientId, orderId, products<productId, quantity>, submissionDate
    public ResponseT<DeliveryRecipe> deliver(String[] orderParams){
        //create deliveryOrder object
        DeliveryOrder delOrder = new DeliveryOrder(orderParams[0], orderParams[1], orderParams[2],
                                                                        orderParams[3], orderParams[4]);
        ResponseT<DeliveryRecipe> res = new ResponseT<>(delExecController.deliver(delOrder));
        return res;
    }

    public ResponseT<List<String>> getDeliveryHistoryBySupplierId(String SupplierId){
        ResponseT<List<String>> res = new ResponseT<>(delExecController.getDeliveryHistoryBySupplierId(SupplierId));
        return res;
    }

    public ResponseT<List<String>> getDeliveryHistoryByDate(Date deliveryDate){
        ResponseT<List<String>> res = new ResponseT<>(delExecController.getDeliveryHistoryByDate(SupplierId));
        return res;
    }

    public ResponseT<List<String>> getDeliveryHistoryByZone(String zone){
        ResponseT<List<String>> res = new ResponseT<>(delExecController.getDeliveryHistoryByZone(SupplierId));
        return res;
    }

    public ResponseT<List<String>> getDeliveryHistory(){
        ResponseT<List<String>> res = new ResponseT<>(delExecController.getDeliveryHistoryByZone(SupplierId));
        return res;
    }

}

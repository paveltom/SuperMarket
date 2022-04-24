package Facade;

import BusinessLayer.Element.*;
import BusinessLayer.Type.*;
import BusinessLayer.Controller.DeliveryController;
import Facade.FacadeObjects.*;

import java.util.ArrayList;
import java.util.List;


public class DeliveryService {
    private DeliveryController delController;

    public DeliveryService(){
        delController = DeliveryController.GetInstance();
    }

    public DeliveryService(String code){
        if(code.equals("sudo"))
            delController = DeliveryController.newInstanceForTests("sudo");
        else
            delController = DeliveryController.GetInstance();
    }


    //orderParams: siteId, clientId, orderId, products<productId, quantity>, submissionDate
    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        try {

            List<Product> products = new ArrayList<>();
            for (FacadeProduct curr : facProducts) {
                Product temp = new Product(curr.getId(), curr.getWeight(), curr.getAmount());
                products.add(temp);
            }

            Date delSubmissionDate = new Date(facSubDate.getDay(), facSubDate.getMonth(), facSubDate.getYear());
            ShippingZone zone = ShippingZone.valueOf(origin.getZone());
            Site supplier = new Site(zone, origin.getAddress(), origin.getContactName(), origin.getCellphone());
            Site client = new Site(ShippingZone.valueOf(destination.getZone()), destination.getAddress(), destination.getContactName(), destination.getCellphone());
            DeliveryOrder delOrder = new DeliveryOrder(supplier, client, orderId, products, delSubmissionDate, zone);
            DeliveryRecipe delRec = delController.Deliver(delOrder);
            FacadeDriver facadeDriver = new FacadeDriver(delRec.DeliveryPerson);
            FacadeTruck facadeTruck = new FacadeTruck(delRec.DeliveryTruck);
            FacadeDate facadeDate = new FacadeDate(delRec.DueDate);
            FacadeRecipe facadeRecipe = new FacadeRecipe(delRec.OrderId, delRec.DeliveryId, delRec.IsPartitioned, facadeDate, facadeDriver, facadeTruck, delRec.UnDeliveredProducts);
            return new ResponseT<>(facadeRecipe, true);
        }
        catch(Exception e) {
           return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<String> getDeliveryHistory(){
        try {
            return new ResponseT<>(delController.GetDeliveriesHistory(), true);
        }
        catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }

//    public ResponseT<List<String>> getDeliveryHistoryBySupplierId(String SupplierId){
//        ResponseT<List<String>> res = new ResponseT<>(delController.GetDeliveryHistoryBySupplierId(SupplierId));
//        return res;
//    }
//
//    public ResponseT<List<String>> getDeliveryHistoryByDate(Date deliveryDate){
//        ResponseT<List<String>> res = new ResponseT<>(delController.getDeliveryHistoryByDate(SupplierId));
//        return res;
//    }
//
//    public ResponseT<List<String>> getDeliveryHistoryByZone(String zone){
//        ResponseT<List<String>> res = new ResponseT<>(delController.getDeliveryHistoryByZone(SupplierId));
//        return res;
//    }

}

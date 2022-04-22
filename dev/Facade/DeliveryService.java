package Facade;

import BusinessLayer.DataObject.*;
import BusinessLayer.Types.*;
import BusinessLayer.Controller.DeliveryController;
import Facade.FacadeObjects.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class DeliveryService {
    private DeliveryController delController;

    public DeliveryService(){
        delController = DeliveryController.GetInstance();
    }


    //orderParams: siteId, clientId, orderId, products<productId, quantity>, submissionDate
    public Response deliver(FacadeSite origin, FacadeSite destination, int orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
        try {

            List<Product> products = new ArrayList<>();
            for (FacadeProduct curr : facProducts) {
                Product temp = new Product(curr.id, curr.weight, curr.amount);
                products.add(temp);
            }

            Date delSubmissionDate = new Date(facSubDate.day, facSubDate.month, facSubDate.year);
            ShippingZone zone = ShippingZone.valueOf(origin.zone);
            Site supplier = new Site(zone, origin.address, origin.name, origin.cellphone);
            Site client = new Site(ShippingZone.valueOf(destination.zone), destination.address, destination.name, destination.cellphone);
            DeliveryOrder delOrder = new DeliveryOrder(supplier, client, orderId, products, delSubmissionDate, zone);
            ResponseT<DeliveryRecipe> res = new ResponseT<>(delController.Deliver(delOrder));
            return res;
        }
        catch(Exception e) {
           return new Response(e.getMessage());
        }
    }

    public Response getDeliveryHistory(){
        try {
            ResponseT<String> res = new ResponseT<>(delController.GetDeliveriesHistory());
            return res;
        }
        catch (Exception e){
            return new Response(e.getMessage());
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

package DeliveryModule.Facade;

import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.*;
import DeliveryModule.Facade.FacadeObjects.*;

import java.util.ArrayList;
import java.util.List;


public class DeliveryService {
    private DeliveryController delController;

    public DeliveryService(){
        delController = DeliveryController.GetInstance();
    }

    public DeliveryController tearDownDelControllerSingletone(String code, DeliveryController delc){
        if(delc != null)
            delController = delc;
        else {
            if (code.equals("sudo"))
                delController = DeliveryController.newInstanceForTests("sudo");
            else
                delController = DeliveryController.GetInstance();
        }
        return delController;
    }


    //orderParams: siteId, clientId, ordFSerId, products<productId, quantity>, submissionDate
    public ResponseT<String> deliver(FacadeSite origin, FacadeSite destination, String orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
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
            DeliveryOrder delOrder = new DeliveryOrder(supplier, client, orderId, products, delSubmissionDate);
            Recipe delRec = delController.Deliver(delOrder);
            return (delRec.Status==RetCode.SuccessfulDelivery) ? new ResponseT<>(delRec.toString(), true) : new ResponseT<>(RetCode.GetRetCodeName(delRec.Status));
        }
        catch(Exception e) {
           return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<Receipt> deliver(FacadeSite origin,
                                     FacadeSite destination,
                                     String orderId,
                                     List<FacadeProduct> facProducts,
                                     FacadeDate facSubDate,
                                     boolean[] supplierWorkingDays)
    {
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
            DeliveryOrder delOrder = new DeliveryOrder(supplier, client, orderId, products, delSubmissionDate, supplierWorkingDays);
            Recipe delRec = delController.Deliver(delOrder);
            return (delRec.Status==RetCode.SuccessfulDelivery) ? new ResponseT<>(delRec, true) : new ResponseT<>(delRec, false);
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

    public ResponseT<String> cancelDelivery(String deliveryId){
        try {
            delController.CancelDelivery(deliveryId);
            return new ResponseT("", true);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<String> showShippingZones(){
        return new ResponseT<>(delController.ShowShippingZone(), true);
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

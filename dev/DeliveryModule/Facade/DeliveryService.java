package DeliveryModule.Facade;

import DeliveryModule.BusinessLayer.Element.*;
import DeliveryModule.BusinessLayer.Type.*;
import DeliveryModule.BusinessLayer.Controller.DeliveryController;
import DeliveryModule.Facade.FacadeObjects.*;

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


    //orderParams: siteId, clientId, ordFSerId, products<productId, quantity>, submissionDate
    public ResponseT<FacadeRecipe> deliver(FacadeSite origin, FacadeSite destination, String orderId, List<FacadeProduct> facProducts, FacadeDate facSubDate){
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
            DeliveryOrder delOrder = new DeliveryOrder(supplier, client, Integer.parseUnsignedInt(orderId), products, delSubmissionDate);
            Recipe delRec = delController.Deliver(delOrder);
            if(delRec instanceof DeliveryRecipe) {
                FacadeDriver facadeDriver = new FacadeDriver(((DeliveryRecipe) delRec).DriverId, ((DeliveryRecipe) delRec).DriverName, "", "", ((DeliveryRecipe) delRec).DriverCellphone);
                FacadeTruck facadeTruck = new FacadeTruck(((DeliveryRecipe) delRec).TruckLicenseNumber, "", "", 0.0, 0.0);
                FacadeDate facadeDate = new FacadeDate(((DeliveryRecipe) delRec).DueDate);
                List<FacadeProduct> delProducts = new ArrayList<>();
                for(Product prod : ((DeliveryRecipe) delRec).DeliveredProducts)
                    delProducts.add(new FacadeProduct(prod.Id, prod.Amount, prod.WeightPerUnit));
                FacadeRecipe facadeRecipe = new FacadeRecipe(((DeliveryRecipe) delRec).OrderId, ((DeliveryRecipe) delRec).DeliveryId, false, facadeDate, facadeDriver, facadeTruck, delProducts);
                return new ResponseT<>(facadeRecipe, true);
            }
            if(delRec instanceof ExceedsMaxLoadWeight) return new ResponseT<>(new FacadeRecipe(((ExceedsMaxLoadWeight) delRec).OrderId + "exceeds max load weight."), false);
            if(delRec instanceof NoAvailableDriver) return new ResponseT<>(new FacadeRecipe(((NoAvailableDriver) delRec).OrderId + "- no available driver."), false);
            else return new ResponseT<>(new FacadeRecipe(((NoAvailableTruck) delRec).OrderId + "- no available truck."), false);
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

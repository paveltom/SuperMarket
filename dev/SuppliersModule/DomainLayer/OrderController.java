package SuppliersModule.DomainLayer;

import StockModule.BusinessLogicLayer.StockController;
import SuppliersModule.DomainLayer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController {
    private static OrderController oc = null;
    private StockController stc;
    private SupplierController supc;
    private OrderController(){}

    public static OrderController getInstance(){
        if(oc == null)
            oc = new OrderController();
        return oc;
    }

    public void registerStock(StockController stc){
        this.stc = stc;
    }

    public void registerSuppliers(SupplierController supc){
        this.supc = supc;
    }

    public Map<String, Integer> orderPeriodic(List<String> products, int daysToOrder){
        Map<String, Integer> suppQuantities = new HashMap<>();
        for (String pId: products) {
            suppQuantities.put(pId, stc.getQuantityForOrder(pId, daysToOrder));
        }
        return suppQuantities;
    }

    public void orderShortage(String pId){
        Map<Supplier, Integer> suppDays = supc.getSuppliersDays(pId); //return amount of days until next supply for each supplier
        Map<Supplier, Integer> suppQuantities = new HashMap<>();
        for (Map.Entry<Supplier, Integer> entry : suppDays.entrySet()) {
            int quantity = stc.getQuantityForOrder(pId, entry.getValue());
            suppQuantities.put(entry.getKey(), quantity);
        }
        supc.orderShortage(suppQuantities, pId);
    }


}

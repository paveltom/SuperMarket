package DeliveryModule.Facade.FacadeObjects;

import java.util.Map;

public class FacadeRecipe {

    private final int OrderId;
    private final int DeliveryId;
    private final boolean IsPartitioned;
    private final Map<Integer, Integer> UnDeliveredProducts;
    private final FacadeDate DueDate;
    private final FacadeDriver DeliveryPerson;
    private final FacadeTruck DeliveryTruck;

    public FacadeRecipe(int supplierOrderId, int deliveryId, boolean isPartitioned, FacadeDate dueDate, FacadeDriver deliveryPerson, FacadeTruck deliveryTruck, Map<Integer, Integer> unDeliveredProduct) {
        OrderId = supplierOrderId;
        DeliveryId = deliveryId;
        IsPartitioned = isPartitioned;
        DueDate = dueDate;
        DeliveryPerson = deliveryPerson;
        DeliveryTruck = deliveryTruck;
        UnDeliveredProducts = unDeliveredProduct;
    }

    public int getOrderId() {
        return OrderId;
    }

    public int getDeliveryId() {
        return DeliveryId;
    }

    public boolean isPartitioned() {
        return IsPartitioned;
    }

    public Map<Integer, Integer> getUnDeliveredProducts() {
        return UnDeliveredProducts;
    }

    public FacadeDate getDueDate() {
        return DueDate;
    }

    public FacadeDriver getDeliveryPerson() {
        return DeliveryPerson;
    }

    public FacadeTruck getDeliveryTruck() {
        return DeliveryTruck;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Delivery Recipe ----------\n");
        sb.append(String.format("Delivery: %d\nOrder: %d\nDue Date: %s\n\n%s\n%s\n", DeliveryId, OrderId, DueDate, DeliveryPerson, DeliveryTruck));
        if (IsPartitioned) {
            sb.append("\nMissing products: show missing per product id\n");
            for (var entry : UnDeliveredProducts.entrySet())
                sb.append(String.format("\nProduct: %d\nMissing amount: %d\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }


}

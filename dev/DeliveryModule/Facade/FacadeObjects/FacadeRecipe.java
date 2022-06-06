package DeliveryModule.Facade.FacadeObjects;

import java.util.List;

public class FacadeRecipe {

    private final int OrderId;
    private final int DeliveryId;
    private final boolean IsUndelivered;
    private final List<FacadeProduct> deliveredProducts;
    private final FacadeDate DueDate;
    private final FacadeDriver DeliveryPerson;
    private final FacadeTruck DeliveryTruck;

    private String errorMsg = null;

    public FacadeRecipe(String errMsg){
        this.errorMsg = errMsg;
        this.deliveredProducts = null;
        this.DeliveryId = 0;
        this.DeliveryTruck = null;
        this.DeliveryPerson = null;
        this.DueDate = null;
        this.IsUndelivered = true;
        this.OrderId = 0;
    }

    public FacadeRecipe(int supplierOrderId, int deliveryId, boolean isUndelivered, FacadeDate dueDate, FacadeDriver deliveryPerson, FacadeTruck deliveryTruck, List<FacadeProduct> deliveredProduct) {
        OrderId = supplierOrderId;
        DeliveryId = deliveryId;
        IsUndelivered = isUndelivered;
        DueDate = dueDate;
        DeliveryPerson = deliveryPerson;
        DeliveryTruck = deliveryTruck;
        deliveredProducts = deliveredProduct;
    }

    public int getOrderId() {
        return OrderId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getDeliveryId() {
        return DeliveryId;
    }

    public boolean IsUndelivered() {
        return IsUndelivered;
    }

    public List<FacadeProduct> getDeliveredProducts() {
        return deliveredProducts;
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
        if (IsUndelivered) {
            sb.append("\nMissing products: show missing per product id\n");
            for (FacadeProduct entry : deliveredProducts)
                sb.append(String.format("\nProduct: %d\nAmount: %d\n", entry.getId(), entry.getAmount()));
        }
        return sb.toString();
    }


}

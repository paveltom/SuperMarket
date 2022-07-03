package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.SupplyTime;
import java.util.Arrays;

public class SupplyTimeDao extends DAO {
    public SupplyTimeDao(){}

    public void insert(SupplyTime st){
        String[] params = {st.getsId(), Arrays.toString(st.getOrderingDays()), "String.valueOf(st.getMaxDeliveryDuration())", //todo delete the green
                String.valueOf(st.getOrderCycle()), String.valueOf(st.getDaysAcc())};
        insert("SupplyTimes", params);
    }

    public void delete(SupplyTime st){
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        delete("SupplyTimes", keys, keysVals);
    }

    public void changeDaysOfDelivery(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "daysOfDelivery", String.valueOf(st.getOrderingDays()));
    }

    public void setMaxDeliveryDuration(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "maxDeliveryDuration", "String.valueOf(st.getMaxDeliveryDuration())"); //todo delete the green
    }

    public void setOrderCycle(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "orderCycle", String.valueOf(st.getOrderCycle()));
    }
}

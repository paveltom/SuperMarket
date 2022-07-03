package DAL.Stock_Suppliers.DAOS.SupplierObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import SuppliersModule.DomainLayer.SupplyTime;
import java.util.Arrays;

public class SupplyTimeDao extends DAO {
    public SupplyTimeDao(){}

    public void insert(SupplyTime st){
        String[] params = {st.getsId(), Arrays.toString(st.getOrderingDays()),
                String.valueOf(st.getOrderCycle()), String.valueOf(st.getDaysAcc())};
        insert("SupplyTimes", params);
    }

    public void delete(SupplyTime st){
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        delete("SupplyTimes", keys, keysVals);
    }

    public void changeOrderDays(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "orderingDays", String.valueOf(st.getOrderingDays()));
    }


    public void setOrderCycle(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "orderCycle", String.valueOf(st.getOrderCycle()));
    }

    public void setDaysAcc(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        update("SupplyTimes", keys, keysVals, "daysAcc", String.valueOf(st.getDaysAcc()));
    }
}

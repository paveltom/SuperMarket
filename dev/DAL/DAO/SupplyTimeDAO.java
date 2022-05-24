package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.SupplyTime;

import java.util.List;

public class SupplyTimeDAO {
    DataBaseConnection conn;
    public SupplyTimeDAO(){ conn = new DataBaseConnection(); }

    public void insert(SupplyTime st){
        String[] params = {st.getsId(), String.valueOf(st.getDaysOfDelivery()), String.valueOf(st.getMaxDeliveryDuration()),
                    String.valueOf(st.getOrderCycle()), String.valueOf(st.getDaysAcc())};
        conn.insert("SupplyTimes", params);
    }

    public void delete(SupplyTime st){
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        conn.delete("SupplyTimes", keys, keysVals);
    }

    public void changeDaysOfDelivery(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        conn.update("SupplyTimes", keys, keysVals, "daysOfDelivery", String.valueOf(st.getDaysOfDelivery()));
    }

    public void setMaxDeliveryDuration(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        conn.update("SupplyTimes", keys, keysVals, "maxDeliveryDuration", String.valueOf(st.getMaxDeliveryDuration()));
    }

    public void setOrderCycle(SupplyTime st) {
        String[] keys = {"supplier_id"};
        String[] keysVals = {st.getsId()};
        conn.update("SupplyTimes", keys, keysVals, "orderCycle", String.valueOf(st.getOrderCycle()));
    }

    public SupplyTime getSupplyTimeFromDB(String sId){
        String[] paramsW = {"supplier_id"};
        String[] paramsWV = {sId};
        List<String[]> st = conn.select("SupplyTimes",paramsW, paramsWV);

        //assuming unique sId
        return makeSupplyTimeFromDB(st.get(0));
    }
    private SupplyTime makeSupplyTimeFromDB(String[] s){
        String[] splitDaysOfDelivery = s[1].substring(1, s[1].length()-2).split(",");
        boolean[] daysOfDelivery = new boolean[7];
        for(int i = 0; i < 7; i++){
            daysOfDelivery[i] = Boolean.valueOf(splitDaysOfDelivery[i]);
        }

        return new SupplyTime(s[0], daysOfDelivery, Integer.valueOf(s[2]), Integer.valueOf(s[3]), Integer.valueOf(s[4]));
    }

}

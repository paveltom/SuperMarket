package SuppliersModule.DomainLayer;

import DAL.Stock_Suppliers.DAOS.SupplierObjects.SupplyTimeDao;
import java.time.LocalDate;
import java.util.Arrays;

public class SupplyTime {
    private boolean[] orderingDays; //|7| from sunday:= 0 to saturday:=7
    private int orderCycle; //when no weekly deliver, determines the amount of days between orders
    private int daysAcc; //when no weekly deliver, determines the amount of days past from last order
    private String sId;
    private SupplyTimeDao dao;

    //  getters
    public String getsId(){ return sId; }
    public boolean[] getOrderingDays() {
        return orderingDays;
    }
    public int getOrderCycle(){return orderCycle;}
    public int getDaysAcc() {return daysAcc;}
    public boolean hasWeeklyOrdering(){
        for(boolean day: orderingDays) {if (day) return true;}
        return false;
    }

    //  setters
    public void changeWeeklyOrdering(boolean[] orderingDays) {
        if (orderingDays == null || orderingDays.length != 7 )
            throw new IllegalArgumentException("incorrect format at days of delivery");
        this.orderingDays = orderingDays;


        dao.changeOrderDays(this);
    }

    public void setOrderCycle(int orderCycle){
        if(orderCycle == 0 | orderCycle < -1)
            throw new IllegalArgumentException("order cycle must be positive or -1");
        if(orderCycle > 0 & hasWeeklyOrdering())
            throw new IllegalArgumentException("order cycle is not an option when there is a weekly delivery");
        if(orderCycle == -1 & !hasWeeklyOrdering())
            throw new IllegalArgumentException("order cycle is a must when there isn't a weekly delivery");
        this.orderCycle = orderCycle;

        dao.setOrderCycle(this);
    }

    //  constructor
    public SupplyTime(String sId, boolean[] orderingDAys, int orderCycle){
        dao = new SupplyTimeDao();
        changeWeeklyOrdering(orderingDAys);
        setOrderCycle(orderCycle);
        daysAcc = orderCycle - 1; //if cycle delivery then an order would be placed at the end of the day
        this.sId = sId;

        dao.insert(this);
    }
    //constructor from db
    public SupplyTime(String sId, boolean[] orderingDays, int orderCycle, int daysAcc){
        this.orderingDays = orderingDays;
        this.orderCycle = orderCycle;
        daysAcc = orderCycle - 1;
        this.sId = sId;
        this.daysAcc = daysAcc;
        dao = new SupplyTimeDao();
    }

    //methods

    //return true if tomorrow is a weekly delivery / it's the cyclic time to order from the supplier
    public boolean endDay(){
        if (hasWeeklyOrdering()){
            int day = LocalDate.now().getDayOfWeek().getValue() % 7; //sunday:= 0
            return orderingDays[(day+1)%7];
        }
        else {
            daysAcc = (daysAcc + 1) % orderCycle;
            dao.setDaysAcc(this);
            return daysAcc == 0;
        }
    }

    public int getPeriodicOrderInterval(){ //returns the difference between the closest periodic order to the next
        if(!hasWeeklyOrdering()){
            return orderCycle;
        }
        else{
            int day = LocalDate.now().getDayOfWeek().getValue() % 7; //sunday:= 0
            int nextPeriodicDel = -1;
            int nextNextPeriodicDel = -1;
            for(int i=1; i<8; i++){
                if (orderingDays[(day+i)%7])
                    nextPeriodicDel = i;
            }
            for(int i=nextPeriodicDel+1; i<nextPeriodicDel+8; i++){
                if (orderingDays[(day+i)%7])
                    nextNextPeriodicDel = i;
            }
            return nextNextPeriodicDel - nextPeriodicDel;
        }
    }

    public int getDaysForShortageOrder() { //returns the difference between the closest available order to the next one
        if(!hasWeeklyOrdering()){
            return orderCycle - daysAcc;
        }
        else
            return getPeriodicOrderInterval();
    }

    public String toString() {
        return  "daysOfDelivery=" + Arrays.toString(orderingDays) +
                ", orderCycle=" + orderCycle +
                ", daysAcc=" + daysAcc ;
    }

    public void delete(){
        dao.delete(this);
    }
}

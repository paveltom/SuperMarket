package SuppliersModule.DomainLayer;

import DAL.DAO.SupplyTimeDAO;

import java.time.LocalDate;

public class SupplyTime {
    private boolean[] daysOfDelivery; //|7| from sunday:= 0 to saturday:=7
    private int maxDeliveryDuration; // 0:= deliver the same day of order, -1:= only weekly deliver
    private int orderCycle; //when no weekly deliver, determines the amount of days between orders
    private int daysAcc; //when no weekly deliver, determines the amount of days past from last order
    private String sId;
    private SupplyTimeDAO dao;

    //  getters
    public String getsId(){ return sId; }
    public boolean[] getDaysOfDelivery() {
        return daysOfDelivery;
    }
    public int getMaxDeliveryDuration() {
        return maxDeliveryDuration;
    }
    public int getOrderCycle(){return orderCycle;}
    public int getDaysAcc() {return daysAcc;}
    public boolean hasWeeklyDeliver(){
        for(boolean day: daysOfDelivery) {if (day) return true;}
        return false;
    }
    private boolean hasCallDelivery(){return maxDeliveryDuration >= 0;}

    //  setters
    private void setDaysOfDelivery(boolean[] daysOfDelivery) {
        if (daysOfDelivery == null || daysOfDelivery.length != 7 )
            throw new IllegalArgumentException("incorrect format at days of delivery");
        this.daysOfDelivery = daysOfDelivery;

        dao.changeDaysOfDelivery(this);
    }
    public void changeDaysOfDelivery(int day, boolean state) {
        if (day > 7 || day < 1)
            throw new IllegalArgumentException("day out of week range.");
        boolean[] week = daysOfDelivery.clone();
        week[day-1] = state;
        setDaysOfDelivery(week);

        dao.changeDaysOfDelivery(this);
    }
    public void setMaxDeliveryDuration(int maxDeliveryDuration) {
        if(maxDeliveryDuration < -1)
            throw new IllegalArgumentException("delivery duration must be -1 or greater");
        if(maxDeliveryDuration == -1 & !hasWeeklyDeliver())
            throw new IllegalArgumentException("missing delivery time options");
        this.maxDeliveryDuration = maxDeliveryDuration;

        dao.setMaxDeliveryDuration(this);
    }
    public void setOrderCycle(int orderCycle){
        if(orderCycle == 0 | orderCycle < -1)
            throw new IllegalArgumentException("order cycle must be positive or -1");
        if(orderCycle > 0 & hasWeeklyDeliver())
            throw new IllegalArgumentException("order cycle is not an option when there is a weekly delivery");
        if(orderCycle == -1 & !hasWeeklyDeliver())
            throw new IllegalArgumentException("order cycle is a must when there isn't a weekly delivery");
        this.orderCycle = orderCycle;

        dao.setOrderCycle(this);
    }

    //  constructor
    public SupplyTime(String sId, boolean[] daysOfDelivery, int maxDeliveryDuration, int orderCycle){
        dao = new SupplyTimeDAO();
        setDaysOfDelivery(daysOfDelivery);
        setMaxDeliveryDuration(maxDeliveryDuration);
        setOrderCycle(orderCycle);
        daysAcc = orderCycle - 1; //if cycle delivery then an order would be placed at the end of the day
        this.sId = sId;

        dao.insert(this);
    }
    //constructor from db
    public SupplyTime(String sId, boolean[] daysOfDelivery, int maxDeliveryDuration, int orderCycle, int daysAcc){
        setDaysOfDelivery(daysOfDelivery);
        setMaxDeliveryDuration(maxDeliveryDuration);
        setOrderCycle(orderCycle);
        daysAcc = orderCycle - 1; //if cycle delivery then an order would be placed at the end of the day
        this.sId = sId;
        this.daysAcc = daysAcc;
        dao = new SupplyTimeDAO();
    }

    //methods

    //return true if tomorrow is a weekly delivery / it's the cyclic time to order from the supplier
    public boolean endDay(){
        if (hasWeeklyDeliver()){
            int day = LocalDate.now().getDayOfWeek().getValue() % 7; //sunday:= 0
            return daysOfDelivery[(day+1)%7];
        }
        else {
            daysAcc = (daysAcc + 1) % orderCycle;
            return daysAcc == 0;
        }
    }

    public int getPeriodicOrderInterval(){ //returns the difference between the closest periodic order to the next
        if(!hasWeeklyDeliver()){
            return orderCycle;
        }
        else{
            int day = LocalDate.now().getDayOfWeek().getValue() % 7; //sunday:= 0
            int nextPeriodicDel = -1;
            int nextNextPeriodicDel = -1;
            for(int i=1; i<8; i++){
                if (daysOfDelivery[(day+i)%7])
                    nextPeriodicDel = i;
            }
            for(int i=nextPeriodicDel+1; i<nextPeriodicDel+8; i++){
                if (daysOfDelivery[(day+i)%7])
                    nextNextPeriodicDel = i;
            }
            return nextNextPeriodicDel - nextPeriodicDel;
        }
    }

    public int getDaysForShortageOrder() { //returns the difference between the closest available order arrival and the next periodic order arrival
        if(!hasWeeklyDeliver()){
            return orderCycle - daysAcc;
        }
        else if(!hasCallDelivery()) {
            return getPeriodicOrderInterval();
        }
        else{ //has both delivery options,check what comes first and choose by that
            int day = LocalDate.now().getDayOfWeek().getValue() % 7; //sunday:= 0
            int nextPeriodicDel = -1;
            for(int i=1; i<8; i++){
                if (daysOfDelivery[(day+i)%7])
                    nextPeriodicDel = i;
            }
            if(maxDeliveryDuration < nextPeriodicDel){
                return nextPeriodicDel - maxDeliveryDuration;
            }
            else
                return getPeriodicOrderInterval();
        }
    }
}

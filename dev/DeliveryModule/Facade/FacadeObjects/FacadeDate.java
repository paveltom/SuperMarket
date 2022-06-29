package DeliveryModule.Facade.FacadeObjects;


import DeliveryModule.BusinessLayer.Element.Date;

public class FacadeDate {

    private int month;
    private int day;
    private int year;
    private int shift;

    public FacadeDate(){}


    public FacadeDate(Date date){
        this.day = date.Day;
        this.month = date.Month;
        this.year = date.Year;
        this.shift = date.Shift;
    }


    public FacadeDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        this.shift = -1;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getShift(){return shift;}
}

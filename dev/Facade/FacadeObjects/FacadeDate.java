package Facade.FacadeObjects;


import BusinessLayer.Element.Date;
import BusinessLayer.Element.DeliveryDate;

public class FacadeDate {

    private int month;
    private int day;
    private int year;

    public FacadeDate(){}

    public FacadeDate(Date date){
        this.day = date.Day;
        this.month = date.Month;
        this.year = date.Year;
    }

    public FacadeDate(DeliveryDate date){
        this.day = date.Date.Day;
        this.month = date.Date.Month;
        this.year = date.Date.Year;
    }


    public FacadeDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
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

}

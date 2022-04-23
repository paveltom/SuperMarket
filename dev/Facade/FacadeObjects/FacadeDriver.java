package Facade.FacadeObjects;

public class FacadeDriver {
    private int id;
    private String firstName, lastName, cellphone, vehicleCategory, livingArea;

    public  FacadeDriver(){}

    public FacadeDriver(int id, String firstName, String lastName, String cellphone, String vehicleCategory, String livingArea){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.vehicleCategory = vehicleCategory;
        this.livingArea = livingArea;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(String livingArea) {
        this.livingArea = livingArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

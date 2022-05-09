package DeliveryModule.Facade.FacadeObjects;

import DeliveryModule.BusinessLayer.Element.Driver;

public class FacadeDriver {
    private long id;
    private String firstName, lastName, cellphone, vehicleCategory, livingArea;

    public  FacadeDriver(){}

    public  FacadeDriver(Driver driver){
        this.id = (int)driver.Id;
        this.firstName = driver.FirstName;
        this.lastName = driver.LastName;
        this.cellphone = driver.Cellphone;
        this.vehicleCategory = driver.License.name();
        this.livingArea = driver.Zone.name();
    }

    public FacadeDriver(long id, String firstName, String lastName, String cellphone, String vehicleCategory, String livingArea){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.vehicleCategory = vehicleCategory;
        this.livingArea = livingArea;
    }

    public String toString(){
        return id + ", " + firstName + " " + lastName + ", " + cellphone + ", " + vehicleCategory + ", " + livingArea;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public long getId() {
        return id;
    }

}

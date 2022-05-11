package DeliveryModule.Facade.FacadeObjects;

import DeliveryModule.BusinessLayer.Element.Driver;

public class FacadeDriver {
    private String id;
    private String name, vehicleCategory, livingArea;

    public  FacadeDriver(){}

    //waiting for Business layer Driver changes...
    public  FacadeDriver(Driver driver){
        this.id = Long.toString(driver.Id);
        this.name = driver.FirstName + " " + driver.LastName;
        this.vehicleCategory = driver.License.name();
        this.livingArea = driver.Zone.name();
    }

    public FacadeDriver(String id, String name, String vehicleCategory, String livingArea){
        this.id = id;
        this.name = name;
        this.vehicleCategory = vehicleCategory;
        this.livingArea = livingArea;
    }

    public String toString(){
        return id + ", " + name + ", " + vehicleCategory + ", " + livingArea + ".";
    }

    public String getName() {
        return name;
    }


    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public String getId() {
        return id;
    }

}

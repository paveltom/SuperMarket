package DeliveryModule.Facade.FacadeObjects;

import DeliveryModule.BusinessLayer.Element.Driver;

public class FacadeDriver {
    private String id;
    private String name, vehicleCategory, livingArea, cellphone;

    public  FacadeDriver(){}

    //waiting for Business layer Driver changes...
    public  FacadeDriver(Driver driver){
        this.id = driver.Id;
        this.name = driver.Name;
        this.vehicleCategory = driver.License.name();
        this.livingArea = driver.Zone.name();
        this.cellphone = driver.Cellphone;
    }

    public FacadeDriver(String id, String name, String vehicleCategory, String livingArea, String cellphone){
        this.id = id;
        this.name = name;
        this.vehicleCategory = vehicleCategory;
        this.livingArea = livingArea;
        this.cellphone = cellphone;
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

    public String getCellphone(){return this.cellphone;};

}

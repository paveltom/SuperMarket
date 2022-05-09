package DeliveryModule.Facade.FacadeObjects;

public class FacadeSite {

    private String zone, address, contactName, cellphone;

    public FacadeSite(){}

    public FacadeSite(String zone, String address, String contactName, String cellphone){
        this.zone = zone;
        this.address = address;
        this.contactName = contactName;
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getContactName() {
        return contactName;
    }


    public String getAddress() {
        return address;
    }


    public String getZone() {
        return zone;
    }
}

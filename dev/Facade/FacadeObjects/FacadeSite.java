package Facade.FacadeObjects;

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

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}

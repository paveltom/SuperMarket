package DeliveryModule.BusinessLayer.Element;
import DeliveryModule.BusinessLayer.Type.ShippingZone;

public class Site
{
    public final ShippingZone Zone;
    public final String Address, Name, Cellphone;

    public Site(ShippingZone Zone, String Address, String Name, String Cellphone)
    {
        this.Zone = Zone;
        this.Address = Address;
        this.Name = Name;
        this.Cellphone = Cellphone;
    }

    @Override
    public String toString()
    {
        return String.format("Site: %s\nZone: %s\nAddress: %s\nCellphone: %s\n", Name, Zone, Address, Cellphone);
    }
}

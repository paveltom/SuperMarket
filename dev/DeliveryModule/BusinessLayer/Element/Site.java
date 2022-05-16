package DeliveryModule.BusinessLayer.Element;
import DeliveryModule.BusinessLayer.Type.ShippingZone;

public class Site
{
    public final ShippingZone Zone;
    public final String Address, Name, Cellphone;
    private final String Delimiter = "\\$";

    public Site(ShippingZone Zone, String Address, String Name, String Cellphone)
    {
        this.Zone = Zone;
        this.Address = Address;
        this.Name = Name;
        this.Cellphone = Cellphone;
    }

    public Site(String encoded)
    {
        final int ZONE_INDEX = 0, ADDRESS_INDEX = 1, NAME_INDEX = 2, CELLPHONE_INDEX = 3;
        String tokens[] = encoded.split(Delimiter);
        Zone = ShippingZone.CreateShippingZoneByName(tokens[ZONE_INDEX]);
        Address = tokens[ADDRESS_INDEX];
        Name = tokens[NAME_INDEX];
        Cellphone = tokens[CELLPHONE_INDEX];
    }

    public String Encode()
    {
        return String.format("%s$%s$%s$%s", ShippingZone.GetShippingZoneName(Zone), Address, Name, Cellphone);
    }

    @Override
    public String toString()
    {
        return String.format("Site: %s\nZone: %s\nAddress: %s\nCellphone: %s\n", Name, Zone, Address, Cellphone);
    }
}

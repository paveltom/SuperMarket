package BusinessLayer.Test;
import BusinessLayer.DataObject.Site;
import BusinessLayer.Types.ShippingZone;


public class Exec {
    public static void main(String[] args)
    {
        var v = new Site(ShippingZone.Negev, "Address", "Name", "Cellphonen");
        System.out.println(v.toString());
    }
}

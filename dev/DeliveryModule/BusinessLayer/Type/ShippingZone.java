package DeliveryModule.BusinessLayer.Type;

import java.util.HashMap;
import java.util.Map;

public enum ShippingZone
{
    Negev,
    Shfela_JerusalemMountains,
    JudaeanDesert_DeadSea,
    WestBank,
    Sharon,
    Carmel,
    JezreelValley,
    Galilee,
    Golan;

    private final static String[] ZONES_NAMES = {"Negev", " Shfela_JerusalemMountains", "JudaeanDesert_DeadSea",
    "WestBank", "Sharon", "Carmel", "JezreelValley", "Galilee", "Golan"};

    public static String GetShippingZoneName(ShippingZone zone)
    {
        return ZONES_NAMES[zone.ordinal()];
    }

    private static Map<String, ShippingZone> Name2ShippingZone;
    static {
        Name2ShippingZone = new HashMap<>();
        for(ShippingZone zone : ShippingZone.values())
            Name2ShippingZone.put(GetShippingZoneName(zone), zone);
    }

    public static ShippingZone CreateShippingZoneByName(String zoneName)
    {
        return Name2ShippingZone.getOrDefault(zoneName, null);
    }
}


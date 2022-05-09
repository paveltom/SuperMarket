package DeliveryModule.BusinessLayer.Type;

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
}


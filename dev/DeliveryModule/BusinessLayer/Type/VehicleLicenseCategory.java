package DeliveryModule.BusinessLayer.Type;

import java.util.HashMap;
import java.util.Map;

public enum VehicleLicenseCategory
{
    E,  //Above 3.5e6 grams. adding trailer is allowed.
    C1, //Up to 12e6 grams.
    C;   // Up to 17e6 grams.

    private static final double MaxLoadWeights[] = {3500000.00, 12000000.00, 17000000.00};
    private static final String VehicleLicenseCategoryNames[] = {"E", "C1", "C"};

    public static double MaxLoadWeightByLicense(VehicleLicenseCategory x)
    {
        return MaxLoadWeights[x.ordinal()];
    }

    public static double GetMaxLoadWeight()
    {
        return MaxLoadWeights[C.ordinal()];
    }

    public static String GetVehicleLicenseCategoryName(VehicleLicenseCategory category)
    {
        return VehicleLicenseCategoryNames[category.ordinal()];
    }

    public static VehicleLicenseCategory FindLicense(double weight)
    {
        return weight <= MaxLoadWeights[E.ordinal()] ? E:
                weight <= MaxLoadWeights[C1.ordinal()] ? C1:
                        C;
    }

    private static Map<String, VehicleLicenseCategory> Name2VehicleLicenseCategory;
    static {
        Name2VehicleLicenseCategory = new HashMap<>();
        for(VehicleLicenseCategory licenseCategory : VehicleLicenseCategory.values())
            Name2VehicleLicenseCategory.put(GetVehicleLicenseCategoryName(licenseCategory), licenseCategory);
    }

    public VehicleLicenseCategory CreateShippingZoneByName(String zoneName)
    {
        return Name2VehicleLicenseCategory.getOrDefault(zoneName, null);
    }
}

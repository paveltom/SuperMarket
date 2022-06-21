package DeliveryModule.BusinessLayer.Type;

public enum RetCode
{
    SuccessfulDelivery,
    FailedDelivery_NoAvailableDriver,
    FailedDelivery_NoAvailableTruck,
    FailedDelivery_CannotDeliverWithinAWeek,
    FailedDelivery_CargoExceedMaxLoadWeight
    ;

    private static final String[] RetCodeNames =
    {
        "Delivered successfully",
        "Unable to deliver: There is no available driver",
        "Unable to deliver: There is no available truck",
        "Unable to deliver: Cannot deliver requested cargo within a week",
        "Unable to deliver: Requested delivery exceeds truck max load weight"
    };

    public static String GetRetCodeName(RetCode retCode)
    {
        return RetCodeNames[retCode.ordinal()];
    }

    public static RetCode Ordinal2RetCode(int ordinal)
    {
        return ordinal == 0 ? SuccessfulDelivery :
                ordinal == 1 ? FailedDelivery_NoAvailableDriver :
                        ordinal == 2 ? FailedDelivery_NoAvailableTruck :
                                ordinal == 3 ? FailedDelivery_CannotDeliverWithinAWeek :
                                        FailedDelivery_CargoExceedMaxLoadWeight;
    }
}

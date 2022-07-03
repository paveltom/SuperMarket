package DeliveryModule.BusinessLayer.Type;

public enum RetCode
{
    SuccessfulDelivery,
    FailedDelivery_NoAvailableDriver,
    FailedDelivery_NoAvailableTruck,
    FailedDelivery_CannotDeliverWithinAWeek,
    FailedDelivery_CargoExceedMaxLoadWeight,
    FailedDelivery_OrderIdExists,
    FailedDelivery_NoCertifiedDriver
    ;

    private static final String[] RetCodeNames =
    {
        "Delivered successfully",
        "Unable to deliver: There is no available driver",
        "Unable to deliver: There is no available truck",
        "Unable to deliver: Cannot deliver requested cargo within a week",
        "Unable to deliver: Requested delivery exceeds truck max load weight",
        "Unable to deliver: Order Id has already been served",
        "Unable to deliver: There is no certified driver"
    };

    private static final RetCode[] mapRetByOrdinal =
    {
            SuccessfulDelivery,
            FailedDelivery_NoAvailableDriver,
            FailedDelivery_NoAvailableTruck,
            FailedDelivery_CannotDeliverWithinAWeek,
            FailedDelivery_CargoExceedMaxLoadWeight,
            FailedDelivery_OrderIdExists,
            FailedDelivery_NoCertifiedDriver
    };

    public static String GetRetCodeName(RetCode retCode)
    {
        return RetCodeNames[retCode.ordinal()];
    }


    public static RetCode Ordinal2RetCode(int ordinal)
    {
        return mapRetByOrdinal[ordinal];
    }
}

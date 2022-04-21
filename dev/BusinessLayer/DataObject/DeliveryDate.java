package BusinessLayer.DataObject;

public class DeliveryDate
{
    public final int Day, Month, Shift;
    public final int Year;
    public final byte MONTH = 13, DAYS = 32, SHIFT = 4;
    private final String[] Shifts = {"06:00-09:00", "09:00-12:00", "12:00-15:00", "15:00-18:00"};

    public DeliveryDate(int day, int month, int year, int shift)
    {
        Day = day % 32;
        Month = month % MONTH;
        Year = year;
        Shift = shift % 4;
    }

    @Override
    public String toString()
    {
        return String.format("%d/%d/%d %s", Day, Month, Year, Shifts[Shift]);
    }

}

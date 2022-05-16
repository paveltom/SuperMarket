package DeliveryModule.BusinessLayer.Element;

public class DeliveryDate implements Comparable<DeliveryDate>
{
    public final int Shift;
    public final Date Date;
    private final String[] Shifts = {"06:00-09:00", "09:00-12:00", "12:00-15:00", "15:00-18:00"};
    private final String Delimiter = "\\$";

    public DeliveryDate(int month, int year, Shift shift)
    {
        Date = new Date(shift.Day, month, year);
        Shift = shift.Shift;
    }

    public DeliveryDate(String encoded)
    {
        String[] tokens = encoded.split(Delimiter);
        final int DATE_INDEX = 0, SHIFT_INDEX = 1;
        Date = new Date(tokens[DATE_INDEX]);
        Shift = Integer.parseInt(tokens[SHIFT_INDEX]);
    }

    public String Encode()
    {
        return String.format("%s$%d", Date.Encode(), Shift);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", Date, Shifts[Shift]);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof DeliveryDate))
            return false;
        else
        {
            DeliveryDate other = (DeliveryDate) obj;
            return Date.equals(other.Date) && Shift == other.Shift;
        }
    }

    @Override
    public int compareTo(DeliveryDate o)
    {
        if(o == null)
            return -1;
        boolean dateEquals = Date.equals(o.Date);
        return dateEquals ? Shift - o.Shift : Date.compareTo(o.Date);
    }

}

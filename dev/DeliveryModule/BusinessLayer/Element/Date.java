package DeliveryModule.BusinessLayer.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Date implements Comparable<Date>
{
    public final int Day;
    public final int Month;
    public final int Year;
    public final int Shift;
    public String Name;
    private final LocalDate localDate;

    private final int NSHIFTS = 4;
    private final String[] Shifts = {"06:00-09:00", "09:00-12:00", "12:00-15:00", "15:00-18:00"};
    private final String[] KEY_VALUE_METHOD_DAY_REMINDER = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final int[] KEY_VALUE_METHOD_MONTH_ORDIANL = {0, 1,	4, 4, 0,	2,	5,	0,	3,	6,	1,	4, 6};
    private final String[] MONTHS_NAME = {"PIVOT", "January", "February", "March", "April", "May", "June", "July",
    "August", "September", "October", "November", "December"};
    private final String Delimiter = " ";

    public Date(int day, int month, int year)
    {
        Day = day;
        Month = month;
        Year = year;
        localDate = LocalDate.of(Year, Month, Day);
        Shift = 0;
        KeyValueMethod();
    }

    public Date(int day, int month, int year, int shift)
    {
        Day = day;
        Month = month;
        Year = year;
        localDate = LocalDate.of(Year, Month, Day);
        Shift = shift;
        KeyValueMethod();
    }


    public Date(String encoded)
    {
        final int NAME_INDEX = 0, MONTH_INDEX = 1, DAY_INDEX = 2, YEAR_INDEX = 3, SHIFT_INDEX = 4;
        String[] tokens = encoded.split(Delimiter);
        Name = tokens[NAME_INDEX];
        Month = Integer.parseInt(tokens[MONTH_INDEX]);
        Day = Integer.parseInt(tokens[DAY_INDEX]);
        Year = Integer.parseInt(tokens[YEAR_INDEX]);
//        Shift = Integer.parseInt(tokens[SHIFT_INDEX]);
        Shift = 1;
        localDate = LocalDate.of(Year, Month, Day);
        KeyValueMethod();
    }


    /*
    * Calculating day of week given date.
    * The algorithm is taken from: https://beginnersbook.com/2013/04/calculating-day-given-date/
     */
    private void KeyValueMethod()
    {
        int last_two_digits_of_year = Year % 100;
        int x = (last_two_digits_of_year >> 2); // divide by four
        x += Day;
        x += KEY_VALUE_METHOD_MONTH_ORDIANL[Month];
        /* if(Month <= 2) x--; FOR LEAP YEAR */
        x += 6;
        x += last_two_digits_of_year;
        x = x % 7;
        Name = KEY_VALUE_METHOD_DAY_REMINDER[x];
    }

    public String Encode()
    {
        return String.format("%s %d %d %d %d", Name, Month, Day, Year, Shift);
    }


    @Override
    public String toString()
    {
        return String.format("%s %s %d %d %s", Name, MONTHS_NAME[Month], Day, Year, Shifts[Shift]);
    }

    public int DifferenceBetweenTwoDates(Date other)
    {
        Period diff = Period.between(localDate, other.localDate);
        return diff.getYears() * 365 + diff.getMonths() * 30 + diff.getDays();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Date))
            return false;
        else
        {
            Date other = (Date) obj;
            int diff = localDate.compareTo(other.localDate);
            return diff == 0 && Shift == other.Shift;
        }
    }

    @Override
    public int compareTo(Date o)
    {
        if(o == null)
            return -1;
        int diff = localDate.compareTo(o.localDate);
        return diff == 0 ? Shift-o.Shift : diff;
    }
}

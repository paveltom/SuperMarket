package DeliveryModule.BusinessLayer.Element;

public class Date implements Comparable<Date>
{
    public final int Day;
    public final int Month;
    public final int Year;
    public String Name;

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
        KeyValueMethod();
    }

    public Date(String encoded)
    {
        final int NAME_INDEX = 0, MONTH_INDEX = 1, DAY_INDEX = 2, YEAR_INDEX = 3;
        String[] tokens = encoded.split(Delimiter);
        Name = tokens[NAME_INDEX];
        Month = Integer.parseInt(tokens[MONTH_INDEX]);
        Day = Integer.parseInt(tokens[DAY_INDEX]);
        Year = Integer.parseInt(tokens[YEAR_INDEX]);
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
        return String.format("%s %d %d %d", Name, Month, Day, Year);
    }


    @Override
    public String toString()
    {
        return String.format("%s %s %s %d", Name, MONTHS_NAME[Month], Day, Year);
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
            return Year == other.Year && Month == other.Month && Day == other.Day;
        }
    }

    @Override
    public int compareTo(Date o)
    {
        if(o == null)
            return -1;
        /* If one of the condition is satisfied, this proceed other */
        if(Year < o.Year || Year == o.Year && Month < o.Month || Year == o.Year && Month == o.Month && Day < o.Day)
            return -1;
        /* If one of the condition is satisfied, this is later than other */
        else if(Year > o.Year || Month > o.Month || Day > o.Day)
            return 1;
        /* Both are equals */
        else
            return 0;
    }
}

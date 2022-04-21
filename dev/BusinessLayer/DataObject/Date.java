package BusinessLayer.DataObject;

public class Date implements Comparable<Date>
{
    public final int Day;
    public final int Month;
    public final int Year;

    public final String[] DAYS_NAME = {"PIVOT", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public final String[] MONTHS_NAME = {"PIVOT", "January", "February", "March", "April", "May", "June", "July",
    "August", "September", "October", "November", "December"};

    public Date(int day, int month, int year)
    {
        Day = day;
        Month = month;
        Year = year;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %d", MONTHS_NAME[Month], Day, Year);
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
        /* If one of the condition is satisfied, this proceed other */
        if(Year < o.Year || Month < o.Month || Year == o.Year && Month == o.Month && Day < o.Day)
            return -1;
        /* If one of the condition is satisfied, this is later than other */
        else if(Year > o.Year || Month > o.Month || Day > o.Day)
            return 1;
        /* Both are equals */
        else
            return 0;
    }
}

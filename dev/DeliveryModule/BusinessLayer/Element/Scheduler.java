package DeliveryModule.BusinessLayer.Element;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Scheduler
{
    private static final int NumOfMonths = 12;
    private static final int Year = 2022;
    private static final String Delimiter = "~";

    private Month[] Dairy;

    public Scheduler()
    {
        Dairy = new Month[NumOfMonths + 1];
        final int January = 1, February = 2, January_Ndays = 31, February_Ndays = 28;
        Dairy[January] = new Month(January_Ndays, January);
        Dairy[February] = new Month(February_Ndays, February);
        int numOfdays = 31;
        for(int month = 3; month <= NumOfMonths; month++)
        {
            Dairy[month] = new Month(numOfdays, month);
            /* Odd month has 31 days, whereas even month has 30 days excludes February which initialize before */
            numOfdays = ((month & 1) == 0) ? 31 : 30;
        }
    }

    /* Create Scheduler instance via parsing encoded string persisted in the DB */
    public Scheduler(String encoded)
    {
        Month[] months = new Month[NumOfMonths+1];
        String[] months_encoding = encoded.split(Delimiter);
        int i = 1;
        for(String encode : months_encoding)
            months[i++] = new Month(encode);
        Dairy = months;
    }

    /*
     * return null if all shift throughout the year are occupied.
     * else, return available delivery day.
     * Take into account supplier working days.
     */
    public Date NextShift(int month, int day, boolean[] supplierWorkingDays)
    {
        int i = month, j = day;
        while(i <= NumOfMonths)
        {
            Shift shift = Dairy[i].NextShift(j, supplierWorkingDays);
            if(shift != null)
                /* Month i has available slot on day >= j */
                return new Date(shift.Day, i, Year, shift.Shift);
            /* Month i has no available slot
             * Proceed to successive month from day 1 */
            else
            {
                j = 1;
                i++;
            }
        }
        return null;
    }
    /*
     * return null if all shift throughout the year are occupied.
     * else, return available delivery day.
     * Take into account supplier working days.
     */
    public Date NextShift(int month, int day)
    {
        int i = month, j = day;
        while(i <= NumOfMonths)
        {
            Shift shift = Dairy[i].NextShift(j);
            if(shift != null)
                /* Month i has available slot on day >= j */
                return new Date(shift.Day, i, Year, shift.Shift);
            /* Month i has no available slot
             * Proceed to successive month from day 1 */
            else
            {
                j = 1;
                i++;
            }
        }
        return null;
    }

    public void SetOccupied(Date occupiedDate)
    {
        Dairy[occupiedDate.Month].SetOccupied(new Shift(occupiedDate.Day, occupiedDate.Shift));
    }

    public void SetAvailable(Date occupiedDate)
    {
        Dairy[occupiedDate.Month].SetAvailable(new Shift(occupiedDate.Day, occupiedDate.Shift));
    }

    public void SetConstraint(Constraint constraint)
    {
        Dairy[constraint.Month].SetConstraints(constraint.WeeklyConstraints);
    }

    /* Return true iff exists occupied shift from param month at param day */
    public boolean IsOccupied(int month, int day)
    {
        int i = month, j = day;
        while(i <= NumOfMonths)
        {
            boolean isOccupied = Dairy[i].IsOccupied(j);
            if(isOccupied) /* Month i has pre-determined shift */
                return true;
            /* Proceed to check successive month from day 1 */
            j = 1;
            i++;
        }
        return false;
    }

    /* Encode Scheduler instance into string which will be persisted in the DB  */
    public String Encode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        int ndelimiters = NumOfMonths - 1;
        int j = 1;
        while(j <= NumOfMonths)
        {
            stringBuilder.append(Dairy[j].Encode());
            if(ndelimiters > 0)
                stringBuilder.append(Delimiter);
            ndelimiters--;
            j++;
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Scheduler))
            return false;
        else
        {
            Scheduler other = (Scheduler) obj;
            return Arrays.equals(Dairy, other.Dairy);
        }
    }
}

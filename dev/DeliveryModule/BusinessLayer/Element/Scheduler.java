package BusinessLayer.Element;

import java.util.Arrays;

public class Scheduler
{
    private final int NumOfMonths = 12;
    private final int Year = 2022;

    private Month[] Dairy;

    public Scheduler()
    {
        Dairy = new Month[NumOfMonths + 1];
        final int January = 1, February = 2;
        Dairy[January] = new Month(31);
        Dairy[February] = new Month(28);;
        int numOfdays = 31;
        for(int month = 3; month <= NumOfMonths; month++)
        {
            Dairy[month] = new Month(numOfdays);
            /* Odd month has 31 days, whereas even month has 30 days excludes February which initialize before */
            numOfdays = ((month & 1) == 0) ? 31 : 30;
        }
    }

    /*
     * return null if all shift throughout the year are occupied.
     * else, return available delivery day.
     */
    public DeliveryDate GetAvailableDeliveryDate(int month, int day)
    {
        final int DAY = 0, SHIFT = 1;
        int i = month, j = day;
        while(i <= NumOfMonths)
        {
            /* Month i has available slot on day >= j */
            var shift = Dairy[i].GetAvailableShift(j);
            if(shift != null)
            {
                return new DeliveryDate(i, Year, shift);
            }
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

    public void SetOccupied(DeliveryDate occupiedDate)
    {
        Dairy[occupiedDate.Date.Month].SetOccupied(new Shift(occupiedDate.Date.Day, occupiedDate.Shift));
    }

    public void SetConstraint(Constraint constraint)
    {
        Dairy[constraint.Month].SetConstraints(constraint.WeeklyConstraints);
    }

    public String Encode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        char delimiter = '$';
        int ndelimiters = NumOfMonths - 1;
        int j = 1;
        while(j <= NumOfMonths)
        {
            stringBuilder.append(Dairy[j].Encode());
            if(ndelimiters > 0)
                stringBuilder.append(delimiter);
            ndelimiters--;
            j++;
        }
        return stringBuilder.toString();
    }

    public Scheduler(String encoded)
    {
        Month[] months = new Month[NumOfMonths+1];
        String[] months_encoding = encoded.split("\\$");
        int i = 1;
        for(String encode : months_encoding)
            months[i++] = new Month(encode);
        Dairy = months;
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

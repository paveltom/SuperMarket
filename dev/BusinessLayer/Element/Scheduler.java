package BusinessLayer.Element;

public class Scheduler
{
    private final int NumOfMonths = 12;
    private final int February = 1;
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
            numOfdays = ((month & 1) == 0) ? 30 : 31;
        }
    }

    /*
     * return null if all shift throughout the year are occupied.
     * else, return available delivery day.
     */
    public DeliveryDate GetAvailableShift(int month, int day)
    {
        final int DAY = 0, SHIFT = 1;
        int i = month, j = day;
        while(i <= NumOfMonths)
        {
            /* Month i has available slot on day >= j */
            var shift = Dairy[i].GetAvailableShift(j);
            if(shift != null)
            {
                return new DeliveryDate(shift[DAY], i, Year, shift[SHIFT]);
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
        Dairy[occupiedDate.Date.Month].setOccupied(occupiedDate.Date.Day, occupiedDate.Shift);
    }
}

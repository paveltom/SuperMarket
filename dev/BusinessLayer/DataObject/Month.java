package BusinessLayer.DataObject;

public class Month
{
    private final int ShiftsPerDay = 4;
    public final int NumOfDays;
    /*
    * @INV: Shifts[i][j] = true iff shift j at day i is occupied.
    */
    private boolean[][] Shifts;

    public Month(int numOfDays)
    {
        NumOfDays = numOfDays;
        Shifts = new boolean[NumOfDays][ShiftsPerDay];
    }

    /*
    * return null if all shifts of this month are occupied.
    * else, return array of size 2 satisfy: [available_day, available_shift]
     */
    public int[] GetAvailableShift(int day)
    {
        for(int i = day; i<NumOfDays; i++)
        {
            for(int j = 0; j<ShiftsPerDay; j++)
            {
                if(!Shifts[i][j]) // Available shift
                {
                    Shifts[i][j] = true;
                    return new int[]{i, j};
                }
            }
        }
        return null; // This month is fully occupied.
    }

    public void setOccupied(DeliveryDate deliveryDate)
    {
        Shifts[deliveryDate.Date.Day][deliveryDate.Shift] = true;
    }

}

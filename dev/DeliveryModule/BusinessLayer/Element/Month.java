package DeliveryModule.BusinessLayer.Element;

/*
Each calendar month will be represented via Month object
Each instance record a shift (a delivery day) for Driver/Truck along the calendar month.
Assumption: there are 4 shifts per day, each last 3 hours.
 */
public class Month
{
    public final int ShiftsPerDay = 4;
    public final int NumOfDays;
    /*
    * @INV: Shifts[i][j] = true iff shift j at day i is occupied.
    */
    private boolean[][] Shifts;

    public Month(int numOfDays)
    {
        NumOfDays = numOfDays;
        Shifts = new boolean[NumOfDays+1][ShiftsPerDay];
    }

    /*
    * return null if all shifts of this month are occupied.
    * else, return array of size 2 satisfy: [available_day, available_shift]
     */
    public int[] GetAvailableShift(int day)
    {
        if(day >= 1 && day <= NumOfDays) { // validate legal input
            for (int i = day; i <= NumOfDays; i++) {
                for (int j = 0; j < ShiftsPerDay; j++) {
                    if (!Shifts[i][j]) // Available shift
                    {
                        Shifts[i][j] = true;
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null; // This month is fully occupied.
    }

    public void setOccupied(int day, int shift)
    {
        Shifts[day][shift] = true;
    }

}

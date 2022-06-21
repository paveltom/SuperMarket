package DeliveryModule.BusinessLayer.Element;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/*
Each calendar month will be represented via Month object
Each instance record a shift for Driver/Truck along the calendar month.
Assumption: there are 4 shifts per day, each last 3 hours.
 */
public class Month
{
    public final int ShiftsPerDay = 4;
    public final int NumOfDays;
    private final char NumOfDaysDelimiter = '@';
    private final char DaysDelimiter = '#';
    private int NextShift, NextDay;

    /*
     * @INV: Shifts[i][j] = true iff shift j at day i is occupied.
     */
    private final boolean[][] Shifts;

    private void Init()
    {
        LocalDateTime currDay = LocalDateTime.now();
        int currTime = currDay.getHour();
        NextShift = currTime <= 6 ? 0 : currTime <= 9 ? 1 : currTime <= 12 ? 2 : currTime <= 15 ? 3 : 4;
        NextDay = currDay.getDayOfMonth();
    }

    public Month(int numOfDays)
    {
        NumOfDays = numOfDays;
        Shifts = new boolean[NumOfDays+1][ShiftsPerDay];
        Init();
    }

    /* Create Month instance via parsing encoded string persisted in the DB */
    public Month(String encoded)
    {
        int i = 0, numOfDays = 0, row = 1, col = 0;
        char ch;
        char[] chars = encoded.toCharArray();
        while(chars[i] != NumOfDaysDelimiter)
        {
            numOfDays = numOfDays * 10 + chars[i] - '0';
            i++;
        }
        i++;
        boolean[][] res = new boolean[numOfDays + 1][ShiftsPerDay];
        int length = encoded.length();
        while(i<length)
        {
            ch = chars[i];
            if(ch != DaysDelimiter)
            {
                res[row][col] = ch == '1';
                col++;
            }
            else
            {
                row++;
                col = 0;
            }
            i++;
        }
        Shifts = res;
        NumOfDays = numOfDays;
        Init();
    }

    /*
     * return null if all shifts of this month are occupied.
     * else, return available Shift
     * Take into account supplier working days.
     */
    public Shift NextShift(int day, boolean[] supplierWorkingDays)
    {
        if(day >= 1 && day <= NumOfDays) // validate legal input
        {
            for (int i = NextDay; i <= NumOfDays; i++)
            {
                for (int j = NextShift; j < ShiftsPerDay; j++)
                {
                    if (!Shifts[i][j] && supplierWorkingDays[(i % 7)])
                        return new Shift(i, j);
                }
                NextShift = 0;
            }
        }
        return null; // This month is fully occupied.
    }

    /*
     * return null if all shifts of this month are occupied.
     * else, return available Shift
     * Doesn't take into account supplier working days.
     */
    public Shift NextShift(int day)
    {
        if(day >= 1 && day <= NumOfDays) // validate legal input
        {
            for (int i = NextDay; i <= NumOfDays; i++)
            {
                for (int j = NextShift; j < ShiftsPerDay; j++)
                {
                    if (!Shifts[i][j])
                        return new Shift(i, j);
                }
                NextShift = 0;
            }
        }
        return null; // This month is fully occupied.
    }

    public void SetOccupied(Shift shift)
    {
        Shifts[shift.Day][shift.Shift] = true;
        NextDay = shift.Day;
        NextShift = shift.Shift;
    }
    public void SetAvailable(Shift shift)
    {
        Shifts[shift.Day][shift.Shift] = false;
        NextDay = shift.Day;
        NextShift = shift.Shift;
    }


    public void SetConstraints(List<Shift> constraints)
    {
        for(Shift shift : constraints)
        {
            SetOccupied(shift);
        }
    }

    /* Return true iff exists occupied shift in this month from param day */
    public boolean IsOccupied(int day)
    {
        if(day >= 1 && day <= NumOfDays) { // validate legal input
            for (int i = day; i <= NumOfDays; i++) {
                for (int j = 0; j < ShiftsPerDay; j++) {
                    if (Shifts[i][j]) // occupied shift
                        return true;
                }
            }
        }
        return false; // All days after param day are not occupied.
    }

    /* Encode Month instance into string which will be persisted in the DB  */
    public String Encode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NumOfDays);
        stringBuilder.append(NumOfDaysDelimiter);
        int i = 1, delimiters = NumOfDays - 1;
        while(i <= NumOfDays)
        {
            for(boolean bool : Shifts[i])
            {
                stringBuilder.append(bool ? '1' : '0');
            }
            if(delimiters > 0)
                stringBuilder.append(DaysDelimiter);
            i++;
            delimiters--;
        }
        return stringBuilder.toString();
    }



    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Month))
            return false;
        else
        {
            Month other = (Month) obj;
            if(Shifts.length != other.Shifts.length || Shifts[0].length != other.Shifts[0].length)
                return false;
            int i = 0, n = Shifts.length;
            while(i < n)
            {
                if(! Arrays.equals(Shifts[i], other.Shifts[i]))
                    return false;
                i++;
            }
            return true;
        }
    }

}

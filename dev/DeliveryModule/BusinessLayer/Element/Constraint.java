package BusinessLayer.Element;

import java.util.List;

public class Constraint
{
	
	public final int Month;
    public final List<Shift> WeeklyConstraints;

    public Constraint(int month, List<Shift> weeklyConstraints)
    {
        Month = month;
        WeeklyConstraints = weeklyConstraints;
    }
}



package DeliveryModule.BusinessLayer.Element;

public class Shift
{
    public final int Day;

    /* shift must be value in the range [0,3] */
    public final int Shift;

    public Shift(int day, int shift)
    {
        Day = day;
        assert(shift>=0 && shift <= 3);
        Shift = shift;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Shift))
            return false;
        else
        {
            Shift other = (Shift) obj;
            return Day == other.Day && Shift == other.Shift;
        }
    }
}

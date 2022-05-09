package DeliveryModule.BusinessLayer.Type;

public class Pair<T, E>
{
    public final T First;
    public final E Second;

    public Pair(T f, E s)
    {
        First = f;
        Second = s;
    }
}

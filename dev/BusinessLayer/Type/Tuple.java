package BusinessLayer.Type;

public class Tuple<T, E, U>
{
    public final T First;
    public final E Second;
    public final U Third;


    public Tuple(T f, E s, U u)
    {
        First = f;
        Second = s;
        Third = u;
    }
}

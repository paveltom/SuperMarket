package Facade;

public class ResponseT<T> extends Response
{
    public T value;

    public ResponseT(String msg){
        super(msg);
    }

    // To create a response with value, when no error has occurred.
    public ResponseT(T value, boolean notError){
        this.value = value;
    }

    public ResponseT(T value, String msg){
        super(msg);
        this.value = value;
    }
}
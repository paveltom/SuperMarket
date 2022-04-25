package Service;

public class ResponseT <T> extends Response{
    private T value;

    private ResponseT(T val){
        value = val;
    }

    private ResponseT(String msg){
        super(msg);
    }

    public static <T> ResponseT<T> FromValue(T val){
        return new ResponseT<T>(val);
    }

    public static <T> ResponseT<T> FromError(String msg){
        return new ResponseT<T>(msg);
    }

    public T getValue(){
        return value;
    }
}

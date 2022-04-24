package Service;

public class ResponseT <T> extends Response{
    private T value;

    private ResponseT(T val){
        value = val;
    }

    private ResponseT(String msg){
        super(msg);
    }

    public static <T> ResponseT<T> fromValue(T val){
        return new ResponseT<T>(val);
    }

    public static <T> ResponseT<T> fromError(String msg){
        return new ResponseT<T>(msg);
    }
}

package com.company.ServiceLayer;

public class ResponseT<T> extends Response
{
    public T Value;

    private ResponseT(T value,String msg)
    {
        super(msg);
        this.Value = value;
    }

    static <T>Response FromValue(T value)
    {
        return new ResponseT<T>(value, null);
    }

    static <T>Response FromError(String msg)
    {
        return new ResponseT<T>(null, msg);
    }
}
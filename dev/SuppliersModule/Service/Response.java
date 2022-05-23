package com.company.SuppliersModule.Service;

public class Response {
    private String ErrorMessage = null;

    public Response(){}
    public Response(String msg){
        ErrorMessage = msg;
    }

    public boolean ErrorOccurred(){return ErrorMessage != null;}

    public String getErrorMessage(){return ErrorMessage;}

}

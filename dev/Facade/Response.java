package com.company.Facade;

public class Response {

    private String errorMessage;
    public boolean errorOccured = false;

    Response() {
    }

    Response(String msg){
        errorMessage = msg;
    }

    public boolean getErrorOccured(){
        return errorMessage != null;
    }

}
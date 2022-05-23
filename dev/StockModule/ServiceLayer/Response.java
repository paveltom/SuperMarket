package com.company.StockModule.ServiceLayer;

public class Response
{
    public String ErrorMessage = "";
    public Boolean ErrorOccured;

    Response(){

    }

    Response(String msg)
    {
        this.ErrorMessage = msg;
    }

    public Boolean ErrorOccured()
    {
        return !ErrorMessage.equals("");
    }
}

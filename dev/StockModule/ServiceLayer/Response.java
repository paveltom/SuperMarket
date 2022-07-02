package StockModule.ServiceLayer;

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

    public Boolean ErrorOccurred()
    {
        return !ErrorMessage.equals("");
    }
}

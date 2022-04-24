package Service;

public class Response {
    private String ErrorMessage = null;

    public Response(){

    }

    public Response(String msg){
        ErrorMessage = msg;
    }


    public boolean ErrorOccured(){return ErrorMessage != null;};
}

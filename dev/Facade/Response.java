package Facade;

public class Response {

    private String errorMessage;
    public boolean errorOccurred = false;

    Response() {
    }

    Response(String msg){
        errorMessage = msg;
        errorOccurred = true;
    }

    public boolean getErrorOccurred(){
        return errorOccurred;
    }

    public String getErrorMessage(){return errorMessage;}

}
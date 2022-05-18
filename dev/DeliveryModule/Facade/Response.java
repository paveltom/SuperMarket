package DeliveryModule.Facade;

public class Response {

    private String errorMessage;
    private boolean errorOccurred = false;

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
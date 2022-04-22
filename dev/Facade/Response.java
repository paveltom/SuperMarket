package Facade;

public class Response {

    private String errorMessage;
    private boolean errorOccured = false;

    Response() {
    }

    Response(String msg){
        errorMessage = msg;
    }

    public boolean getErrorOccured(){
        return errorMessage != null;
    }

    public String getErrorMessage(){ return this.errorMessage;}

}
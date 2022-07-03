package DeliveryModule.PresentationLayer;

import java.util.Scanner;

public class MenuPrinter {

    private Scanner scanner;

    public MenuPrinter(){
        scanner = new Scanner(System.in);
    }

    public void printMenu(String[] options){
        for(int i = 1; i < options.length; i++){
            System.out.println(i + ". " + options[i]);
        }
        System.out.println(0 + ". exit");
    }

    public String takeInput(String outputMsg){

        if(outputMsg.length() > 0) System.out.println(outputMsg);
        String input = null;
        if(scanner.hasNextLine())
            input = scanner.nextLine();
        return input;
    }

    public void takeOutput(String outMsg){
        System.out.println(outMsg);
    }

}

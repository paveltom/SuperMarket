package PresentationLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Stream;

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

        System.out.println(outputMsg);
        String input = null;
        if(scanner.hasNextLine())
            input = scanner.nextLine();
        return input;
    }

    public void takeOutput(String outMsg){
        System.out.println(outMsg);
    }

}

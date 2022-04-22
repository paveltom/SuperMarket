package PresentationLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Stream;

public class MenuPrinter {

    //private HashMap<Integer, String> options;
    private Scanner scanner;

    public MenuPrinter(){
        scanner = new Scanner(System.in);
    }

    public void printMenu(String[] options){
        for(int i = 1; i < options.length; i++){
            System.out.println(i + ". " + options[i]);
        }
        System.out.println(0 + ". exit");

//        Map<Integer, String> copy = new TreeMap<>(options);
//        for (Map.Entry<Integer, ?> opt : copy.entrySet()){
//            System.out.println(opt + ". " + copy.get(opt));
//        Stream<Map.Entry<Integer, String>> sortedStream = options.entrySet().stream().sorted(Map.Entry.comparingByKey());
//        for (Integer opt : options.keySet()){
//            System.out.println(opt + ". ");
//        }
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
        System.out.println();
    }

}

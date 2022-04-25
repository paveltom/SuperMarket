package Presentation;

import Service.SupplierServices;
import java.util.Scanner;

public class CliController {

    private final Scanner in;
    private final SupplierServices ss;

    public CliController(){
        in = new Scanner(System.in);
        ss = new SupplierServices();
        //loadData();
    }

    public void loadData(){
        //TODO
    }

    public void displayMainMenu() {
        System.out.println("""
                at any stage insert “$” to roll back to the main menu
                at any stage insert “b” to go back to the previous window
                insert one of the following numbers to select action

                1. add supplier
                2. show suppliers
                3. search product""");

        String input = in.nextLine();
        switch (input) {
            case "1" -> addingSupplierWindow();
            case "2" -> showSuppliersWindow();
            case "3" -> searchProductWindow();
            case "$", "b" -> displayMainMenu();
            default -> {
                System.out.println("incorrect input\n");
                displayMainMenu();
            }
        }
    }

    private void addingSupplierWindow(){
        System.out.println("""
                        insert bank account, using cash?, using credit?, contact name, contact phone-number
                        e.g 6456684 n y yossi 0524679565""");

        String input = in.nextLine();
        switch (input) {
            case "$", "b" -> displayMainMenu();
            default -> {
                String[] splitted = input.split(" ");
                if (splitted.length != 5) {
                    System.out.println("incorrect input\n");
                    addingSupplierWindow();
                } else {
                    String bankAcc = splitted[0];
                    boolean cash = splitted[1]=="y";
                    ss.addSupplier(s)
                    //give info about the action
                    //repeat if neccessery
                    //or go back to mm
                }
            }
        }

    }

    private void showSuppliersWindow(){


    }

    private void searchProductWindow() {

    }


}



package Presentation;

import Service.SupplierServices;

import java.util.Scanner;

public class CliController {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        SupplierServices ss = new SupplierServices();
        loadData();
        displayMainMenu();
    }

    private static void loadData(){
        //TODO
    }

    private static void displayMainMenu() {
        System.out.println("""
                at any stage insert “$” to roll back to the main menu
                at any stage insert “b” to go back to the previous window
                insert one of the following numbers to select action

                1. add supplier
                2. show suppliers
                3. search product""");

        String input = in.nextLine();
        switch (input) {
            case "1":
                addingSupplierWindow();
                break;
            case "2":
                showSuppliersWindow();
                break;
            case "3":
                searchProductWindow();
                break;
            case "$":
            case "b":
                displayMainMenu();
                break;
            default:
                System.out.println("incorrect input\n");
                displayMainMenu();
        }
    }

    private static void addingSupplierWindow(){
        System.out.println("""
                        insert bank account, using cash?, using credit?, contact name, contact phone-number
                        e.g 6456684 n y yossi 0524679565""");

        String input = in.nextLine();
        switch (input) {
            case "$":
            case "b":
                displayMainMenu();
                break;
            default:
                String[] splitted = input.split(" ");
                if(splitted.length != 5){
                    System.out.println("incorrect input\n");
                    addingSupplierWindow();
                }
                else{
                    //ss.addSup
                    //give info about the action
                    //repeat if neccessery
                    //or go back to mm
                }
        }

    }


}

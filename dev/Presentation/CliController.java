package Presentation;

import DomainLayer.Supplier;
import Service.Response;
import Service.ResponseT;
import Service.SupplierServices;

import java.util.List;
import java.util.Scanner;

public class CliController {

    private final Scanner in;
    private final SupplierServices ss;


    public CliController(){
        in = new Scanner(System.in);
        ss = new SupplierServices();
        loadData();
    }

    private void loadData(){
        LoadDataForTesting ld = new LoadDataForTesting(ss);
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
                        insert supplier id, bank account, using cash?, using credit?, contact name, contact phone-number
                        e.g 235 6456684 n y yossi 0524679565""");

        String input = in.nextLine();
        switch (input) {
            case "$", "b" -> displayMainMenu();
            default -> {
                String[] splitted = input.split(" ");
                if (splitted.length != 6 || (!splitted[2].equals("y") && !splitted[2].equals("n")) || (!splitted[3].equals("y") && !splitted[3].equals("n")) ) {
                    System.out.println("incorrect input\n");
                    addingSupplierWindow();
                } else {
                    Response r = ss.addSupplier(splitted[0], splitted[1], splitted[2].equals("y"), splitted[3].equals("y"), splitted[4], splitted[5]);
                    if(r.ErrorOccurred()){
                        System.out.println("action failed, " + r.getErrorMessage() + "\n");
                        addingSupplierWindow();
                    }
                    else{
                        System.out.println("action succeed\n");
                        displayMainMenu();
                    }
                }
            }
        }
    }

    private void showSuppliersWindow() {
        ResponseT<List<Supplier>> r = ss.getSuppliers();
        if (r.ErrorOccurred()) {
            System.out.println("action failed, " + r.getErrorMessage() + "\n");
            displayMainMenu();
        } else {
            for (Supplier supp : r.getValue()) {
                System.out.println(supp.toString());
            }
            System.out.println("insert supplier id to display its info");
            String input = in.nextLine();
            switch (input) {
                case "$", "b" -> displayMainMenu();
                default -> {
                    List<Supplier> matchedSupp = r.getValue().stream().filter(supplier -> supplier.getSid().equals(input)).toList();
                    if (r.getValue().stream().filter(supplier -> supplier.getSid().equals(input)).toList().isEmpty()) {
                        System.out.println("incorrect input\n");
                        showSuppliersWindow();
                    } else {
                        supplierInfoWindow(input);
                    }
                }
            }
        }
    }

    private void searchProductWindow() {

    }

    private void supplierInfoWindow(String suppId){
        System.out.println("""
                            1. display products
                            2. display quantity agreement
                            3. display contract
                            4. display contacts
                            5. delete supplier""");

        String input = in.nextLine();
        switch (input) {
            case "1" -> displayProductsWindow(suppId);
            case "2" -> displayQuantityAgreementWindow(suppId);
            case "3" -> displayContractWindow(suppId);
            case "4" -> displayContactsWindow(suppId);
            case "5" -> {
                Response r = ss.removeSupplier(suppId);
                if (r.ErrorOccurred()) {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    supplierInfoWindow(suppId);
                } else {
                    System.out.println("action succeed\n");
                    showSuppliersWindow();
                }
            }
            case "$" -> displayMainMenu();
            case "b" -> showSuppliersWindow();
            default -> {
                System.out.println("incorrect input\n");
                supplierInfoWindow(suppId);
            }
        }

    }

    private void displayProductsWindow(String suppId){

    }
    private void displayQuantityAgreementWindow(String suppId) {
    }

    private void displayContractWindow(String suppId) {

    }

    private void displayContactsWindow(String suppId) {

    }

}



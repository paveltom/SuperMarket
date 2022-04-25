package Presentation;

import DomainLayer.SupProduct;
import Service.Response;
import Service.ResponseT;
import Service.SupplierServices;

import java.lang.annotation.Repeatable;
import java.util.List;
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
                if (splitted.length != 5 || (!splitted[1].equals("y") && !splitted[1].equals("n")) || (!splitted[2].equals("y") && !splitted[2].equals("n")) ) {
                    System.out.println("incorrect input\n");
                    addingSupplierWindow();
                } else {
                    Response r = ss.addSupplier(splitted[0], splitted[1].equals("y"), splitted[2].equals("y"), splitted[3], splitted[4]);
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

    private void showSuppliersWindow(){


    }

    private void searchProductWindow() {
        System.out.println("""
                        insert product name
                        e.g 6456684 n y yossi 0524679565""");

        String input = in.nextLine();
        switch (input) {
            case "$", "b" -> displayMainMenu();
            default -> {
                ResponseT<List<SupProduct>> r = ss.searchProduct(input);
                if(!r.ErrorOccurred()){
                    productsDisplay(r.getValue());
                }
                else if(r.ErrorOccurred()){
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    searchProductWindow();
                }
            }
        }
    }

    private void showProducts(List<SupProduct> products){
        String productsString = "";
        for(SupProduct  sp: products){
            productsString += sp.toString() + "\n";
        }

        System.out.println("""
                        products: \n""" + products + "\n");
    }

    private void productsDisplay(List<SupProduct> products){
        showProducts(products);

        System.out.println("""
                        products: \n""" + products + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> searchProductWindow();
        }
    }

    private boolean isStringFloat(String testString) {
        try {
            float f = Float.parseFloat(testString);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private void productsDisplay(List<SupProduct> products, String sid){
        showProducts(products);

        System.out.println("Supplier id: " + sid + "\n" +
                         "products: \n" + products + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> showSuppliersWindow();

            default -> {

                if(splitted.length != 3) {
                    System.out.println("action failed, invalid argumets" + "\n");
                    productsDisplay(products, sid);
                }
                else {
                    Response r;
                    switch (splitted[0]) {
                        case "1" -> {
                            if (!isStringFloat(splitted[2])) {
                                System.out.println("action failed, price must be float" + "\n");
                                productsDisplay(products, sid);
                            }
                            r = ss.updateProductPrice(sid, splitted[1], Float.parseFloat(splitted[2]));
                            if(!r.ErrorOccurred()) {
                                productsDisplay(products, sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                productsDisplay(products, sid);
                            }

                        }
                        case "2" -> {
                            r = ss.updateProductName(sid, splitted[1], splitted[2]);
                            if(!r.ErrorOccurred()) {
                                productsDisplay(products, sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                productsDisplay(products, sid);
                            }
                        }
                        case "3" -> {
                            r = ss.updateProductCatalogNum(sid, splitted[1], splitted[2]);
                            if(!r.ErrorOccurred()) {
                                productsDisplay(products, sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                productsDisplay(products, sid);
                            }
                        }
                        default -> {
                            System.out.println("action failed, invalid argumets" + "\n");
                            productsDisplay(products, sid);
                        }
                    }

                }
            }
        }
    }

}



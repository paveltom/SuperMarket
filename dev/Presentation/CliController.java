package Presentation;

import DomainLayer.SupProduct;
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
        System.out.println("""
                        insert product name
                        e.g 6456684 n y yossi 0524679565""");

        String input = in.nextLine();
        switch (input) {
            case "$", "b" -> displayMainMenu();
            default -> {
                ResponseT<List<SupProduct>> r = ss.searchProduct(input);
                if(!r.ErrorOccurred()){
                    displayProductsWindow(r.getValue());
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

    private void displayProductsWindow(List<SupProduct> products){
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

    private void displayProductsWindow(String sid){
        ResponseT<List<SupProduct>> p = ss.getCatalog(sid);
        if(!p.ErrorOccurred())
            showProducts(p.getValue());

        System.out.println("Supplier id: " + sid + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> showSuppliersWindow();

            default -> {

                if(splitted.length != 3) {
                    System.out.println("action failed, invalid argumets" + "\n");
                    displayProductsWindow(sid);
                }
                else {
                    Response r;
                    switch (splitted[0]) {
                        case "1" -> {
                            if (!isStringFloat(splitted[2])) {
                                System.out.println("action failed, price must be float" + "\n");
                                displayProductsWindow(sid);
                            }
                            r = ss.updateProductPrice(sid, splitted[1], Float.parseFloat(splitted[2]));
                            if(!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }

                        }
                        case "2" -> {
                            r = ss.updateProductName(sid, splitted[1], splitted[2]);
                            if(!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }
                        }
                        case "3" -> {
                            r = ss.updateProductCatalogNum(sid, splitted[1], splitted[2]);
                            if(!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            }
                            else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }
                        }
                        default -> {
                            System.out.println("action failed, invalid argumets" + "\n");
                            displayProductsWindow(products, sid);
                        }
                    }

                }
            }
        }
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

    private void displayQuantityAgreementWindow(String suppId) {
    }

    private void displayContractWindow(String suppId) {

    }

    private void displayContactsWindow(String suppId) {

    }

}



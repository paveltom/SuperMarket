package Presentation;

import DomainLayer.*;
import Service.*;

import java.util.*;

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
                        insert product name""");

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

    private void showProducts(List<SupProduct> products){
        String productsString = "";
        for(SupProduct  sp: products){
            productsString = productsString + sp.toString() + "\n";
        }

        System.out.println("""
                        products: \n""" + productsString + "\n");
    }

    private void displayProductsWindow(List<SupProduct> products){
        showProducts(products);

        System.out.println("""
                        products: \n""" + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> searchProductWindow();
        }
    }

    private void displayProductsWindow(String sid) {
        ResponseT<List<SupProduct>> p = ss.getCatalog(sid);
        if (!p.ErrorOccurred())
            showProducts(p.getValue());

        System.out.println("Supplier id: " + sid + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> showSuppliersWindow();

            default -> {

                if (splitted.length != 3) {
                    System.out.println("action failed, invalid argumets" + "\n");
                    displayProductsWindow(sid);
                } else {
                    Response r;
                    switch (splitted[0]) {
                        case "1" -> {
                            if (!isStringFloat(splitted[2])) {
                                System.out.println("action failed, price must be float" + "\n");
                                displayProductsWindow(sid);
                            }
                            r = ss.updateProductPrice(sid, splitted[1], Float.parseFloat(splitted[2]));
                            if (!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            } else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }

                        }
                        case "2" -> {
                            r = ss.updateProductName(sid, splitted[1], splitted[2]);
                            if (!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            } else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }
                        }
                        case "3" -> {
                            r = ss.updateProductCatalogNum(sid, splitted[1], splitted[2]);
                            if (!r.ErrorOccurred()) {
                                displayProductsWindow(sid);
                            } else {
                                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                                displayProductsWindow(sid);
                            }
                        }
                        default -> {
                            System.out.println("action failed, invalid argumets" + "\n");
                            displayProductsWindow(sid);
                        }
                    }

                }
            }
        }
    }

    private void printQuantityAgreementMap(Dictionary<String, Dictionary<Integer, Float>> m){
        if(m == null) {
            System.out.print("no discounts");
        } else {
            Enumeration<String> e1 = m.keys();
            while(e1.hasMoreElements()) {
                String k = e1.nextElement();
                System.out.print(k + "\n\t");
                Dictionary<Integer, Float> d = m.get(k);

                if(d == null) {
                    System.out.print("no discounts for this product\n");
                } else {
                    Enumeration<Integer> e2 = d.keys();
                    while(e2.hasMoreElements()) {
                        int i = e2.nextElement();
                        System.out.print("quantity: " + i + " discount: " + d.get(i) + "\n\t");
                    }
                }
                System.out.println();
            }
        }
    }

    private void displayQuantityAgreementWindow(String suppId) {
        ResponseT<Dictionary<String, Dictionary<Integer, Float>>> m1 = ss.getPerItem(suppId);
        ResponseT<Dictionary<String, Dictionary<Integer, Float>>> m2 = ss.getPerOrder(suppId);

        if(m1.ErrorOccurred()){
            System.out.println("action failed: " + m1.getErrorMessage());
            supplierInfoWindow(suppId);
        }
        if(m2.ErrorOccurred()){
            System.out.println("action failed: " + m2.getErrorMessage());
            supplierInfoWindow(suppId);
        }

        System.out.println("""
                        1. discount by order size list : """);
        //print map 1
        printQuantityAgreementMap(m1.getValue());
        System.out.println("""
                        2. discount by item quantity list : """);
        //print map 2
        printQuantityAgreementMap(m2.getValue());

        System.out.println("""
                to add a discount press 1 or 2 and enter item id and quantity and discount
                to delete a discount press -1 or -2 and enter item id and quantity
                to add a discount press *1 or *2 and enter item id and quantity and discount
                 e.g “2 5 100 20””
                 """ + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> displayMainMenu();
            default -> {
                if(splitted.length == 4) {
                    if (splitted[0].equals("1")) {
                        Response action = ss.addDiscountPerItem(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                        if (action.ErrorOccurred()) {
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    } else if (splitted[0].equals("2")) {
                        Response action = ss.addDiscountPerOrder(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                        if (action.ErrorOccurred()) {
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    }
                    else if (splitted[0].equals("*1")) {
                        Response action = ss.updateDiscountPerItem(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                        if (action.ErrorOccurred()) {
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    }
                    else if (splitted[0].equals("*2")) {
                        Response action = ss.updateDiscountPerOrder(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                        if (action.ErrorOccurred()) {
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    }
                }
                else if(splitted.length == 3){
                    if(splitted[0].equals("-1")){
                        Response action = ss.removeDiscountPerItem(suppId, splitted[1], Integer.valueOf(splitted[2]));
                        if(action.ErrorOccurred()){
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    }
                    if(splitted[0].equals("-2")){
                        Response action = ss.removeDiscountPerOrder(suppId, splitted[1], Integer.valueOf(splitted[2]));
                        if(action.ErrorOccurred()){
                            System.out.println("action faild: " + action.getErrorMessage());
                        }
                        displayQuantityAgreementWindow(suppId);
                    }
                }else {
                    System.out.println("Invalid action");
                    displayQuantityAgreementWindow(suppId);
                }

            }
        }
    }

    private void displayContractWindow(String suppId) {
        ResponseT<Contract> r = ss.getSupplierContract(suppId);
        if (r.ErrorOccurred()){
            System.out.println("action failed, " + r.getErrorMessage() + "\n");
            supplierInfoWindow(suppId);
        }
        else{
            System.out.println(r.getValue().toString());
            System.out.println("""
                    insert a number and a following info to update
                    e.g 2 5 will change the max delivery days to 5
                    
                    1. change delivery day (expecting a day in the week as a number between 1-7 and y/n)
                    2. change max delivery days (any not negative number or -1 for no max delivery days)
                    3. change delivery service status (y/n)""");
            String input = in.nextLine();
            String[] splitted = input.split(" ");
            switch (splitted[0]) {
                case "1" -> {
                    try {
                        if (!splitted[2].equals("y") && !splitted[2].equals("n")) {
                            System.out.println("incorrect input\n");
                            displayContractWindow(suppId);
                        } else {
                            boolean[] days = new boolean[7];
                            days[Integer.parseInt(splitted[1]) - 1] = splitted[2].equals("y");
                            Response r2 = ss.setSupplyDays(suppId, days);
                            if (r2.ErrorOccurred()) {
                                System.out.println("incorrect input\n");
                                displayContractWindow(suppId);
                            } else {
                                System.out.println("action succeed\n");
                                displayContractWindow(suppId);
                            }
                        }
                    }
                    catch(Exception e){
                            System.out.println("incorrect input\n");
                            displayContractWindow(suppId);
                    }
                }
                case "2" -> {
                    try {
                        Response r2 = ss.setSupplyMaxDays(suppId, Integer.parseInt(splitted[1]));
                        if(r2.ErrorOccurred()){
                            System.out.println("incorrect input\n");
                            displayContractWindow(suppId);
                        }
                        else {
                            System.out.println("action succeed\n");
                            displayContractWindow(suppId);
                        }
                    }
                    catch (Exception e){
                        System.out.println("incorrect input\n");
                        displayContractWindow(suppId);
                    }
                }
                case "3" -> {
                    try {
                        if (!splitted[1].equals("y") && !splitted[1].equals("n")) {
                            System.out.println("incorrect input\n");
                            displayContractWindow(suppId);
                        } else {
                            Response r2 = ss.setDeliveryService(suppId, splitted[1].equals("y"));
                            if (r2.ErrorOccurred()) {
                                System.out.println("incorrect input\n");
                                displayContractWindow(suppId);
                            } else {
                                System.out.println("action succeed\n");
                                displayContractWindow(suppId);
                            }
                        }
                    }
                    catch(Exception e){
                            System.out.println("incorrect input\n");
                            displayContractWindow(suppId);
                    }

                }
                case "$" -> displayMainMenu();
                case "b" -> supplierInfoWindow(suppId);
                default -> {
                    System.out.println("incorrect input\n");
                    displayContractWindow(suppId);
                }
            }
        }


    }

    private void displayContactsWindow(String suppId) {
        System.out.println("""
                            to add "1" and contact insert name and phone number
                            to delete a contact enter "2" and contacts name
                            """);

        ResponseT<Map<String,String>> p = ss.getSupplierContacts(suppId);
        if (p.ErrorOccurred())
            supplierInfoWindow(suppId);

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        switch (input) {
            case "$" -> displayMainMenu();
            case "b" -> showSuppliersWindow();
            default -> {
                if(splitted[0].equals("1")){
                    if(splitted.length != 3){
                        System.out.println("Invalid input");
                        displayContactsWindow(suppId);
                    }
                    Response action = ss.addContact(suppId, splitted[1], splitted[2]);
                    if(action.ErrorOccurred())
                        System.out.println("action failed: " + action.getErrorMessage());
                }
                else if(splitted[0].equals("2") && splitted.length == 2){
                    Response action = ss.removeContact(suppId, splitted[1]);
                    if(action.ErrorOccurred())
                        System.out.println("action failed: " + action.getErrorMessage());
                }
                displayContactsWindow(suppId);
            }
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

}





package SuppliersModule.Presentation;

import SuppliersModule.DomainLayer.*;
import SuppliersModule.Service.*;

import java.util.*;
import java.util.stream.Collectors;

public class CliController {

    private final Scanner in;
    private final SupplierServices ss;


    public CliController() {
        in = new Scanner(System.in);
        ss = new SupplierServices();
        loadData();
    }

    private void loadData() {
        LoadDataForTesting ld;
        System.out.println("load testind data? y/n");
        String input = in.nextLine();
        if (input.equals("y")) {
            ld = new LoadDataForTesting(ss);
        }

    }

    public void displayMainMenu() {
        System.out.println("\n at any stage insert “$” to roll back to the main menu \n at any stage insert “b” to go back to the previous window \n insert one of the following numbers to select action\n\n1. add supplier \n2. show suppliers\n3. search product");

        String input = in.nextLine();
        if (input.equals("1"))
            addingSupplierWindow();
        else if(input.equals("2"))
            showSuppliersWindow();
        else if(input.equals("3"))
            searchProductWindow();
        else if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else{
            System.out.println("incorrect input\n");
            displayMainMenu();
        }
    }

    private void addingSupplierWindow() {
        System.out.println("\ninsert supplier id, bank account, using cash?, using credit?, contact name, contact phone-number\ne.g 235 6456684 n y yossi 0524679565");

        String input = in.nextLine();

        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else{
            String[] splitted = input.split(" ");
            if (splitted.length != 6 || (!splitted[2].equals("y") && !splitted[2].equals("n")) || (!splitted[3].equals("y") && !splitted[3].equals("n"))) {
                System.out.println("incorrect input\n");
                addingSupplierWindow();
            } else {
                Response r = ss.addSupplier(splitted[0], splitted[1], splitted[2].equals("y"), splitted[3].equals("y"), splitted[4], splitted[5]);
                if (r.ErrorOccurred()) {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    addingSupplierWindow();
                } else {
                    System.out.println("action succeed\n");
                    displayMainMenu();
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
            if(input.equals("$") || input.equals("b"))
                displayMainMenu();
            else {
                List<Supplier> matchedSupp = r.getValue().stream().filter(supplier -> supplier.getSid().equals(input)).collect(Collectors.toList());
                if (r.getValue().stream().noneMatch(supplier -> supplier.getSid().equals(input))) {
                    System.out.println("incorrect input\n");
                    showSuppliersWindow();
                } else {
                    supplierInfoWindow(input);
                }
            }
        }
    }

    private void searchProductWindow() {
        System.out.println("\ninsert product name");

        String input = in.nextLine();
        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else {
            ResponseT<List<SupProduct>> r = ss.searchProduct(input);
            if (!r.ErrorOccurred()) {
                displayProductsWindow(r.getValue());
            } else if (r.ErrorOccurred()) {
                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                searchProductWindow();
            }
        }
    }

    private void supplierInfoWindow(String suppId) {
        System.out.println("\n1. display products\n2. display quantity agreement\n3. display contract\n4. display contacts\n5. delete supplier");

        String input = in.nextLine();
        if(input.equals("1"))
            displayProductsWindow(suppId);
        else if(input.equals("2"))
            displayQuantityAgreementWindow(suppId);
        else if(input.equals("3"))
            displayContractWindow(suppId);
        else if(input.equals("4"))
            displayContactsWindow(suppId);
        else if(input.equals("5"))
            displayMainMenu();
        else if(input.equals("$"))
            displayMainMenu();
        else if(input.equals("b"))
            showSuppliersWindow();
        else {
            System.out.println("incorrect input\n");
            supplierInfoWindow(suppId);
        }



    }

    private void showProducts(List<SupProduct> products) {
        String productsString = "";
        for (SupProduct sp : products) {
            productsString = productsString + sp.toString() + "\n";
        }

        System.out.println("\nproducts: \n" + productsString + "\n");
    }

    private void displayProductsWindow(List<SupProduct> products) {
        showProducts(products);

        System.out.println("\nproducts: \n" + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        if(input.equals("$"))
            displayMainMenu();
        else if(input.equals("b"))
            searchProductWindow();
    }

    private void invalidproductAction(String sid){
        System.out.println("invalid action");
        displayProductsWindow(sid);
    }

    private void displayProductsWindow(String sid) {
        ResponseT<List<SupProduct>> p = ss.getCatalog(sid);
        if (!p.ErrorOccurred())
            showProducts(p.getValue());

        System.out.print("Supplier id: " + sid + "\n");
        System.out.println("\nto add product insert 1 and <catalog number, name, price” => page refreshed with product added            \ninsert 2 and product catalog number to remove it\ninsert 3 and product catalog number and product price to update price\ninsert 4 and product catalog number and product name to update name\ninsert 5 and product catalog number and new catalog number to update catalog number");


        String input = in.nextLine();
        String[] splitted = input.split(" ");

        if(input.equals("$"))
            displayMainMenu();
        else if(input.equals("b"))
            showSuppliersWindow();
        else{
            Response r;

            if(splitted[0].equals("1")) {
                if(splitted.length != 4 || !isStringFloat(splitted[3]))
                    invalidproductAction(sid);
                r = ss.addProduct(sid, splitted[1], splitted[2], Float.valueOf(splitted[3]));
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sid);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sid);
                }

            }
            else if(splitted[0].equals("2")) {
                if(splitted.length != 2)
                    invalidproductAction(sid);
                r = ss.removeProduct(sid, splitted[1]);
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sid);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sid);
                }

            }
            else if(splitted[0].equals("3")) {
                if(splitted.length != 3)
                    invalidproductAction(sid);
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
            else if(splitted[0].equals("4")) {
                if(splitted.length != 3)
                    invalidproductAction(sid);
                r = ss.updateProductName(sid, splitted[1], splitted[2]);
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sid);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sid);
                }
            }
            else if(splitted[0].equals("5")) {
                if(splitted.length != 3)
                    invalidproductAction(sid);
                r = ss.updateProductCatalogNum(sid, splitted[1], splitted[2]);
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sid);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sid);
                }
            }
            else {
                System.out.println("action failed, invalid argumets" + "\n");
                displayProductsWindow(sid);
            }
        }
    }

    private void printQuantityAgreementMap(Dictionary<String, Dictionary<Integer, Float>> m) {
        if (m == null) {
            System.out.print("no discounts");
        } else {
            Enumeration<String> e1 = m.keys();
            while (e1.hasMoreElements()) {
                String k = e1.nextElement();
                System.out.print(k + "\n\t");
                Dictionary<Integer, Float> d = m.get(k);

                if (d == null) {
                    System.out.print("no discounts for this product\n");
                } else {
                    Enumeration<Integer> e2 = d.keys();
                    while (e2.hasMoreElements()) {
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

        if (m1.ErrorOccurred()) {
            System.out.println("action failed: " + m1.getErrorMessage());
            supplierInfoWindow(suppId);
        }
        if (m2.ErrorOccurred()) {
            System.out.println("action failed: " + m2.getErrorMessage());
            supplierInfoWindow(suppId);
        }

        System.out.println(" 1. discount by order size list : ");
        //print map 1
        printQuantityAgreementMap(m1.getValue());
        System.out.println(" 2. discount by item quantity list : ");
        //print map 2
        printQuantityAgreementMap(m2.getValue());

        System.out.println("\nto add a discount press 1 or 2 and enter item id and quantity and discount\nto delete a discount press -1 or -2 and enter item id and quantity\nto add a discount press *1 or *2 and enter item id and quantity and discount\ne.g “2 5 100 20””\n\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");

        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else {
            if (splitted.length == 4) {
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
                } else if (splitted[0].equals("*1")) {
                    Response action = ss.updateDiscountPerItem(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                    if (action.ErrorOccurred()) {
                        System.out.println("action faild: " + action.getErrorMessage());
                    }
                    displayQuantityAgreementWindow(suppId);
                } else if (splitted[0].equals("*2")) {
                    Response action = ss.updateDiscountPerOrder(suppId, splitted[1], Integer.valueOf(splitted[2]), Float.valueOf(splitted[3]));
                    if (action.ErrorOccurred()) {
                        System.out.println("action faild: " + action.getErrorMessage());
                    }
                    displayQuantityAgreementWindow(suppId);
                }
            } else if (splitted.length == 3) {
                if (splitted[0].equals("-1")) {
                    Response action = ss.removeDiscountPerItem(suppId, splitted[1], Integer.valueOf(splitted[2]));
                    if (action.ErrorOccurred()) {
                        System.out.println("action faild: " + action.getErrorMessage());
                    }
                    displayQuantityAgreementWindow(suppId);
                }
                if (splitted[0].equals("-2")) {
                    Response action = ss.removeDiscountPerOrder(suppId, splitted[1], Integer.valueOf(splitted[2]));
                    if (action.ErrorOccurred()) {
                        System.out.println("action faild: " + action.getErrorMessage());
                    }
                    displayQuantityAgreementWindow(suppId);
                }
            } else {
                System.out.println("Invalid action");
                displayQuantityAgreementWindow(suppId);
            }
        }
    }

    private void displayContractWindow(String suppId) {
        ResponseT<Contract> r = ss.getSupplierContract(suppId);
        if (r.ErrorOccurred()) {
            System.out.println("action failed, " + r.getErrorMessage() + "\n");
            supplierInfoWindow(suppId);
        } else {
            System.out.println(r.getValue().toString());
            System.out.println("\ninsert a number and a following info to update\ne.g 2 5 will change the max delivery days to 5\n\n1. change delivery day (expecting a day in the week as a number between 1-7 and y/n)\n2. change max delivery days (any not negative number or -1 for no max delivery days)\n3. change delivery service status (y/n)");
            String input = in.nextLine();
            String[] splitted = input.split(" ");

            if(splitted[0].equals("1")){
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
                } catch (Exception e) {
                    System.out.println("incorrect input\n");
                    displayContractWindow(suppId);
                }
            }
            else if(splitted[0].equals("2")){
                try {
                    Response r2 = ss.setSupplyMaxDays(suppId, Integer.parseInt(splitted[1]));
                    if (r2.ErrorOccurred()) {
                        System.out.println("incorrect input\n");
                        displayContractWindow(suppId);
                    } else {
                        System.out.println("action succeed\n");
                        displayContractWindow(suppId);
                    }
                } catch (Exception e) {
                    System.out.println("incorrect input\n");
                    displayContractWindow(suppId);
                }
            }
            else if(splitted[0].equals("3")){
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
                } catch (Exception e) {
                    System.out.println("incorrect input\n");
                    displayContractWindow(suppId);
                }
            }
            else if(splitted[0].equals("$")){
                displayMainMenu();
            }
            else if(splitted[0].equals("b")){
                supplierInfoWindow(suppId);
            }
            else{
                System.out.println("incorrect input\n");
                displayContractWindow(suppId);
            }
        }
    }

    private void displayContactsWindow(String suppId) {
        System.out.println(" \nto add \"1\" and contact insert name and phone number\nto delete a contact enter \"2\" and contacts name");

        ResponseT<Map<String, String>> p = ss.getSupplierContacts(suppId);
        if (p.ErrorOccurred())
            supplierInfoWindow(suppId);

        String input = in.nextLine();
        String[] splitted = input.split(" ");

        if(splitted[0].equals("$")){
            displayMainMenu();
        }
        else if(splitted[0].equals("b")){
            showSuppliersWindow();
        }
        else{
            if (splitted[0].equals("1")) {
                if (splitted.length != 3) {
                    System.out.println("Invalid input");
                    displayContactsWindow(suppId);
                }
                Response action = ss.addContact(suppId, splitted[1], splitted[2]);
                if (action.ErrorOccurred())
                    System.out.println("action failed: " + action.getErrorMessage());
            } else if (splitted[0].equals("2") && splitted.length == 2) {
                Response action = ss.removeContact(suppId, splitted[1]);
                if (action.ErrorOccurred())
                    System.out.println("action failed: " + action.getErrorMessage());
            }
            displayContactsWindow(suppId);
        }
    }


    private boolean isStringFloat(String testString) {
        try {
            float f = Float.parseFloat(testString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}





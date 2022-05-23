package SuppliersModule.Presentation;

import SuppliersModule.DomainLayer.CatalogProduct;
import SuppliersModule.DomainLayer.Contract;
import SuppliersModule.DomainLayer.Supplier;
import SuppliersModule.Service.Response;
import SuppliersModule.Service.ResponseT;
import SuppliersModule.Service.SupplierServices;
import java.util.*;
import java.util.stream.Collectors;

public class SupplierCLI {

    private final Scanner in;
    private final SupplierServices ss;

    public SupplierCLI() {
        in = new Scanner(System.in);
        ss = new SupplierServices();
        loadData();
    }

    private void loadData() {
        LoadDataForTesting ld;
        System.out.println("load testing data? y/n");
        String input = in.nextLine();
        if (input.equals("y")) {
            ld = new LoadDataForTesting(ss);
        }
    }

    public void run(){
        displayMainMenu();
    }

    public void displayMainMenu() {
        System.out.println("\n at any stage insert “$” to roll back to the main menu " +
                "\n at any stage insert “b” to go back to the previous window " +
                "\n insert one of the following numbers to select action" +
                "\n\n1. add supplier " +
                "\n2. show suppliers" +
                "\n3. search product");

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
        System.out.println("\ninsert supplier id, bank account, using cash?, using credit?, contact name, contact phone-number," +
                           "\nsupply days, max supply days, supply cycle, delivery Service?, product id, catalog number, price" +
                           "\ne.g 235 6456684 n y yossi 0524679565 ");

        String input = in.nextLine();
        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else{
            String[] splitted = input.split(" ");
            if (splitted.length != 13 || (!splitted[2].equals("y") && !splitted[2].equals("n")) || (!splitted[3].equals("y") && !splitted[3].equals("n"))) {
                System.out.println("incorrect input\n");
                addingSupplierWindow();
            } else {
                boolean[] days = new boolean[7];
                days[Integer.parseInt(splitted[1]) - 1] = splitted[2].equals("y");
                Response r = ss.addSupplier(splitted[0], splitted[1], splitted[2], splitted[3], splitted[4].equals("y"), splitted[5].equals("y"), splitted[6], splitted[7],
                                            days, Integer.parseInt(splitted[9]), Integer.parseInt(splitted[10]), splitted[11].equals("y"),    //TODO days
                                            splitted[10], splitted[11], Float.parseFloat(splitted[12]));
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
                List<Supplier> matchedSupp = r.getValue().stream().filter(supplier -> supplier.getsId().equals(input)).collect(Collectors.toList());
                if (r.getValue().stream().noneMatch(supplier -> supplier.getsId().equals(input))) {
                    System.out.println("incorrect input\n");
                    showSuppliersWindow();
                } else {
                    supplierInfoWindow(input);
                }
            }
        }
    }

    private void searchProductWindow() {
        System.out.println("\ninsert product id");

        String input = in.nextLine();
        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else {
            ResponseT<List<CatalogProduct>> r = ss.searchProduct(input);
            if (!r.ErrorOccurred()) {
                displayProductsWindow(r.getValue());
            } else if (r.ErrorOccurred()) {
                System.out.println("action failed, " + r.getErrorMessage() + "\n");
                searchProductWindow();
            }
        }
    }

    private void supplierInfoWindow(String suppId) {
        System.out.println("\n1. display products" +
                "\n2. display quantity agreement\n" +
                "3. display contract\n" +
                "4. display contacts\n" +
                "5. delete supplier");

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

    private void showProducts(List<CatalogProduct> products) {
        String productsString = "";
        for (CatalogProduct sp : products) {
            productsString = productsString + sp.toString() + "\n";
        }

        System.out.println("\nproducts: \n" + productsString + "\n");
    }

    private void displayProductsWindow(List<CatalogProduct> products) {
        showProducts(products);

        System.out.println("\nproducts: \n" + "\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");
        if(input.equals("$"))
            displayMainMenu();
        else if(input.equals("b"))
            searchProductWindow();
    }

    private void invalidproductAction(String sId){
        System.out.println("invalid action");
        displayProductsWindow(sId);
    }

    private void displayProductsWindow(String sId) {
        ResponseT<List<CatalogProduct>> p = ss.getCatalog(sId);
        if (!p.ErrorOccurred())
            showProducts(p.getValue());

        System.out.print("Supplier id: " + sId + "\n");
        System.out.println("\nto add product insert 1 and <product id, catalog number, price> => page refreshed with product added" +
                "\ninsert 2 and product id to remove it from the supplier" +
                "\ninsert 3, product id and price to update price" +
                "\ninsert 4, product id and new catalog number to update catalog number");


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
                    invalidproductAction(sId);
                r = ss.addProduct(sId, splitted[1], splitted[2], Float.parseFloat(splitted[3]));
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sId);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sId);
                }

            }
            else if(splitted[0].equals("2")) {
                if(splitted.length != 2)
                    invalidproductAction(sId);
                r = ss.removeProduct(sId, splitted[1]);
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sId);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sId);
                }

            }
            else if(splitted[0].equals("3")) {
                if(splitted.length != 3)
                    invalidproductAction(sId);
                if (!isStringFloat(splitted[2])) {
                    System.out.println("action failed, price must be float" + "\n");
                    displayProductsWindow(sId);
                }
                r = ss.updateProductPrice(sId, splitted[1], Float.parseFloat(splitted[2]));
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sId);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sId);
                }

            }
            else if(splitted[0].equals("4")) {
                if(splitted.length != 3)
                    invalidproductAction(sId);
                r = ss.updateProductCatalogNum(sId, splitted[1], splitted[2]);
                if (!r.ErrorOccurred()) {
                    displayProductsWindow(sId);
                } else {
                    System.out.println("action failed, " + r.getErrorMessage() + "\n");
                    displayProductsWindow(sId);
                }
            }
            else {
                System.out.println("action failed, invalid argumets" + "\n");
                displayProductsWindow(sId);
            }
        }
    }

    private void printQuantityAgreementMap(Map<String, Map<Integer, Float>> m) {
        if (m == null) {
            System.out.print("no discounts");
        } else {
            Set<String> e1 = m.keySet();
           for(String s: e1) {
                System.out.print(s + "\n\t");
                Map<Integer, Float> d = m.get(s);

                if (d == null) {
                    System.out.print("no discounts for this product\n");
                } else {
                    Set<Integer> e2 = d.keySet();
                    for(Integer i: e2) {
                        System.out.print("quantity: " + i + " discount: " + d.get(i) + "\n\t");
                    }
                }
                System.out.println();
            }
        }
    }

    private void displayQuantityAgreementWindow(String suppId) {
        ResponseT<Map<String, Map<Integer, Float>>> m1 = ss.getDiscounts(suppId);

        if (m1.ErrorOccurred()) {
            System.out.println("action failed: " + m1.getErrorMessage());
            supplierInfoWindow(suppId);
        }

        System.out.println(" 1. discounts list:");
        //print map 1
        printQuantityAgreementMap(m1.getValue());

        System.out.println("\nto update a discount enter product id, quantity and discount" +
                            "\ne.g 5234 100 20\n");

        String input = in.nextLine();
        String[] splitted = input.split(" ");

        if(input.equals("$") || input.equals("b"))
            displayMainMenu();
        else {
            if (splitted.length == 3) {
                if (splitted[0].equals("1")) {
                    Response action = ss.updateDiscount(suppId, splitted[0], Integer.parseInt(splitted[1]), Float.parseFloat(splitted[2]));
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
        ResponseT<Contract> r = ss.getContract(suppId);
        if (r.ErrorOccurred()) {
            System.out.println("action failed, " + r.getErrorMessage() + "\n");
            supplierInfoWindow(suppId);
        } else {
            System.out.println(r.getValue().toString());
            System.out.println("\ninsert a number and a following info to update" +
                    "\ne.g 2 5 will change the max delivery days to 5" +
                    "\n\n1. change delivery day (expecting a day in the week as a number between 1-7 and y/n)" +
                    "\n2. change max delivery days (any not negative number or -1 for no max delivery days)" +
                    "\n3. change delivery service status (y/n)");

            String input = in.nextLine();
            String[] splitted = input.split(" ");

            if(splitted[0].equals("1")){
                try {
                    if (!splitted[2].equals("y") && !splitted[2].equals("n")) {
                        System.out.println("incorrect input\n");
                        displayContractWindow(suppId);
                    } else {
                        Response r2 = ss.changeDaysOfDelivery(suppId, Integer.parseInt(splitted[1]), splitted[2].equals("y"));
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

        ResponseT<Map<String, String>> p = ss.getContacts(suppId);
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





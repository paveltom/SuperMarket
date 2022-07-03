package SuppliersModule.Presentation;

import SuppliersModule.DomainLayer.Order;
import SuppliersModule.DomainLayer.Supplier;
import SuppliersModule.Service.Response;
import SuppliersModule.Service.ResponseT;
import SuppliersModule.Service.SupplierServices;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class SupplierCLI {

    private final Scanner in;
    private final SupplierServices ss;

    public SupplierCLI() {
        in = new Scanner(System.in);
        ss = new SupplierServices();
    }

    public void run(){
        supplierManagement();
    }

    private void supplierManagement() {
        System.out.println("\n at any stage insert “$” to roll back to the main menu " +
                "\n at any stage insert “b” to go back to the previous window " +
                "\n insert one of the following numbers to select action" +
                "\n0. exit suppliers system" +
                "\n1. Manage Suppliers " +
                "\n2. Orders");

        String input = in.nextLine();
        switch (input) {
            case "0":
                break;
            case "1":
                manageSuppliers();
                break;
            case "2":
                orders();
                break;
            case "$":
            case "b":
                supplierManagement();
                break;
            default:
                System.out.println("incorrect input\n\n");
                supplierManagement();
                break;
        }
    }

    private void manageSuppliers(){
        System.out.println( "1. Add suppliers\n" +
                            "2. Remove supplier\n" +
                            "3. Add contact\n" +
                            "4. Update order cycle\n" +
                            "5. Update Catalog\n" +
                            "6. Show suppliers info");

        String input = in.nextLine();
        switch (input) {
            case "$":
            case "b":
                supplierManagement();
                break;
            case "1":
                addSuppliers();
                break;
            case "2":
                System.out.println("Insert supplier id\n");
                String input2 = in.nextLine();
                Response r = ss.removeSupplier(input2);
                if(r.ErrorOccurred())
                    System.out.println("Action failed: " + r.getErrorMessage() + "\n");
                break;
            case "3":
                addContact();
                break;
            case "4":
                updateOrderCycle();
                break;
            case "5":
                updateCatalog();
                break;
            case "6":
                showSuppliers();
                break;
            default:
                System.out.println("incorrect input\n\n");
                break;
        }
        manageSuppliers();
    }

    private void orders(){
        System.out.println( "1. End day - make periodic orders\n" +
                            "2. Get order\n" +
                            "3. Get failed orders\n");

        String input = in.nextLine();
        switch (input) {
            case "b":
            case "$":
                supplierManagement();
                break;
            case "1":
                Response r = ss.endDay();
                if(r.ErrorOccurred())
                    System.out.println("Action Failed: " + r.getErrorMessage());
                break;
            case "2":
                getOrders();
                break;
            case "3":
                getFailedOrders();
                break;
            default:
                System.out.println("incorrect input\n\n");
                break;
        }
        orders();
    }

    private void updateOrderCycle(){
        System.out.println( "1. Set a weekly ordering cycle\n" +
                            "2. Set a fix number of days ordering cycle\n");

        String input = in.nextLine();
        switch (input) {
            case "b":
                manageSuppliers();
            case "$":
                supplierManagement();
                break;
            case "1":
                setWeeklyCycle();
                break;
            case "2":
                setFixDaysCycle();
                break;
            default:
                System.out.println("incorrect input\n\n");
                updateOrderCycle();
                break;
        }
        updateOrderCycle();
    }

    private void updateCatalog(){
        System.out.println( "1. Add product\n" +
                            "2. Remove product\n" +
                            "3. Update catalog number\n" +
                            "4. Update product price\n" +
                            "5. Update discount\n");

        String input = in.nextLine();
        switch (input) {
            case "b":
                manageSuppliers();
            case "$":
                supplierManagement();
                break;
            case "1":
                addProduct();
                break;
            case "2":
                removeProduct();
                break;
            case "3":
                updateCatalogNumber();
                break;
            case "4":
                updatePrice();
                break;
            case "5":
                updateDiscount();
                break;
            default:
                System.out.println("incorrect input\n\n");
                updateCatalog();
                break;
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getOrders(){
        System.out.println("Insert supplier id\n");
        String input = in.nextLine();
        ResponseT<List<Order>> r = ss.getOrders(input);
        if(!r.ErrorOccurred()){
            for (Order o : r.getValue())
                System.out.println(o.toString()+"\n");
        }
        else if (input.equals("b"))
            orders();
        else if (input.equals("$"))
            supplierManagement();
        else {
            System.out.println("Action failed: " + r.getErrorMessage() + "\n");
            getOrders();
        }
    }

    private void getFailedOrders(){
        throw new NotImplementedException();
    }

    private void addSuppliers() {
        String sId, name, address,  bankAccount, contactName, phoneNum, catNumber, pId;
        boolean cash, credit;
        boolean[] workingDays, orderingDays = {false, false, false, false, false, false, false};
        int orderCycle = -1;
        float price;

        do {
            System.out.println("insert supplier id");
            sId = in.nextLine();
            if (sId.isEmpty())
                System.out.println("incorrect input");
        }while (sId.isEmpty());

        do {
            System.out.println("insert bank account");
            bankAccount = in.nextLine();
            if (bankAccount.isEmpty())
                System.out.println("incorrect input");
        }while (bankAccount.isEmpty());

        do {
            System.out.println("insert name");
            name = in.nextLine();
            if (name.isEmpty())
                System.out.println("incorrect input");
        }while (name.isEmpty());

        do {
            System.out.println("insert address");
            address = in.nextLine();
            if (address.isEmpty())
                System.out.println("incorrect input");
        }while (address.isEmpty());

        while (true) {
            System.out.println("using cash? insert y/n");
            String sCash = in.nextLine();
            if(!(sCash.equals("y")) && !(sCash.equals("n")))
                System.out.println("incorrect input");
            else {
                cash = sCash.equals("y");
                break;
            }
        }

        while (true) {
            System.out.println("using credit? insert y/n");
            String sCredit = in.nextLine();
            if(!(sCredit.equals("y")) && !(sCredit.equals("n")))
                System.out.println("incorrect input");
            else {
                credit = sCredit.equals("y");
                break;
            }
        }

        while (true) {
            System.out.println("insert working days with 7 digits, 1 for working and 0 for not working, starting from Sunday, e.g 1111100");
            String sWorkingDays = in.nextLine();
            if(!isStringBoolean7(sWorkingDays))
                System.out.println("incorrect input");
            else {
                workingDays = stringToBoolean7(sWorkingDays);
                break;
            }
        }

        do {
            System.out.println("insert contact name");
            contactName = in.nextLine();
            if (contactName.isEmpty())
                System.out.println("incorrect input");
        }while (contactName.isEmpty());

        do {
            System.out.println("insert contact phone-number");
            phoneNum = in.nextLine();
            if (phoneNum.isEmpty())
                System.out.println("incorrect input");
        }while (phoneNum.isEmpty());

        String choice;
        do {
            System.out.println("choose ordering cycle: \n" +
                    "1. weekly \n" +
                    "2. a number of fix days\n");
            choice = in.nextLine();
            if (!choice.equals("1") && !choice.equals("2"))
                System.out.println("incorrect input");
        }while (!choice.equals("1") && !choice.equals("2"));
        if (choice.equals("1")){
            while (true) {
                System.out.println("insert order days with 7 digits, 1 for order day and 0 for not, starting from Sunday, e.g 1000100");
                String sOrderingDays = in.nextLine();
                if(!isStringBoolean7(sOrderingDays))
                    System.out.println("incorrect input");
                else {
                    orderingDays = stringToBoolean7(sOrderingDays);
                    break;
                }
            }
        }
        else {
            while (true) {
                System.out.println("insert a number of fix days");
                String sCycle = in.nextLine();
                if(!isStringInt(sCycle) || Integer.parseInt(sCycle) < 1)
                    System.out.println("incorrect input");
                else {
                    orderCycle = Integer.parseInt(sCycle);
                    break;
                }
            }
        }

        do {
            System.out.println("insert product id");
            pId = in.nextLine();
            if (pId.isEmpty())
                System.out.println("incorrect input");
        }while (pId.isEmpty());

        do {
            System.out.println("insert catalog number");
            catNumber = in.nextLine();
            if (catNumber.isEmpty())
                System.out.println("incorrect input");
        }while (catNumber.isEmpty());

        while (true) {
            System.out.println("insert price");
            String sPrice = in.nextLine();
            if(!isStringFloat(sPrice) || Float.parseFloat(sPrice) <= 0)
                System.out.println("incorrect input");
            else {
                price = Float.parseFloat(sPrice);
                break;
            }
        }

        Response r = ss.addSupplier(sId, name, address, bankAccount, cash, credit, workingDays, contactName, phoneNum,
                                    orderingDays, orderCycle, pId, catNumber, price);

        if (r.ErrorOccurred())
            System.out.println("action failed: " + r.getErrorMessage() + "\n");

        manageSuppliers();
    }

    private void addContact(){
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert contact name\n");
        String name = in.nextLine();
        System.out.println("Insert contact phone-number\n");
        String phone = in.nextLine();
        Response r = ss.addContact(sId, name, phone);
        if(r.ErrorOccurred())
            System.out.println("Action failed: " + r.getErrorMessage() + "\n");
    }

    private void showSuppliers() {
        ResponseT<List<Supplier>> r = ss.getSuppliers();
        if (r.ErrorOccurred())
            System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        else {
            for (Supplier supp : r.getValue()) {
                System.out.println(supp.toString() + "\n");
            }
        }
    }

    private void setWeeklyCycle(){
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert order days with 7 digits, 1 for order day and 0 for not, starting from Sunday, e.g 1000100\n");
        String ordersDays = in.nextLine();
        if(isStringBoolean7(ordersDays)) {
            Response r = ss.setWeeklyOrdering(sId, stringToBoolean7(ordersDays));
            if (r.ErrorOccurred())
                System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        }
        else
            System.out.println("Incorrect input\n");
    }

    private void setFixDaysCycle(){
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert a number of fix days\n");
        String orderCycle = in.nextLine();
        if(isStringInt(orderCycle)) {
            Response r = ss.setSupplyCycle(sId, Integer.parseInt(orderCycle));
            if (r.ErrorOccurred())
                System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        }
        else
            System.out.println("Incorrect input\n");
    }

    private void addProduct() {
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert product id\n");
        String pId = in.nextLine();
        System.out.println("Insert catalog number\n");
        String catNum = in.nextLine();
        System.out.println("Insert price\n");
        String price = in.nextLine();
        if(isStringFloat(price)) {
            Response r = ss.addProduct(sId, pId, catNum, Float.parseFloat(price));
            if (r.ErrorOccurred())
                System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        }
        else
            System.out.println("Incorrect input\n");
    }

    private void removeProduct() {
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert product id\n");
        String pId = in.nextLine();
        Response r = ss.removeProduct(sId, pId);
        if (r.ErrorOccurred())
            System.out.println("Action failed: " + r.getErrorMessage() + "\n");
    }

    private void updateCatalogNumber() {
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert product id\n");
        String pId = in.nextLine();
        System.out.println("Insert new catalog number\n");
        String catNum = in.nextLine();
        Response r = ss.updateProductCatalogNum(sId, pId, catNum);
        if (r.ErrorOccurred())
            System.out.println("Action failed: " + r.getErrorMessage() + "\n");
    }

    private void updatePrice() {
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert product id\n");
        String pId = in.nextLine();
        System.out.println("Insert new price\n");
        String price = in.nextLine();
        if(isStringFloat(price)) {
            Response r = ss.updateProductPrice(sId, pId, Float.parseFloat(price));
            if (r.ErrorOccurred())
                System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        }
        else
            System.out.println("Invalid input\n");
    }

    private void updateDiscount() {
        System.out.println("Insert supplier id\n");
        String sId = in.nextLine();
        System.out.println("Insert product id\n");
        String pId = in.nextLine();
        System.out.println("Insert quantity\n");
        String quantity = in.nextLine();
        System.out.println("Insert discount\n");
        String discount = in.nextLine();
        if(isStringInt(quantity) && isStringFloat(discount) && Integer.parseInt(quantity) > 0 && Float.parseFloat(discount) >= 0) {
            Response r = ss.updateDiscount(sId, pId, Integer.parseInt(quantity), Float.parseFloat(discount));
            if (r.ErrorOccurred())
                System.out.println("Action failed: " + r.getErrorMessage() + "\n");
        }
        else
            System.out.println("Invalid input\n");

    }

//////////////////////////////////////////////////////////////////////////////
    private boolean isStringFloat(String testString) {
        try {
            Float.parseFloat(testString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isStringInt(String testString) {
        try {
            Integer.parseInt(testString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isStringBoolean7(String testString){
        if (testString == null || testString.length() != 7)
            return false;
        for(int i=0; i<7; i++){
            if(testString.charAt(i) != '0' && testString.charAt(i) != '1')
                return false;
        }
        return true;
    }

    private boolean[] stringToBoolean7(String testString){
        boolean[] output = new boolean[7];
        for(int i=0; i<7; i++){
            output[i] = testString.charAt(i) == '1';
        }
        return output;
    }

}





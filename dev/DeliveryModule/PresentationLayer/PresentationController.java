package DeliveryModule.PresentationLayer;

import DAL.Delivery_Personnel.DataBaseConnection;
import DeliveryModule.Facade.FacadeObjects.*;
import DeliveryModule.Facade.IService;
import DeliveryModule.Facade.Response;
import DeliveryModule.Facade.ResponseT;
import DeliveryModule.Facade.Service;
import StockModule.BusinessLogicLayer.Product;
import StockModule.BusinessLogicLayer.StockController;

import java.time.LocalDate;
import java.util.*;

public class PresentationController {
    private final IService service;
    private final MenuPrinter menuPrinter;
    private CallableMenu mainMenu;
    private CallableMenu resourceMenu;
    private CallableMenu deliveryMenu;
    private CallableMenu superUserMenu;
    private final Stack<CallableMenu> menuStorage;

    public PresentationController(PersonelModule.BusinessLayer.ServiceLayer.Service pmService){
        service = new Service(pmService);
        menuPrinter = new MenuPrinter();
        menuStorage = new Stack<>();
        initDB();
        setMenus();
    }

    public void run(){
        int option = -1;
        String chooseMsg = "Choose your option : ";
        CallableMenu currMenu = mainMenu;

        while (option != 0) {
            menuPrinter.printMenu(currMenu.getMenuNames());
            String response = operateInput(chooseMsg);

            switch (response){
                case "sudo":
                    superUserMenu();
                    break;

                case "0":
                    exit();
                    break;

                case "1":
                    if(!menuStorage.isEmpty())
                        currMenu = menuStorage.pop();
                    else
                        return;
                    break;

                default:
                    try{
                        option = Integer.parseInt(response);
                    } catch (Exception e){
                        operateOutput("Bad input. Try again...");
                        operateOutput("");
                        continue;
                    }
                    CallableMenu tempCallMenu = currMenu.getMenu().get(option);
                    if(tempCallMenu == null) {
                        operateOutput("No such option. Try again...");
                        operateOutput("");
                        continue;
                    }
                    if(tempCallMenu.isMethod()){
                        try {
                            tempCallMenu.getMethod().call();
                        }catch (Exception e) {
                            operateOutput("An unhandled exception occurred: " + e.getMessage());
                            operateOutput("");
                        }
                    }
                    else {
                        menuStorage.push(currMenu);
                        currMenu = tempCallMenu;
                    }
            }
        }
        exit();
    }


    private String operateInput(String outputMsg){
        return menuPrinter.takeInput(outputMsg);
    }
    private void operateOutput(String outputMsg){
        menuPrinter.takeOutput(outputMsg);
    }

    private void superUserMenu() {
        operateOutput("Under construction...");
        operateOutput("");
    }

    private int addDelivery(){
        FacadeSite origin;
        FacadeSite destination;
        String[] deliveryParams = {"shipping zone", "address", "contact name", "cellphone"};
        String[] userInput = new String[4];
        operateOutput("-----------Enter origin site parameters----------");
        operateOutput("-----------Enter 0 at any field to cancel----------");
        showShippingZones();
        // Creating origin site obj considering received parameters
        for(int i = 0; i < deliveryParams.length; i++){
            String outMsg = "Enter " + deliveryParams[i] + ": ";
            String input = operateInput(outMsg);
            if(input.equals("0")) return 0;
            if(!checkAddDeliverySiteInput(i, input)){
                operateOutput("Bad input. Try again...");
                i--;
                continue;
            }
            userInput[i] = input;
        }

        origin = new FacadeSite(userInput[0], userInput[1], userInput[2], userInput[3]);
        operateOutput("");

        // Creating destination site obj considering received parameters
        operateOutput("-----------Enter destination site parameters-----------");
        operateOutput("-----------Enter 0 at any field to cancel----------");
        for(int i = 0; i < deliveryParams.length; i++){
            String outMsg = "Enter " + deliveryParams[i] + ": ";
            String input = operateInput(outMsg);
            if(input.equals("0")) return 0;
            if(!checkAddDeliverySiteInput(i, input)){
                operateOutput("Bad input. Try again...");
                i--;
                continue;
            }
            userInput[i] = input;
        }
        destination = new FacadeSite(userInput[0], userInput[1], userInput[2], userInput[3]);
        operateOutput("");

        // Creating list of products
        operateOutput("-----------Enter product ID and its amount.-----------");
        operateOutput("-----------Enter 0 at any Product Id to FINISH----------");
        operateOutput("-----------Enter 0 at any Amount to CANCEL the delivery order----------");
//        Map<Integer, Integer> products = new HashMap<Integer, Integer>() {{
//            put(123, 1000);
//            put(456, 500);
//            put(789, 100);
//        }};

        HashMap<String, Product> productMap = StockController.getInstance().getProductsInStock();
        String allProducts = "Products: (<ID>: <Product name>) \n";
        for(Product temp : productMap.values()){
            allProducts += temp.getID() + ": " + temp.getName() + "; ";
        }
        operateOutput(allProducts);

        List<FacadeProduct> productList = new ArrayList<>();
        String productId = "-1";
        int productAmount = -1;
        productId = operateInput("Product ID: ");

        // Receiving products parameters
        while(!productId.equals("0")){
            try {
                while (!productMap.containsKey(productId)) {
                    operateOutput("No such product...");
                    productId = operateInput("Product ID: ");
                }
                productAmount = Integer.parseInt(operateInput("Amount: "));
                if (productAmount == 0) return 0;
                if(productAmount < 1) {
                    operateOutput("Bad input. Try again...");
                    operateOutput("");
                    continue;
                }

                operateOutput("Product weight: " + productMap.get(productId).getWeight() + ". Total: " + (productMap.get(productId).getWeight()*productAmount));
                FacadeProduct currProduct = new FacadeProduct(Integer.parseInt(productId), productAmount, productMap.get(productId).getWeight());
                productList.add(currProduct);
                operateOutput("");
                productId = operateInput("Product ID: ");
                // next loop data
                //if(productId == 0) break;
            }catch (Exception e){
                operateOutput("Bad input. Try again...");
                operateOutput("");
            }

        }
        if(productList.size() == 0) return 0;

        boolean[] allDaySupplier = {true, true, true, true, true, true, true};
        boolean[] supplierWorkingDays = null;

        operateOutput("-----------Enter supplier's working days array (exmpl: 0011100)----------");
        operateOutput("***Enter 0 to cancel the delivery order");
        operateOutput("***Enter 1 if the supplier works every day");

        while(supplierWorkingDays == null) {
            String input = operateInput("");
            if (input.equals("0")) return 0;
            if (input.equals("1")) {
                supplierWorkingDays = allDaySupplier;
                break;
            }
            supplierWorkingDays = checkSupplierWorkingDaysInput(input);
            if (supplierWorkingDays == null) {
                operateOutput("Bad input. Try again...");
            }
        }

        LocalDate currTime = LocalDate.now();
        FacadeDate facDate = new FacadeDate(currTime.getDayOfMonth(), currTime.getMonthValue(), currTime.getYear());

        String id = Long.toUnsignedString(System.currentTimeMillis()); //unique order Id
        //String id = Integer.toUnsignedString((int)System.currentTimeMillis()); //unique order Id
        ResponseT<String> res = service.deliver(origin, destination, id, productList, facDate, supplierWorkingDays);
        if(res.getErrorOccurred()){
            operateOutput("Cannot add this delivery. " + res.getErrorMessage());
            return 0;
        }
        operateOutput(res.getValue());
        operateOutput("");
        return 0;
    }

    private boolean[] checkSupplierWorkingDaysInput(String input){
        boolean[] out = {true, true, true, true, true, true, true};
        String line = input.replaceAll(" ", "");
        if(line.length() != 7) return null;
        for(int i = 0; i < out.length; i++){
            if(line.charAt(i) != '1') {
                if (line.charAt(i) == '0') out[i] = false;
                else return null;
            }
        }
        return out;
    }

    public boolean checkAddDeliverySiteInput(int i, String input){
        // "shipping zone", "address", "contact name", "cellphone"
        boolean res = true;
        try {
            switch (i) {
                case 0:
                    if (!service.showShippingZones().getValue().replaceAll(",", "").contains(input)) res = false;
                    break;
                case 1:
                case 2:
                    break;
                case 3:
                    if (Long.parseLong(input) < 1) res = false;
                    break;
            }
        }catch (Exception e){
            res = false;
        }
        return res;
    }

    private int getDeliveriesHistory(){
        ResponseT<String> res = service.getDeliveryHistory();
        if(res.getErrorOccurred()){
            operateOutput("Couldn't display a delivery history. " + res.getErrorMessage());
            return 1;
        }
        operateOutput(res.getValue());
        operateOutput("");
        return 0;
    }

    private int addTruck(){
        operateOutput("----------Please enter trucks details----------");
        operateOutput("----------Enter '0' at any field to cancel----------");
        showShippingZones();
        //showLicenseCategories(); => there is no need to show them cause its defined automatically considering net weight
        String[] details = {"License Plate", "Model", "Parking area", "Net weight(5,000,000 - 10,000,000)", "Max load weight(excluding own weight)"};
        for(int i = 0; i < details.length; i++){
            String input = operateInput(details[i] + ": ");
            input = input.trim();
            if(input.equals("0")) return 0;
            if(!checkAddTruckInput(i , input, details)) {
                operateOutput("Bad input. Try again...");
                i--;
                continue;
            }
            details[i] = input;
        }
        FacadeTruck facTruck = new FacadeTruck(details[0], details[1], details[2], Double.parseDouble(details[3]), Double.parseDouble(details[3]) + Double.parseDouble(details[4]));
        Response res = service.addTruck(facTruck);
        if(res.getErrorOccurred()) {
            operateOutput("Couldn't add a new truck. " + res.getErrorMessage());
            operateOutput("");
            return 1;
        }
        operateOutput("Truck " + facTruck.getLicensePlate() + " was added successfully.");
        operateOutput("");
        return 0;
    }

    private int addDriver(){
        operateOutput("----------Please enter drivers details----------");
        operateOutput("----------Enter '0' at any field to cancel----------");
        showShippingZones();
        showLicenseCategories();
        String[] details = {"ID", "Name", "Vehicle Category", "Living Area"};
        for(int i = 0; i < details.length; i++){
            String input = operateInput(details[i] + ": ");
            if(input.equals("0")) return 0;
            details[i] = input;
        }
        FacadeDriver facDriver = new FacadeDriver(details[0], details[1], details[2], details[3], "");
        Response res = service.addDriver(facDriver);
        if(res.getErrorOccurred()) {
            operateOutput("Couldn't add a new driver. " + res.getErrorMessage());
            operateOutput("");
            return 1;
        }
        operateOutput("Driver " + facDriver.getId() + " was added successfully.");
        operateOutput("");
        return 0;
    }

    private int removeTruck(){
        try {
            String input = operateInput("Truck's license plate (enter '0' to cancel): ");
            if (input.equals("0")) return 0;
            Response res = service.removeTruck(input);
            if (res.getErrorOccurred()) {
                operateOutput("Couldn't remove a truck " + input + ". " + res.getErrorMessage());
                return 1;
            }
            operateOutput("Truck " + input + " was removed successfully.");
        }catch(Exception e){
            operateOutput("Bad input. Try again...");
            return removeTruck();
        }
        return 0;
    }

    private int removeDriver(){
        try {
            String input = operateInput("Driver's ID (enter '0' to cancel): ");
            input = input.trim();
            if (input.equals("0")) return 0;
            Response res = service.removeDriver(input);
            if (res.getErrorOccurred()) {
                operateOutput("Couldn't remove a driver " + input + ". " + res.getErrorMessage());
                return 1;
            }
            operateOutput("Driver " + input + " was removed successfully.");
        } catch (Exception e){
            operateOutput("Bad input. Try again...");
            return removeDriver();
        }
        return 0;
    }

    private int getDriverById(){
        try {
            String input = operateInput("Driver's ID (enter '0' to cancel): ");
            if (input.equals("0")) return 0;
            ResponseT<FacadeDriver> res = service.getDriverById(input);
            if(!res.getErrorOccurred()) {
                operateOutput(res.getValue().toString());
            }
            else {
                operateOutput("Couldn't get a driver " + input + ". " + res.getErrorMessage());
                return 1;
            }
        } catch (Exception e){
            operateOutput("Couldn't get a driver. Try again...");
            return 1;
        }
        return 0;
    }

    private int getTruckByPlate(){
        try {
            String input = operateInput("Truck's license plate (enter '0' to cancel): ");
            if (input.equals("0")) return 0;
            ResponseT<FacadeTruck> res = service.getTruckByPlate(input);
            if(!res.getErrorOccurred()) operateOutput(res.getValue().toString());
            else{
                operateOutput("Couldn't get a truck " + input + ". " + res.getErrorMessage());
                return 1;
            }
        }catch (Exception e){
            operateOutput("Couldn't get a truck. Try again...");
            return 1;
        }
        operateOutput("");
        return 0;
    }

    private int cancelDelivery(){
        try {
            String input = operateInput("Delivery's ID (enter '0' to cancel): ");
            if (input.equals("0")) return 0;
            ResponseT<String> res = service.cancelDelivery(input);
            if(!res.getErrorOccurred()) {
                operateOutput("Delivery " + input + " was cancelled successfully.\n");
            }
            else{
                operateOutput("Couldn't cancel a delivery " + input + ". " + res.getErrorMessage() + "\n");
                return 1;
            }
        }catch (Exception e){
            operateOutput("Couldn't cancel a delivery. Try again...\n");
            return 1;
        }
        return 0;
    }

    private int showDrivers(){
        operateOutput(service.showDrivers().getValue());
        return 0;
    }

    private int showTrucks(){
        operateOutput(service.showTrucks().getValue());
        return 0;
    }

    private int exit(){
        System.out.println("exiting...");
        System.exit(0);
        return 0;
    }

    private boolean checkAddTruckInput(int i, String input, String[] details) {
        // "License Plate", "Model", "Parking area", "Net weight", "Max load weight"
        boolean res = true;
        try {
            switch (i) {
                case 0:
                    if (Long.parseLong(input) < 1){
                        operateOutput("Negative number entered.");
                        res = false;
                    }
                    break;
                case 1:
                    try {
                        Long.parseLong(input);
                        operateOutput("Model cannot be a number.");
                        res = false;
                    }catch (Exception e){}
                    break;
                case 2:
                    if (!service.showShippingZones().getValue().replaceAll(",", "").contains(input)){
                        operateOutput("Non-existing shipping zone.");
                        res = false;
                    }
                    break;
                case 3:
                    if(Double.parseDouble(input) < 5000000 || Double.parseDouble(input) > 15000000){
                        operateOutput("Cannot be less than 5,000,000 or greater than 15,000,000.");
                        res = false;
                    }
                    break;
                case 4:
                    if(Double.parseDouble(input) <= 1){
                        operateOutput("Cannot be less than 1.");
                        res = false;
                    }
                    break;
            }
        }catch (Exception e){
            res = false;
        }
        return  res;
    }


    private void setMenus(){
        String[] resourcesMenuStrings = {"Exit", "Back", "Add truck", "Remove truck", "Show all drivers", "Show all trucks", "Get driver by ID", "Get truck by license plate"};
        Map<Integer, CallableMenu> resourceOpts = new HashMap<Integer, CallableMenu>(){{
            //put(2, new CallableMenu(() -> addDriver()));
            put(2, new CallableMenu(() -> addTruck()));
            //put(4, new CallableMenu(() -> removeDriver()));
            put(3, new CallableMenu(() -> removeTruck()));
            put(4, new CallableMenu(() -> showDrivers()));
            put(5, new CallableMenu(() -> showTrucks()));
            put(6, new CallableMenu(() -> getDriverById()));
            put(7, new CallableMenu(() -> getTruckByPlate()));
        }};
        resourceMenu = new CallableMenu(resourceOpts, resourcesMenuStrings);


        String[] deliveryMenuStrings = {"Exit", "Back", "New delivery", "Show deliveries history", "Cancel delivery", "Show shipping zones"};
        Map<Integer, CallableMenu> deliveryOpts = new HashMap<Integer, CallableMenu>(){{
            put(2, new CallableMenu(() -> addDelivery()));
            put(3, new CallableMenu(() -> getDeliveriesHistory()));
            put(4, new CallableMenu(() -> cancelDelivery()));
            put(5, new CallableMenu(() -> showShippingZones()));
        }};
        deliveryMenu = new CallableMenu(deliveryOpts, deliveryMenuStrings);


        String[] mainMenuStrings = {"Exit", "Back", "Delivery", "Resources"};
        Map<Integer, CallableMenu> mainMenuOpts = new HashMap<Integer, CallableMenu>(){{
            put(2, deliveryMenu);
            put(3, resourceMenu);
        }};
        mainMenu = new CallableMenu(mainMenuOpts, mainMenuStrings);


        String[] superUserMenuStrings = {"Exit", "Back", "Add Driver", "Remove Driver"};
        Map<Integer, CallableMenu> superUserMenuOpts = new HashMap<Integer, CallableMenu>(){{
            put(2, new CallableMenu(() -> addDriver()));
            put(3, new CallableMenu(() -> removeDriver()));
            put(4, mainMenu);
        }};
        superUserMenu = new CallableMenu(superUserMenuOpts, superUserMenuStrings);
    }


    private int showShippingZones(){
        operateOutput(service.showShippingZones().getValue());
        return 0;
    }

    private int showLicenseCategories(){
        operateOutput("License categories: " + service.showLicenseCategories().getValue());
        return 0;
    }

    private void initDB(){
        DataBaseConnection dbconn = new DataBaseConnection();
        dbconn.initDB();
    }


}

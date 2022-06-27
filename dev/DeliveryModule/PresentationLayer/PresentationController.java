package DeliveryModule.PresentationLayer;

import DeliveryModule.Facade.FacadeObjects.*;
import DeliveryModule.Facade.IService;
import DeliveryModule.Facade.Response;
import DeliveryModule.Facade.ResponseT;
import DeliveryModule.Facade.Service;
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
                    option = Integer.parseInt(response);
                    CallableMenu tempCallMenu = currMenu.getMenu().get(option);
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
            userInput[i] = input;
        }
        destination = new FacadeSite(userInput[0], userInput[1], userInput[2], userInput[3]);
        operateOutput("");

        // Creating list of products
        operateOutput("-----------Enter product ID and its amount.-----------");
        operateOutput("-----------Enter 0 at any Product Id to FINISH----------");
        operateOutput("-----------Enter 0 at any Amount to CANCEL----------");
        List<FacadeProduct> productList = new ArrayList<>();
        int productId = -1;
        int productAmount = -1;
        productId = Integer.parseInt(operateInput("Product ID (only Milk - 123): "));
        while (productId != 123) {
            operateOutput("No such product...");
            productId = Integer.parseInt(operateInput("Product ID (only Milk - 123): "));
        }
        productAmount = Integer.parseInt(operateInput("Amount: "));
        //Random rand = new Random();

        // Receiving products parameters
        while(productId != 0){
            //double randProductWeight = rand.nextDouble(20000000); //randomized weight of a single product (double-time of a truck's maxLoadWeight)
            //randProductWeight += 1;
            //operateOutput("Product weight: " + randProductWeight);
            operateOutput("Product weight: " + 153);
            FacadeProduct currProduct = new FacadeProduct(productId, productAmount, 153);
            productList.add(currProduct);

            // next loop data
            operateOutput("");
            productId = Integer.parseInt(operateInput("Product ID: "));
            if(productId == 0) break;
            while (productId != 123)
            {
                operateOutput("No such product...");
                productId = Integer.parseInt(operateInput("Product ID (only Milk - 123): "));
            }
            productAmount = Integer.parseInt(operateInput("Amount: "));
            if(productAmount == 0) return 0;
        }

        LocalDate currTime = LocalDate.now();
        FacadeDate facDate = new FacadeDate(currTime.getDayOfMonth(), currTime.getMonthValue(), currTime.getYear());

        String id = Integer.toUnsignedString((int)System.currentTimeMillis()); //unique order Id
        ResponseT<String> res = service.deliver(origin, destination, id, productList, facDate);
        operateOutput(res.getValue());
        operateOutput("");
        return 0;
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
        showLicenseCategories();
        String[] details = {"License Plate", "Model", "Parking area", "Net weight", "Max load weight"};
        for(int i = 0; i < details.length; i++){
            String input = operateInput(details[i] + ": ");
            if(input.equals("0")) return 0;
            details[i] = input;
        }
        FacadeTruck facTruck = new FacadeTruck(Integer.parseInt(details[0]), details[1], details[2], Double.parseDouble(details[3]), Double.parseDouble(details[4]));
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
        int input = Integer.parseInt(operateInput("Truck's license plate (enter '0' to cancel): "));
        if(input == 0) return 0;
        Response res = service.removeTruck(input);
        if(res.getErrorOccurred()) {
            operateOutput("Couldn't remove a truck " + input + ". " + res.getErrorMessage());
            return 1;
        }
        operateOutput("Truck " + input + " was removed successfully.");
        return 0;
    }

    private int removeDriver(){
        int input = Integer.parseInt(operateInput("Driver's ID (enter '0' to cancel): "));
        if(input == 0) return 0;
        Response res = service.removeDriver(String.valueOf(input));
        if(res.getErrorOccurred()) {
            operateOutput("Couldn't remove a driver " + input + ". " + res.getErrorMessage());
            return 1;
        }
        operateOutput("Driver " + input + " was removed successfully.");
        return 0;
    }

    private int getDriverById(){
        int input = Integer.parseInt(operateInput("Driver's ID (enter '0' to cancel): "));
        if(input == 0) return 0;
        operateOutput(service.getDriverById(String.valueOf(input)).getValue().toString());
        return 0;
    }

    private int getTruckByPlate(){
        int input = Integer.parseInt(operateInput("Truck's license plate (enter '0' to cancel): "));
        if(input == 0) return 0;
        operateOutput(service.getTruckByPlate(input).getValue().toString());
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


        String[] deliveryMenuStrings = {"Exit", "Back", "New delivery", "Show deliveries history"};
        Map<Integer, CallableMenu> deliveryOpts = new HashMap<Integer, CallableMenu>(){{
            put(2, new CallableMenu(() -> addDelivery()));
            put(3, new CallableMenu(() -> getDeliveriesHistory()));
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


}

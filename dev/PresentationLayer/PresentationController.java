package PresentationLayer;

import Facade.FacadeObjects.*;
import Facade.IService;
import Facade.Response;
import Facade.ResponseT;
import Facade.Service;
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

    public PresentationController(){
        service = new Service();
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
                        exit();
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
        operateOutput("-----------Enter 0 at any Amount to cancel----------");
        List<FacadeProduct> productList = new ArrayList<>();
        int productId = -1;
        int productAmount = -1;
        productId = Integer.parseInt(operateInput("Product ID: "));
        productAmount = Integer.parseInt(operateInput("Amount: "));
        Random rand = new Random();

        // Receiving products parameters
        while(productId != 0){
            double randProductWeight = rand.nextDouble(20000000); //randomized weight of a single product (double-time of a truck's maxLoadWeight)
            randProductWeight += 1;
            operateOutput("Product weight: " + randProductWeight);
            FacadeProduct currProduct = new FacadeProduct(productId, productAmount, randProductWeight);
            productList.add(currProduct);

            // next loop data
            operateOutput("");
            productId = Integer.parseInt(operateInput("Product ID: "));
            if(productId == 0) break;
            productAmount = Integer.parseInt(operateInput("Amount: "));
            if(productAmount == 0) return 0;
        }

        LocalDate currTime = LocalDate.now();
        FacadeDate facDate = new FacadeDate(currTime.getDayOfMonth(), currTime.getMonthValue(), currTime.getYear());

        int id = (int) System.currentTimeMillis(); //unique order Id
        Response res = service.deliver(origin, destination, id, productList, facDate);
        if(res.getErrorOccurred())
            operateOutput("Couldn't create a delivery for this order.\n" + res.getErrorMessage());
        else
            operateOutput("Delivery for the order " + id + " was successfully created.");
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
        String[] details = {"ID", "First Name", "Last Name", "Cellphone", "Vehicle Category", "Living Area"};
        for(int i = 0; i < details.length; i++){
            String input = operateInput(details[i] + ": ");
            if(input.equals("0")) return 0;
            details[i] = input;
        }
        FacadeDriver facDriver = new FacadeDriver(Integer.parseInt(details[0]), details[1], details[2], details[3], details[4], details[5]);
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

    private int exit(){
        System.out.println("exiting...");
        System.exit(0);
        return 0;
    }

    private void setMenus(){
        String[] resourcesMenuStrings = {"Exit", "Back", "Add driver", "Add truck"};
        Map<Integer, CallableMenu> resourceOpts = new HashMap<>(){{
            put(2, new CallableMenu(() -> addDriver()));
            put(3, new CallableMenu(() -> addTruck()));
        }};
        resourceMenu = new CallableMenu(resourceOpts, resourcesMenuStrings);


        String[] deliveryMenuStrings = {"Exit", "Back", "New delivery", "Show deliveries history"};
        Map<Integer, CallableMenu> deliveryOpts = new HashMap<>(){{
            put(2, new CallableMenu(() -> addDelivery()));
            put(3, new CallableMenu(() -> getDeliveriesHistory()));
        }};
        deliveryMenu = new CallableMenu(deliveryOpts, deliveryMenuStrings);


        String[] mainMenuStrings = {"Exit", "Back", "Delivery", "Resources"};
        Map<Integer, CallableMenu> mainMenuOpts = new HashMap<>(){{
            put(2, deliveryMenu);
            put(3, resourceMenu);
        }};
        mainMenu = new CallableMenu(mainMenuOpts, mainMenuStrings);


        String[] superUserMenuStrings = {"Exit", "Back", "dummyOption"};
        Map<Integer, CallableMenu> superUserMenuOpts = new HashMap<>(){{
            put(2, mainMenu);
        }};
        superUserMenu = new CallableMenu(superUserMenuOpts, superUserMenuStrings);
    }

//    private int showDrivers(){
//
//    }

//    private int showTrucks(){
//
//    }

}

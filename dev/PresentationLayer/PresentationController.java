package PresentationLayer;

import Facade.FacadeObjects.FacadeDate;
import Facade.FacadeObjects.FacadeProduct;
import Facade.FacadeObjects.FacadeSite;
import Facade.Response;
import Facade.ResponseT;
import Facade.Service;
import java.time.LocalDate;
import java.util.*;

public class PresentationController {
    private Service service;
    private MenuPrinter menuPrinter;
    private CallableMenu mainMenu;
    private CallableMenu resourceMenu;
    private CallableMenu deliveryMenu;
    private CallableMenu superUserMenu;
    private Stack<CallableMenu> menuStorage;

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

                case "0":
                    exit();

                case "1":
                    if(!menuStorage.isEmpty())
                        currMenu = menuStorage.pop();
                    else
                        exit();

                default:
                    option = Integer.parseInt(response);
                    CallableMenu tempCallMenu = currMenu.getMenu().get(option);
                    if(tempCallMenu.isMethod()){
                        try {
                            tempCallMenu.getMethod().call();
                        }catch (Exception e) {
                            operateOutput("An unhandled exception occurred: " + e.getMessage());
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
        operateOutput("under construction...");
    }

    private int addDelivery(){
        FacadeSite origin;
        FacadeSite destination;
        String[] deliveryParams = {"shipping zone", "address", "contact name", "cellphone"};
        String[] userInput = new String[4];
        operateOutput("Enter origin site parameters.");


        // Creating origin site obj considering received parameters
        for(int i = 0; i < deliveryParams.length; i++){
            String outMsg = "Enter " + deliveryParams[i] + ": ";
            userInput[i] = operateInput(outMsg);
        }
        origin = new FacadeSite(userInput[0], userInput[1], userInput[2], userInput[3]);


        // Creating destination site obj considering received parameters
        operateOutput("Enter destination site parameters.");
        for(int i = 0; i < deliveryParams.length; i++){
            String outMsg = "Enter " + deliveryParams[i] + ": ";
            userInput[i] = operateInput(outMsg);
        }
        destination = new FacadeSite(userInput[0], userInput[1], userInput[2], userInput[3]);


        // Creating list of products
        operateOutput("Enter product ID and its amount. Press '0' at the end.");
        List<FacadeProduct> productList = new ArrayList<>();
        int productId = -1;
        int productAmount = 0;
        productId = Integer.parseInt(operateInput("Product ID: "));
        productAmount = Integer.parseInt(operateInput("Amount: "));
        Random rand = new Random();

        // Receiving products parameters
        while(productId != 0){
            productId = Integer.parseInt(operateInput("Product ID: "));
            productAmount = Integer.parseInt(operateInput("Amount: "));
            double randProductWeight = rand.nextDouble(20000000); //randomized weight of a single product (double-time of a truck's maxLoadWeight)
            randProductWeight += 1;

            FacadeProduct currProduct = new FacadeProduct(productId, productAmount, randProductWeight);
            productList.add(currProduct);

            // next loop data
            productId = Integer.parseInt(operateInput("Product ID: "));
            productAmount = Integer.parseInt(operateInput("Amount: "));
        }

        LocalDate currTime = LocalDate.now();
        FacadeDate facDate = new FacadeDate(currTime.getDayOfMonth(), currTime.getMonthValue(), currTime.getYear());

        int id = (int) System.currentTimeMillis(); //unique order Id
        Response res = service.deliver(origin, destination, id, productList, facDate);
        if(res.getErrorOccured())
            operateOutput("Couldn't create a delivery for this order.\n" + res.getErrorMessage());
        else
            operateOutput("Delivery for the order " + id + " was successfully created.");
        return 0;
    }

    private int getDeliveriesHistory(){
        Response res = service.getDeliveryHistory();
        if(res.getErrorOccured()){
            operateOutput("Couldn't display a delivery history. " + res.getErrorMessage());
            return 1;
        }
        operateOutput(res.);
        return 0;
    }

    private int addTruck(){

    }

    private int addDriver(){

    }

//    private int showDrivers(){
//
//    }

//    private int showTrucks(){
//
//    }

    private int exit(){
        System.out.println("exiting...");
        System.exit(0);
        return 0;
    }

    private void setMenus(){
        String[] resourcesMenuStrings = {"exit", "Back", "Add driver", "Add truck"};
        Map<Integer, CallableMenu> resourceOpts = new HashMap<>(){{
            put(2, new CallableMenu(() -> addDriver()));
            put(3, new CallableMenu(() -> addTruck()));
        }};
        resourceMenu = new CallableMenu(resourceOpts, resourcesMenuStrings);


        String[] deliveryMenuStrings = {"exit", "Back", "New delivery", "Show deliveries history"};
        Map<Integer, CallableMenu> deliveryOpts = new HashMap<>(){{
            put(2, new CallableMenu(() -> addDelivery()));
            put(3, new CallableMenu(() -> getDeliveriesHistory()));
        }};
        deliveryMenu = new CallableMenu(deliveryOpts, deliveryMenuStrings);


        String[] mainMenuStrings = {"exit", "back", "Delivery", "Resources"};
        Map<Integer, CallableMenu> mainMenuOpts = new HashMap<>(){{
            put(2, deliveryMenu);
            put(3, resourceMenu);
        }};
        mainMenu = new CallableMenu(mainMenuOpts, mainMenuStrings);


        String[] superUserMenuStrings = {"exit", "Back", "dummyOption"};
        Map<Integer, CallableMenu> superUserMenuOpts = new HashMap<>(){{
            put(2, mainMenu);
        }};
        superUserMenu = new CallableMenu(superUserMenuOpts, superUserMenuStrings);
    }
}

package PresentationLayer;

import Facade.Service;
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
                            exit();
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
    }

    private int getDeliveriesHistory(){

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
